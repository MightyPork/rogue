package mightypork.gamecore.control;


import java.io.File;

import javax.swing.JOptionPane;

import mightypork.gamecore.audio.SoundSystem;
import mightypork.gamecore.control.bus.EventBus;
import mightypork.gamecore.control.bus.events.*;
import mightypork.gamecore.control.interf.Destroyable;
import mightypork.gamecore.control.interf.NoImpl;
import mightypork.gamecore.control.timing.Updateable;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.loading.AsyncResourceLoader;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.utils.files.InstanceLock;
import mightypork.utils.logging.Log;
import mightypork.utils.logging.LogInstance;


/**
 * Basic screen-based game with subsystems.<br>
 * This class takes care of the initialization sequence.
 * 
 * @author MightyPork
 */
public abstract class BaseApp implements AppAccess {
	
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
	public void start()
	{
		
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
		final LogInstance log = createLog();
		org.newdawn.slick.util.Log.setLogSystem(new SlickLogRedirector(log));
		
		// only here it makes sense to log.
		Log.i("=== Commencing initialization sequence ===");
		
		/*
		 * Event bus
		 */
		Log.f2("Starting Event Bus...");
		eventBus = new EventBus();
		
		Log.f3("Registering channels...");
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
	@NoImpl
	protected void preInit()
	{
	}
	
	
	/**
	 * 
	 */
	@NoImpl
	protected void postInit()
	{
	}
	
	
	/**
	 * Create and configure a log (using {@link Log})
	 * 
	 * @return new log instance
	 */
	protected abstract LogInstance createLog();
	
	
	/**
	 * Create window and configure display system
	 * 
	 * @param display
	 */
	protected abstract void initDisplay(DisplaySystem display);
	
	
	/**
	 * Configure sound system (ie. adjust volume)
	 * 
	 * @param audio
	 */
	protected abstract void initSoundSystem(SoundSystem audio);
	
	
	/**
	 * Configure input system (ie. define global keystrokes)
	 * 
	 * @param input
	 */
	protected abstract void initInputSystem(InputSystem input);
	
	
	/**
	 * Initialize resource banks; {@link AsyncResourceLoader} is already
	 * started.
	 */
	protected abstract void initResources();
	
	
	/**
	 * Register game screens to the registry.
	 * 
	 * @param screens
	 */
	protected abstract void initScreens(ScreenRegistry screens);
	
	
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
	protected void initBus(EventBus bus)
	{
		// framework events
		bus.addChannel(DestroyEvent.class, Destroyable.class);
		bus.addChannel(UpdateEvent.class, Updateable.class);
		
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
		System.err.println("Could not obtain lock file.\nOnly one instance can run at a time.");
		
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
	protected abstract File getLockFile();
	
	
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
	
	
	@Override
	public void shutdown()
	{
		Log.i("Shutting down subsystems...");
		
		if (getEventBus() != null) {
			getEventBus().send(new DestroyEvent());
			getEventBus().destroy();
		}
		
		Log.i("Terminating...");
		System.exit(0);
	}
}
