package mightypork.gamecore.control;


import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import mightypork.gamecore.audio.SoundSystem;
import mightypork.gamecore.control.bus.EventBus;
import mightypork.gamecore.control.bus.events.*;
import mightypork.gamecore.control.interf.Destroyable;
import mightypork.gamecore.control.timing.Pollable;
import mightypork.gamecore.control.timing.Updateable;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.loading.AsyncResourceLoader;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.utils.annotations.DefaultImpl;
import mightypork.utils.files.InstanceLock;
import mightypork.utils.logging.Log;
import mightypork.utils.logging.LogWriter;


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
	private GameLoop gameLoop;
	private ScreenRegistry screenRegistry;
	
	
	/**
	 * Start the application
	 */
	public final void start()
	{
		Thread.setDefaultUncaughtExceptionHandler(this);
		
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
		/*
		 *  Lock working directory
		 */
		initLock();
		
		// hook
		preInit();
		
		/*
		 * Setup logging
		 */
		final LogWriter log = createLog();
		if (log != null) {
			Log.setMainLogger(log);
			org.newdawn.slick.util.Log.setLogSystem(new SlickLogRedirector(log));
		}
		
		// only here it makes sense to log.
		Log.i("=== Commencing initialization sequence ===");
		
		/*
		 * Event bus
		 */
		Log.f2("Starting Event Bus...");
		eventBus = new EventBus();
		
		Log.f3("Registering channels...");
		initDefaultBusChannels(eventBus);
		initBus(eventBus);
		
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
		gameLoop = createLoop();
		gameLoop.setRootRenderable(screenRegistry);
		
		/*
		 * Load resources
		 * 
		 * Resources should be registered to banks, and AsyncResourceLoader will load them.
		 */
		Log.f1("Loading resources...");
		AsyncResourceLoader.launch(this);
		initResources();
		
		/*
		 * Screen registry
		 * 
		 * Must be after resources, because screens can request them during instantiation.
		 */
		Log.f2("Registering screens...");
		initScreens(screenRegistry);
		
		postInit();
		Log.i("=== Initialized sequence completed ===");
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
	 * Create and configure a log (using {@link Log})
	 * 
	 * @return new log instance
	 */
	@DefaultImpl
	protected LogWriter createLog()
	{
		final LogWriter log = Log.create("runtime", new File("runtime.log"));
		log.setLevel(Level.ALL);
		return log;
	}
	
	
	/**
	 * Create window and configure display system
	 * 
	 * @param display
	 */
	@DefaultImpl
	protected void initDisplay(DisplaySystem display)
	{
		display.createMainWindow(800, 600, true, false, "BaseApp using LWJGL display.");
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
	 * Configure input system (ie. define global keystrokes)
	 * 
	 * @param input
	 */
	@DefaultImpl
	protected void initInputSystem(InputSystem input)
	{
	}
	
	
	/**
	 * Initialize resource banks; {@link AsyncResourceLoader} is already
	 * started.
	 */
	@DefaultImpl
	protected void initResources()
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
	}
	
	
	/**
	 * Create game loop instance
	 * 
	 * @return the game loop.
	 */
	protected abstract GameLoop createLoop();
	
	
	/**
	 * Initialize event bus (ie. add custom channels)<br>
	 * When overriding, must call super!
	 * 
	 * @param bus
	 */
	@DefaultImpl
	protected void initBus(EventBus bus)
	{
	}
	
	
	private void initDefaultBusChannels(EventBus bus)
	{
		// framework events
		bus.addChannel(DestroyEvent.class, Destroyable.class);
		bus.addChannel(UpdateEvent.class, Updateable.class);
		bus.addChannel(LayoutChangeEvent.class, Pollable.class);
		
		// input events
		bus.addChannel(ScreenChangeEvent.class, ScreenChangeEvent.Listener.class);
		bus.addChannel(KeyEvent.class, KeyEvent.Listener.class);
		bus.addChannel(MouseMotionEvent.class, MouseMotionEvent.Listener.class);
		bus.addChannel(MouseButtonEvent.class, MouseButtonEvent.Listener.class);
		
		// control events
		bus.addChannel(ScreenRequestEvent.class, ScreenRequestEvent.Listener.class);
		bus.addChannel(ResourceLoadRequest.class, ResourceLoadRequest.Listener.class);
		bus.addChannel(MainLoopTaskRequest.class, MainLoopTaskRequest.Listener.class);
	}
	
	
	/*
	 * Try to obtain lock.
	 */
	private void initLock()
	{
		final File lockFile = getLockFile();
		
		if (lockFile == null) {
			// lock off
			return;
		}
		
		if (!InstanceLock.onFile(lockFile)) {
			onLockError();
			return;
		}
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
	
	
	/**
	 * Get lock file path; Used to enforce single-instance policy.
	 * 
	 * @return lock file, or null to disable lock.
	 */
	@DefaultImpl
	protected File getLockFile()
	{
		return new File(".lock");
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
