package mightypork.rogue;


import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.Locale;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import mightypork.gamecore.SlickLogRedirector;
import mightypork.gamecore.audio.SoundSystem;
import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.GameLoop;
import mightypork.gamecore.control.bus.EventBus;
import mightypork.gamecore.control.bus.events.*;
import mightypork.gamecore.control.interf.Destroyable;
import mightypork.gamecore.control.interf.Updateable;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.rogue.events.ActionRequest;
import mightypork.rogue.events.ActionRequest.RequestType;
import mightypork.rogue.screens.test_bouncyboxes.ScreenTestBouncy;
import mightypork.rogue.screens.test_cat_sound.ScreenTestCat;
import mightypork.rogue.screens.test_font.ScreenTestFont;
import mightypork.utils.logging.Log;
import mightypork.utils.logging.LogInstance;


/**
 * Main class
 * 
 * @author MightyPork
 */
public class App implements AppAccess {
	
	/** instance pointer */
	private static App inst;
	
	// modules
	private InputSystem inputSystem;
	private DisplaySystem displaySystem;
	private static SoundSystem soundSystem;
	private EventBus eventBus;
	private GameLoop mainLoop;
	private ScreenRegistry screens;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Config.init();
		
		Thread.setDefaultUncaughtExceptionHandler(new CrashHandler());
		
		inst = new App();
		
