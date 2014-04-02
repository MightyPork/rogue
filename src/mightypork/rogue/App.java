package mightypork.rogue;


import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

import javax.swing.JOptionPane;

import mightypork.rogue.display.DisplaySystem;
import mightypork.rogue.display.Screen;
import mightypork.rogue.display.screens.screenBouncy.TestLayeredScreen;
import mightypork.rogue.input.InputSystem;
import mightypork.rogue.input.KeyStroke;
import mightypork.rogue.sounds.SoundSystem;
import mightypork.rogue.tasks.TaskTakeScreenshot;
import mightypork.rogue.textures.TextureRegistry;
import mightypork.rogue.util.Utils;
import mightypork.utils.control.bus.EventBus;
import mightypork.utils.control.bus.events.DestroyEvent;
import mightypork.utils.control.bus.events.UpdateEvent;
import mightypork.utils.control.timing.TimerDelta;
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
	private SoundSystem soundSystem;
	private DisplaySystem displaySystem;
	private EventBus eventBus;
	private TextureRegistry textureRegistry;
	
	/** current screen */
	private Screen screen;
	
	/** Flag that screenshot is scheduled to be taken next loop */
	private boolean scheduledScreenshot = false;
	
	/** Log instance; accessible as static via Log. */
	private LogInstance log;
	
	/** timer */
	private TimerDelta timerRender;
	
	
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
		mainLoop();
		shutdown();
	}
	
	
	/**
	 * App main loop
	 */
	private void mainLoop()
	{
		screen = new TestLayeredScreen(this);
		
		screen.setActive(true);
		
		timerRender = new TimerDelta();
		
		while (!displaySystem.isCloseRequested()) {
			displaySystem.beginFrame();
			
			eventBus.broadcast(new UpdateEvent(timerRender.getDelta()));
			
			if (scheduledScreenshot) {
				takeScreenshot();
				scheduledScreenshot = false;
			}
			
			displaySystem.endFrame();
		}
	}
	
	
	/**
	 * Handle a crash
	 * 
	 * @param error
	 */
	public static void onCrash(Throwable error)
	{
		Log.e("The game has crashed.", error);
		
		if (inst != null) inst.shutdown();
	}
	
	
	@Override
	public void shutdown()
	{
		bus().broadcast(new DestroyEvent());
		
		System.exit(0);
	}
	
	
	public void initialize()
	{
		Log.i("Initializing subsystems");
		
		// lock working directory
		initLock();
		
		// setup logging
		log = Log.create("runtime", Paths.LOGS, 10);
		log.enable(Config.LOGGING_ENABLED);
		log.enableSysout(Config.LOG_TO_STDOUT);
		
		// event bus
		eventBus = new EventBus();
		
		// Subsystems
		textureRegistry = new TextureRegistry(this);
		
		displaySystem = new DisplaySystem(this);
		displaySystem.createMainWindow(Const.WINDOW_W, Const.WINDOW_H, true, Config.START_IN_FS, Const.TITLEBAR);
		displaySystem.setTargetFps(Const.FPS_RENDER);
		
		soundSystem = new SoundSystem(this);
		soundSystem.setMasterVolume(1);
		
		inputSystem = new InputSystem(this);
		setupGlobalKeystrokes();
		
		// load resources
		Resources.load(this);
	}
	
	
	private void initLock()
	{
		if (!Config.SINGLE_INSTANCE) return;
		
		if (!lockInstance()) {
			System.out.println("Working directory is locked.\nOnly one instance can run at a time.");
			
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
	 * initialize inputs
	 */
	private void setupGlobalKeystrokes()
	{
		inputSystem.bindKeyStroke(new KeyStroke(Keyboard.KEY_F2), new Runnable() {
			
			@Override
			public void run()
			{
				Log.f3("F2, taking screenshot.");
				scheduledScreenshot = true;
			}
		});
		
		inputSystem.bindKeyStroke(new KeyStroke(false, Keyboard.KEY_F11), new Runnable() {
			
			@Override
			public void run()
			{
				Log.f3("F11, toggling fullscreen.");
				displaySystem.switchFullscreen();
			}
		});
		
		inputSystem.bindKeyStroke(new KeyStroke(Keyboard.KEY_LCONTROL, Keyboard.KEY_Q), new Runnable() {
			
			@Override
			public void run()
			{
				Log.f3("CTRL+Q, shutting down.");
				shutdown();
			}
		});
	}
	
	
	/**
	 * Do take a screenshot
	 */
	private void takeScreenshot()
	{
		soundSystem.getEffect("gui.shutter").play(1);
		Utils.runAsThread(new TaskTakeScreenshot(displaySystem));
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
	
	
	@Override
	public TextureRegistry tx()
	{
		return textureRegistry;
	}
	
}
