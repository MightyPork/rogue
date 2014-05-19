package mightypork.gamecore.core;


import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import mightypork.gamecore.Config;
import mightypork.gamecore.Config.ConfigSetup;
import mightypork.gamecore.Config.KeySetup;
import mightypork.gamecore.WorkDir;
import mightypork.gamecore.WorkDir.RouteSetup;
import mightypork.gamecore.eventbus.EventBus;
import mightypork.gamecore.eventbus.events.DestroyEvent;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.gamecore.gui.screens.impl.CrossfadeOverlay;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.logging.Log;
import mightypork.gamecore.logging.SlickLogRedirector;
import mightypork.gamecore.logging.writers.LogWriter;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.gamecore.resources.AsyncResourceLoader;
import mightypork.gamecore.resources.Res;
import mightypork.gamecore.resources.ResourceLoader;
import mightypork.gamecore.resources.ResourceSetup;
import mightypork.gamecore.resources.audio.SoundSystem;
import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.files.InstanceLock;
import mightypork.gamecore.util.ion.Ion;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Move;


/**
 * Basic screen-based game with subsystems.<br>
 * This class takes care of the initialization sequence.
 * 
 * @author MightyPork
 */
public abstract class BaseApp implements AppAccess, UncaughtExceptionHandler {
	
	/**
	 * Init options holder class
	 */
	public class AppInitOptions {
		
		private String logDir = "log";
		private String logFilePrefix = "runtime";
		
		private String screenshotDir = "screenshots";
		
		private int logArchiveCount = 0;
		private boolean busLogging = false;
		
		private String configFile = "settings.cfg";
		private String configComment = "Main config file";
		
		public String lockFile = ".lock";
		
		private final List<ResourceSetup> resourceLists = new ArrayList<>();
		private final List<Config.KeySetup> keyLists = new ArrayList<>();
		private final List<ConfigSetup> configLists = new ArrayList<>();
		private final List<RouteSetup> routeLists = new ArrayList<>();
		
		private ResourceLoader resourceLoader = new AsyncResourceLoader();
		private Level logLevel = Level.ALL;
		public boolean sigleInstance;
		
		
		public void setConfigFile(String filename, String comment)
		{
			configFile = filename;
			configComment = comment;
		}
		
		
		public void addConfig(ConfigSetup cfg)
		{
			configLists.add(cfg);
		}
		
		
		public void addKeys(Config.KeySetup keys)
		{
			keyLists.add(keys);
		}
		
		
		public void addRoutes(RouteSetup keys)
		{
			routeLists.add(keys);
		}
		
		
		public void addResources(ResourceSetup res)
		{
			resourceLists.add(res);
		}
		
		
		public void setBusLogging(boolean yes)
		{
			busLogging = yes;
		}
		
		
		public void setLogOptions(String logDir, String filePrefix, int archivedCount, Level logLevel)
		{
			this.logDir = logDir;
			this.logFilePrefix = filePrefix;
			this.logArchiveCount = archivedCount;
			this.logLevel = logLevel;
		}
		
		
		public void setResourceLoader(ResourceLoader resLoader)
		{
			resourceLoader = resLoader;
		}
		
		
		public void setScreenshotDir(String path)
		{
			this.screenshotDir = path;
		}
		
		
		public void setLockFile(String lockFile)
		{
			this.lockFile = lockFile;
		}
	}
	
	// modules
	private InputSystem inputSystem;
	private DisplaySystem displaySystem;
	private SoundSystem soundSystem;
	private EventBus eventBus;
	private MainLoop gameLoop;
	private ScreenRegistry screenRegistry;
	private boolean started = false;
	private boolean lockObtained = false;
	
