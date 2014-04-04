package mightypork.rogue;


import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import mightypork.rogue.audio.SoundSystem;
import mightypork.rogue.bus.events.*;
import mightypork.rogue.gui.screens.ScreenRegistry;
import mightypork.rogue.input.InputSystem;
import mightypork.rogue.input.KeyStroke;
import mightypork.rogue.render.DisplaySystem;
import mightypork.utils.control.bus.EventBus;
import mightypork.utils.control.bus.events.DestroyEvent;
import mightypork.utils.control.bus.events.UpdateEvent;
import mightypork.utils.control.interf.Destroyable;
import mightypork.utils.control.interf.Updateable;
import mightypork.utils.logging.Log;
import mightypork.utils.logging.LogInstance;

import org.lwjgl.input.Keyboard;


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
	private MainLoop mainLoop;
	
	public ScreenRegistry screens;
	
	
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
		} catch (Throwable t) {
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
		mainLoop.start();
	}
	
	
	/**
	 * Handle a crash
	 * 
	 * @param error
	 */
	public static void onCrash(Throwable error)
	{
		Log.e("The game has crashed!", error);
		
		if (inst != null) inst.shutdown();
	}
	
	
	@Override
	public void shutdown()
	{
		bus().send(new DestroyEvent());
		
		bus().destroy();
		
		Log.i("Shutting down...");
		
		System.exit(0);
	}
	
	
	public void initialize()
	{
		/*
		 *  Lock working directory
		 */
		initLock();
		
		/*
		 * Setup logging
		 */
		LogInstance log = Log.create("runtime", Paths.LOGS, 10);
		log.enable(Config.LOGGING_ENABLED);
		log.enableSysout(Config.LOG_TO_STDOUT);
		
		Log.f1("Initializing subsystems...");
		
		/*
		 * Event bus
		 */
		Log.f2("Initializing Event Bus...");
		eventBus = new EventBus();
		eventBus.enableLogging(Config.LOG_BUS);
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
		 * Screen registry
		 */
		Log.f2("Initializing screen registry...");
		screens = new ScreenRegistry(this);
		
		/*
		 * Load resources
		 */
		Log.f1("Registering resources...");
		Res.load(this);
		
		/*
		 * Prepare main loop
		 */
		Log.f1("Preparing main loop...");
		ArrayList<Runnable> loopTasks = new ArrayList<Runnable>();
		loopTasks.add(new Runnable() {
			
			@Override
			public void run()
			{
				screens.render();
			}
		});
		
		mainLoop = new MainLoop(this, loopTasks);
	}
	
	
	private void initChannels()
	{
		Log.f3("Registering channels...");
		
		bus().addChannel(DestroyEvent.class, Destroyable.class);
		bus().addChannel(UpdateEvent.class, Updateable.class);
		
		bus().addChannel(ScreenChangeEvent.class, ScreenChangeEvent.Listener.class);
		bus().addChannel(KeyboardEvent.class, KeyboardEvent.Listener.class);
		bus().addChannel(MouseMotionEvent.class, MouseMotionEvent.Listener.class);
		bus().addChannel(MouseButtonEvent.class, MouseButtonEvent.Listener.class);
		bus().addChannel(ScreenRequestEvent.class, ScreenRequestEvent.Listener.class);
		bus().addChannel(ActionRequest.class, ActionRequest.Listener.class);
	}
	
	
	private void setupGlobalKeystrokes()
	{
		input().bindKeyStroke(new KeyStroke(Keyboard.KEY_F11), new Runnable() {
			
			@Override
			public void run()
			{
				bus().queue(new ActionRequest(RequestType.FULLSCREEN));
			}
		});
		
		input().bindKeyStroke(new KeyStroke(Keyboard.KEY_F2), new Runnable() {
			
			@Override
			public void run()
			{
				bus().queue(new ActionRequest(RequestType.SCREENSHOT));
			}
		});
		
		input().bindKeyStroke(new KeyStroke(Keyboard.KEY_LCONTROL, Keyboard.KEY_Q), new Runnable() {
			
			@Override
			public void run()
			{
				bus().queue(new ActionRequest(RequestType.SHUTDOWN));
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
	
	
	private boolean lockInstance()
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
						} catch (Exception e) {
							System.err.println("Unable to remove lock file.");
							e.printStackTrace();
						}
					}
				});
				return true;
			}
		} catch (Exception e) {
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
