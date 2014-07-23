package mightypork.gamecore.core.modules;


import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import mightypork.gamecore.backend.Backend;
import mightypork.gamecore.core.WorkDir;
import mightypork.gamecore.core.WorkDir.RouteSetup;
import mightypork.gamecore.core.config.Config;
import mightypork.gamecore.core.config.ConfigSetup;
import mightypork.gamecore.core.config.KeySetup;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.gamecore.gui.screens.impl.CrossfadeOverlay;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.gamecore.resources.AsyncResourceLoader;
import mightypork.gamecore.resources.Res;
import mightypork.gamecore.resources.ResourceLoader;
import mightypork.gamecore.resources.ResourceSetup;
import mightypork.gamecore.resources.audio.SoundSystem;
import mightypork.gamecore.util.SlickLogRedirector;
import mightypork.utils.annotations.DefaultImpl;
import mightypork.utils.eventbus.EventBus;
import mightypork.utils.eventbus.events.DestroyEvent;
import mightypork.utils.files.InstanceLock;
import mightypork.utils.ion.Ion;
import mightypork.utils.ion.IonInput;
import mightypork.utils.ion.IonOutput;
import mightypork.utils.ion.IonizerBinary;
import mightypork.utils.logging.Log;
import mightypork.utils.logging.writers.LogWriter;
import mightypork.utils.math.algo.Coord;
import mightypork.utils.math.algo.Move;


/**
 * Basic screen-based game with subsystems.<br>
 * This class takes care of the initialization sequence.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class BaseApp extends App implements AppAccess, UncaughtExceptionHandler {
	
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
		private final List<KeySetup> keyLists = new ArrayList<>();
		private final List<ConfigSetup> configLists = new ArrayList<>();
		private final List<RouteSetup> routeLists = new ArrayList<>();
		
		private ResourceLoader resourceLoader = new AsyncResourceLoader();
		private Level logLevel = Level.ALL;
		public boolean sigleInstance;
		private Level logSoutLevel;
		
		
		public void setConfigFile(String filename, String comment)
		{
			configFile = filename;
			configComment = comment;
		}
		
		
		public void addConfig(ConfigSetup cfg)
		{
			configLists.add(cfg);
		}
		
		
		public void addKeys(KeySetup keys)
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
		
		
		public void setLogLevel(Level logLevel, Level soutLevel)
		{
			this.logLevel = logLevel;
			this.logSoutLevel = soutLevel;
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
	public AppInitOptions getInitOptions()
	{
		if (started) {
			throw new IllegalStateException("Cannot alter init options after starting the App.");
		}
		
		return opt;
	}
	
	
	public BaseApp(File workdir, boolean singleInstance) {
		
		WorkDir.init(workdir);
		
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
		Log.setMainLogger(log);
		Log.setLevel(opt.logLevel);
		Log.setSysoutLevel(opt.logSoutLevel);
		
		// connect slickutil to the logger
		org.newdawn.slick.util.Log.setLogSystem(new SlickLogRedirector(log));
		writeLogHeader();
		
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
	
	
	protected void writeLogHeader()
	{
		logSystemInfo();
	}
	
	
	protected void logSystemInfo()
	{
		String txt = "";
		
		txt += "\n### SYSTEM INFO ###\n\n";
		txt += " Platform ...... " + System.getProperty("os.name") + "\n";
		txt += " Runtime ....... " + System.getProperty("java.runtime.name") + "\n";
		txt += " Java .......... " + System.getProperty("java.version") + "\n";
		txt += " Launch path ... " + System.getProperty("user.dir") + "\n";
		
		try {
			txt += " Workdir ....... " + WorkDir.getWorkDir().getCanonicalPath() + "\n";
		} catch (final IOException e) {
			Log.e(e);
		}
		
		Log.i(txt);
	}
	
	
	protected void registerIonizables()
	{
		Ion.registerIndirect(255, new IonizerBinary<Coord>() {
			
			@Override
			public void save(Coord object, IonOutput out) throws IOException
			{
				out.writeInt(object.x);
				out.writeInt(object.y);
			}
			
			
			@Override
			public Coord load(IonInput in) throws IOException
			{
				final int x = in.readInt();
				final int y = in.readInt();
				return new Coord(x, y);
			}
			
		});
		
		Ion.registerIndirect(254, new IonizerBinary<Move>() {
			
			@Override
			public void save(Move object, IonOutput out) throws IOException
			{
				out.writeInt(object.x());
				out.writeInt(object.y());
			}
			
			
			@Override
			public Move load(IonInput in) throws IOException
			{
				final int x = in.readInt();
				final int y = in.readInt();
				return new Move(x, y);
			}
			
		});
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
		} catch (final Throwable e) {
			Log.e(e);
		}
		
		Log.i("Terminating...");
		System.exit(0);
	}
}
