package mightypork.gamecore.app;


import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import mightypork.gamecore.Config;
import mightypork.gamecore.ConfigSetup;
import mightypork.gamecore.WorkDir;
import mightypork.gamecore.eventbus.EventBus;
import mightypork.gamecore.eventbus.events.DestroyEvent;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.gamecore.gui.screens.impl.CrossfadeOverlay;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.KeyConfig;
import mightypork.gamecore.input.KeySetup;
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
	
	// modules
	private InputSystem inputSystem;
	private DisplaySystem displaySystem;
	private SoundSystem soundSystem;
	private EventBus eventBus;
	private MainLoop gameLoop;
	private ScreenRegistry screenRegistry;
	
	private String logDirName = "log";
	private String logFilePrefix = "runtime";
	private int logArchiveCount = 0;
	private boolean busLogging = false;
	private String configFile = "settings.cfg";
	private String configComment = "Main config file";
	private final List<ResourceSetup> resourcesToLoad = new ArrayList<>();
	private final List<KeySetup> keysToLoad = new ArrayList<>();
	private final List<ConfigSetup> cfgsToLoad = new ArrayList<>();
	private ResourceLoader resourceLoader = new AsyncResourceLoader();
	private Level logLevel = Level.ALL;
	
	
	public BaseApp(File workdir, boolean singleInstance)
	{
		WorkDir.init(workdir);
		
		if (singleInstance) initLock();
	}
	
	
	public void setConfigFile(String filename, String comment)
	{
		this.configFile = filename;
		this.configComment = comment;
	}
	
	
	public void setLogOptions(String logDir, String filePrefix, int archived, Level logLevel)
	{
		this.logDirName = logDir;
		this.logFilePrefix = filePrefix;
		this.logArchiveCount = archived;
		this.logLevel = logLevel;
	}
	
	
	public void setBusLogging(boolean yes)
	{
		this.busLogging = yes;
	}
	
	
	public void addResources(ResourceSetup res)
	{
		this.resourcesToLoad.add(res);
	}
	
	
	public void addKeys(KeySetup keys)
	{
		this.keysToLoad.add(keys);
	}
	
	
	public void addConfig(ConfigSetup cfg)
	{
		this.cfgsToLoad.add(cfg);
	}
	
	
	public void setResourceLoader(ResourceLoader resLoader)
	{
		this.resourceLoader = resLoader;
	}
	
	
	/**
	 * Start the application
	 */
	public final void start()
	{
		Thread.setDefaultUncaughtExceptionHandler(this);
		
		Log.i("Using workdir: " + WorkDir.getWorkDir());
		
		initialize();
		
		Log.i("Starting main loop...");
		
		// open first screen		
		gameLoop.start();
	}
	
	
	/**
	 * Init the app
	 */
	protected void initialize()
	{
		Config.init(WorkDir.getFile(configFile), configComment);
		for (final KeySetup l : keysToLoad) {
			KeyConfig.addKeyLayout(l);
		}
		KeyConfig.inst().addOptions(Config.getProp());
		for (final ConfigSetup cfgl : cfgsToLoad) {
			cfgl.addOptions(Config.getProp());
		}
		Config.load();
		
		
		/*
		 * Setup logging
		 */
		final LogWriter log = Log.create(logFilePrefix, new File(WorkDir.getDir(logDirName), logFilePrefix + ".log"), logArchiveCount);
		log.setLevel(logLevel);
		Log.setMainLogger(log);
		org.newdawn.slick.util.Log.setLogSystem(new SlickLogRedirector(log));
		
		
		Log.i("=== Starting initialization sequence ===");
		
		
		// hook
		Log.f2("Calling pre-init hook...");
		preInit();
		
		/*
		 * Event bus
		 */
		Log.f2("Starting Event Bus...");
		eventBus = new EventBus();
		eventBus.detailedLogging = busLogging;
		
		/*
		 * Ionizables
		 */
		Log.f3("initializing ION...");
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
		 * Resources should be registered to banks, and AsyncResourceLoader will load them.
		 */
		Log.f1("Loading resources...");
		resourceLoader.init(this);
		Res.init(this);
		for (final ResourceSetup rl : resourcesToLoad) {
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
	protected abstract MainLoop createMainLoop();
	
	
	/*
	 * Try to obtain lock.
	 */
	private void initLock()
	{
		final File lock = WorkDir.getFile(".lock");
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
				"Another instance is already running.",
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
	
	
	@DefaultImpl
	protected void beforeShutdown()
	{
	}
	
	
	@Override
	public final void uncaughtException(Thread t, Throwable e)
	{
		onCrash(e);
	}
	
	
	@DefaultImpl
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
			// ignore it
		}
		
		Log.i("Terminating...");
		System.exit(0);
	}
}
