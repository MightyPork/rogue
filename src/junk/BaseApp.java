package junk;


import java.lang.Thread.UncaughtExceptionHandler;

import mightypork.gamecore.backends.lwjgl.LwjglInputModule;
import mightypork.gamecore.core.App;
import mightypork.gamecore.core.AppBackend;
import mightypork.gamecore.core.MainLoop;
import mightypork.gamecore.core.WorkDir;
import mightypork.gamecore.core.config.Config;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.gamecore.gui.screens.impl.CrossfadeOverlay;
import mightypork.gamecore.resources.Res;
import mightypork.gamecore.resources.ResourceSetup;
import mightypork.utils.logging.Log;


/**
 * Basic screen-based game with subsystems.<br>
 * This class takes care of the initialization sequence.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class BaseApp extends App implements UncaughtExceptionHandler {
	
	// modules
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
	
	
	public BaseApp(AppBackend backend) {
		super(backend);
	}
	
	
	/**
	 * Start the application
	 */
	@Override
	public final void start()
	{
		initialize();
		
		Log.i("Starting main loop...");
		
		// open first screen	!!!
		started = true;
		gameLoop.start();
	}
	
	
	/**
	 * Init the app
	 */
	protected void initialize()
	{
		WorkDir.init(opt.workdir);
		
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
		 * Display
		 */
		Log.f2("Initializing Display System...");
		initDisplay(gfx());
		
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
		inputSystem = new LwjglInputModule(this);
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
	
	
	protected void beforeShutdown()
	{
		// ???
		if (lockObtained) Config.save();
	}
}