	// init opt holder
	private final AppInitOptions opt = new AppInitOptions();
	
	
	/**
	 * Get init options
	 * 
	 * @return opt holder
	 */
	public AppInitOptions opt()
	{
		if (started) {
			throw new IllegalStateException("Cannot alter init options after starting the App.");
		}
		
		return opt;
	}
	
	
	public BaseApp(File workdir, boolean singleInstance) {
		WorkDir.init(workdir);
		
		Log.i("Using workdir: " + WorkDir.getWorkDir());
		
		opt.sigleInstance = singleInstance;
	}
	
	
	/**
	 * Start the application
	 */
	public final void start()
	{
		Thread.setDefaultUncaughtExceptionHandler(this);
		
		initialize();
		
		Log.i("Starting main loop...");
		
		// open first screen	
		started = true;
		gameLoop.start();
	}
	
	
	/**
	 * Init the app
	 */
	protected void initialize()
	{
		if (opt.sigleInstance) initLock();
		lockObtained = true;
		
		for (final RouteSetup rs : opt.routeLists) {
			WorkDir.registerRoutes(rs);
		}
		WorkDir.addPath("_screenshot_dir", opt.screenshotDir);
		
		// apply configurations
		Config.init(WorkDir.getFile(opt.configFile), opt.configComment);
		
		// add keys to config
		for (final KeySetup l : opt.keyLists) {
			Config.registerKeys(l);
		}
		
		// add options to config
		for (final ConfigSetup c : opt.configLists) {
			Config.registerOptions(c);
		}
		Config.load();
		
		/*
		 * Setup logging
		 */
		final LogWriter log = Log.create(opt.logFilePrefix, new File(WorkDir.getDir(opt.logDir), opt.logFilePrefix + ".log"), opt.logArchiveCount);
		log.setLevel(opt.logLevel);
		Log.setMainLogger(log);
		org.newdawn.slick.util.Log.setLogSystem(new SlickLogRedirector(log));
		
		Log.i("=== Starting initialization sequence ===");
		
		// pre-init hook
		Log.f2("Calling pre-init hook...");
		preInit();
		
		/*
		 * Event bus
		 */
		Log.f2("Starting Event Bus...");
		eventBus = new EventBus();
		eventBus.subscribe(this);
		eventBus.detailedLogging = opt.busLogging;
		
		/*
		 * Ionizables
		 */
		Log.f3("Initializing ION save system...");
		registerIonizables();
		
		/*
		 * Display
		 */
		Log.f2("Initializing Display System...");
		displaySystem = new DisplaySystem(this);
		initDisplay(displaySystem);
		
		/*
		 * Audio
		 */
		Log.f2("Initializing Sound System...");
		soundSystem = new SoundSystem(this);
		initSoundSystem(soundSystem);
		
		/*
		 * Input
		 */
		Log.f2("Initializing Input System...");
		inputSystem = new InputSystem(this);
		initInputSystem(inputSystem);
		
		/*
		 * Prepare main loop
		 */
		Log.f1("Creating Screen Registry and Game Loop...");
		screenRegistry = new ScreenRegistry(this);
		gameLoop = createMainLoop();
		gameLoop.setRootRenderable(screenRegistry);
		
		/*
		 * Load resources
		 * 
		 * Resources should be registered to registries, and AsyncResourceLoader will load them.
		 */
		Log.f1("Loading resources...");
		if (opt.resourceLoader != null) {
			opt.resourceLoader.init(this);
		}
		
		Res.init(this);
		
		for (final ResourceSetup rl : opt.resourceLists) {
			Res.load(rl);
		}
		
		/*
		 * Screen registry
		 * 
		 * Must be after resources, because screens can request them during instantiation.
		 */
		Log.f2("Registering screens...");
		initScreens(screenRegistry);
		
		postInit();
		Log.i("=== Initialization sequence completed ===");
	}
	
	
	@DefaultImpl
	protected void registerIonizables()
	{
		Ion.registerType(Coord.ION_MARK, Coord.class);
		Ion.registerType(Move.ION_MARK, Move.class);
	}
	
	
	/**
	 * Called at the beginning of the initialization sequence, right after lock
	 * was obtained.
	 */
	@DefaultImpl
	protected void preInit()
	{
	}
	
	
	/**
	 * Called at the end of init sequence, before main loop starts.
	 */
	@DefaultImpl
	protected void postInit()
	{
	}
	
	
	/**
	 * Create window and configure display system
	 * 
	 * @param display
	 */
	@DefaultImpl
	protected void initDisplay(DisplaySystem display)
	{
		display.createMainWindow(800, 600, true, false, "LWJGL game");
		display.setTargetFps(60);
	}
	
	
	/**
	 * Configure sound system (ie. adjust volume)
	 * 
	 * @param audio
	 */
	@DefaultImpl
	protected void initSoundSystem(SoundSystem audio)
	{
	}
	
	
	/**
	 * Register game screens to the registry.
	 * 
	 * @param screens
	 */
	@DefaultImpl
	protected void initScreens(ScreenRegistry screens)
	{
		screens.addOverlay(new CrossfadeOverlay(this));
	}
	
	
	/**
	 * Create game loop instance
	 * 
	 * @return the game loop.
	 */
	protected MainLoop createMainLoop()
	{
		return new MainLoop(this);
	}
	
	
	/*
	 * Try to obtain lock.
	 */
	private void initLock()
	{
		final File lock = WorkDir.getFile(opt.lockFile);
		if (!InstanceLock.onFile(lock)) {
			onLockError();
			return;
		}
	}
	
	
	@DefaultImpl
	protected void initInputSystem(InputSystem input)
	{
	}
	
	
	/**
	 * Triggered when lock cannot be obtained.<br>
	 * App should terminate gracefully.
	 */
	
	protected void onLockError()
	{
		Log.e("Could not obtain lock file.\nOnly one instance can run at a time.");
		
		//@formatter:off
		JOptionPane.showMessageDialog(
				null,
				"Another instance is already running.\n(Delete the "+opt.lockFile +" file in the working directory to override)",
				"Lock Error",
				JOptionPane.ERROR_MESSAGE
		);
		//@formatter:on
		
		shutdown();
	}
	
	
	@Override
	public final SoundSystem getSoundSystem()
	{
		return soundSystem;
	}
	
	
	@Override
	public final InputSystem getInput()
	{
		return inputSystem;
	}
	
	
	@Override
	public final DisplaySystem getDisplay()
	{
		return displaySystem;
	}
	
	
	@Override
	public final EventBus getEventBus()
	{
		return eventBus;
	}
	
	
	protected void beforeShutdown()
	{
		if (lockObtained) Config.save();
	}
	
	
	@Override
	public final void uncaughtException(Thread t, Throwable e)
	{
		onCrash(e);
	}
	
	
	protected void onCrash(Throwable e)
	{
		Log.e("The game has crashed.", e);
		shutdown();
	}
	
	
	@Override
	public final void shutdown()
	{
		beforeShutdown();
		
		Log.i("Shutting down subsystems...");
		
		try {
			if (getEventBus() != null) {
				getEventBus().send(new DestroyEvent());
				getEventBus().destroy();
			}
		} catch (final Exception e) {
			Log.e(e);
		}
		
		Log.i("Terminating...");
		System.exit(0);
	}
}