		try {
			inst.start();
		} catch (final Throwable t) {
			onCrash(t);
		}
		
	}
	
	
	/**
	 * Start the application
	 */
	private void start()
	{
		initialize();
		
		Log.i("Starting main loop...");
		
		// open first screen		
		mainLoop.start();
	}
	
	
	/**
	 * Handle a crash
	 * 
	 * @param error
	 */
	public static void onCrash(Throwable error)
	{
		if (Log.ready()) {
			Log.e("The game has crashed!", error);
		} else {
			System.err.println("The game has crashed!");
			error.printStackTrace();
		}
		
		if (inst != null) inst.shutdown();
	}
	
	
	@Override
	public void shutdown()
	{
		Log.i("Shutting down subsystems...");
		
		if (bus() != null) {
			bus().send(new DestroyEvent());
			bus().destroy();
		}
		
		Log.i("Terminating...");
		System.exit(0);
	}
	
	
	public void initialize()
	{
		// to get dot instead of comma in floats
		Locale.setDefault(Locale.ENGLISH);
		
		/*
		 *  Lock working directory
		 */
		initLock();
		
		/*
		 * Setup logging
		 */
		final LogInstance log = Log.create("runtime", Paths.LOGS, 10);
		log.setFileLevel(Level.WARNING);
		log.setSysoutLevel(Level.ALL);
		log.enable(Config.LOGGING_ENABLED);
		log.enableSysout(Config.LOG_TO_STDOUT);
		org.newdawn.slick.util.Log.setLogSystem(new SlickLogRedirector(log));
		
		Log.f1("Initializing subsystems...");
		
		/*
		 * Event bus
		 */
		Log.f2("Initializing Event Bus...");
		eventBus = new EventBus();
		eventBus.detailedLogging = true;
		initChannels();
		
		/*
		 * Display
		 */
		Log.f2("Initializing Display System...");
		displaySystem = new DisplaySystem(this);
		displaySystem.createMainWindow(Const.WINDOW_W, Const.WINDOW_H, true, Config.START_IN_FS, Const.TITLEBAR);
		displaySystem.setTargetFps(Const.FPS_RENDER);
		
		/*
		 * Audio
		 */
		Log.f2("Initializing Sound System...");
		soundSystem = new SoundSystem(this);
		soundSystem.setMasterVolume(1);
		
		/*
		 * Input
		 */
		Log.f2("Initializing Input System...");
		inputSystem = new InputSystem(this);
		setupGlobalKeystrokes();
		
		/*
		 * Prepare main loop
		 */
		Log.f1("Preparing game systems...");
		screens = new ScreenRegistry(this);
		mainLoop = new MainLoop(this, screens);
		
		/*
		 * Load resources
		 */
		Log.f1("Loading resources...");
		
		Res.load(this);
		
		/*
		 * Screen registry
		 */
		Log.f2("Initializing screens...");
		initScreens();
	}
	
	
	private void initScreens()
	{
		Log.f3("Registering game screens...");
		
		screens.add(new ScreenTestBouncy(this));
		screens.add(new ScreenTestCat(this));
		screens.add(new ScreenTestFont(this));
		
		screens.showScreen("test.bouncy");
	}
	
	
	private void initChannels()
	{
		Log.f3("Registering channels...");
		
		// framework events
		bus().addChannel(DestroyEvent.class, Destroyable.class);
		bus().addChannel(UpdateEvent.class, Updateable.class);
		
		// input events
		bus().addChannel(ScreenChangeEvent.class, ScreenChangeEvent.Listener.class);
		bus().addChannel(KeyboardEvent.class, KeyboardEvent.Listener.class);
		bus().addChannel(MouseMotionEvent.class, MouseMotionEvent.Listener.class);
		bus().addChannel(MouseButtonEvent.class, MouseButtonEvent.Listener.class);
		
		// control events
		bus().addChannel(ScreenRequestEvent.class, ScreenRequestEvent.Listener.class);
		bus().addChannel(ResourceLoadRequest.class, ResourceLoadRequest.Listener.class);
		bus().addChannel(ActionRequest.class, ActionRequest.Listener.class);
		bus().addChannel(MainLoopTaskRequest.class, MainLoopTaskRequest.Listener.class);
	}
	
	
	private void setupGlobalKeystrokes()
	{
		Log.f3("Setting up hot keys...");
		
		// Go fullscreen
		input().bindKeyStroke(new KeyStroke(Keys.KEY_F11), new Runnable() {
			
			@Override
			public void run()
			{
				bus().send(new ActionRequest(RequestType.FULLSCREEN));
			}
		});
		
		// Take screenshot
		input().bindKeyStroke(new KeyStroke(Keys.KEY_F2), new Runnable() {
			
			@Override
			public void run()
			{
				bus().send(new ActionRequest(RequestType.SCREENSHOT));
			}
		});
		
		// Exit
		input().bindKeyStroke(new KeyStroke(Keys.KEY_LCONTROL, Keys.KEY_Q), new Runnable() {
			
			@Override
			public void run()
			{
				bus().send(new ActionRequest(RequestType.SHUTDOWN));
			}
		});
	}
	
	
	private void initLock()
	{
		if (!Config.SINGLE_INSTANCE) return;
		
		if (!lockInstance()) {
			System.err.println("Working directory is locked.\nOnly one instance can run at a time.");
			
			//@formatter:off
			JOptionPane.showMessageDialog(
					null,
					"The game is already running.",
					"Instance error",
					JOptionPane.ERROR_MESSAGE
			);
			//@formatter:on
			
			shutdown();
			return;
		}
	}
	
	
	private static boolean lockInstance()
	{
		final File lockFile = new File(Paths.WORKDIR, ".lock");
		try {
			
			final RandomAccessFile randomAccessFile = new RandomAccessFile(lockFile, "rw");
			
			final FileLock fileLock = randomAccessFile.getChannel().tryLock();
			if (fileLock != null) {
				Runtime.getRuntime().addShutdownHook(new Thread() {
					
					@Override
					public void run()
					{
						try {
							fileLock.release();
							randomAccessFile.close();
							lockFile.delete();
						} catch (final Exception e) {
							System.err.println("Unable to remove lock file.");
							e.printStackTrace();
						}
					}
				});
				return true;
			}
			
		} catch (final Exception e) {
			System.err.println("Unable to create and/or lock file.");
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * @return sound system of the running instance
	 */
	@Override
	public SoundSystem snd()
	{
		return soundSystem;
	}
	
	
	/**
	 * @return input system of the running instance
	 */
	@Override
	public InputSystem input()
	{
		return inputSystem;
	}
	
	
	/**
	 * @return display system of the running instance
	 */
	@Override
	public DisplaySystem disp()
	{
		return displaySystem;
	}
	
	
	/**
	 * @return event bus
	 */
	@Override
	public EventBus bus()
	{
		return eventBus;
	}
	
}
