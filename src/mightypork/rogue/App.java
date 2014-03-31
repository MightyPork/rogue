package mightypork.rogue;


import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

import javax.swing.JOptionPane;

import mightypork.rogue.bus.events.UpdateEvent;
import mightypork.rogue.display.DisplaySystem;
import mightypork.rogue.display.Screen;
import mightypork.rogue.display.ScreenTestAnimations;
import mightypork.rogue.input.InputSystem;
import mightypork.rogue.input.KeyStroke;
import mightypork.rogue.sounds.SoundSystem;
import mightypork.rogue.tasks.TaskTakeScreenshot;
import mightypork.rogue.util.Utils;
import mightypork.utils.logging.Log;
import mightypork.utils.logging.LogInstance;
import mightypork.utils.patterns.Destroyable;
import mightypork.utils.patterns.subscription.MessageBus;
import mightypork.utils.time.TimerDelta;

import org.lwjgl.input.Keyboard;


/**
 * Main class
 * 
 * @author MightyPork
 */
public class App implements Destroyable, AppAccess {

	/** instance pointer */
	private static App inst;

	private InputSystem input;
	private SoundSystem sounds;
	private DisplaySystem display;
	private MessageBus events;

	/** current screen */
	private Screen screen;

	/** Flag that screenshot is scheduled to be taken next loop */
	private boolean scheduledScreenshot = false;


	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Thread.setDefaultUncaughtExceptionHandler(new CrashHandler());

		inst = new App();

		try {
			inst.start();
		} catch (Throwable t) {
			onCrash(t);
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
		destroy();
		System.exit(0);
	}


	public void initialize()
	{
		Log.i("Initializing subsystems");
		initLock();
		initBus();
		initLogger();
		initDisplay();
		initSound();
		initInput();
	}


	@Override
	public void destroy()
	{
		if (sounds != null) sounds.destroy();
		if (input != null) input.destroy();
		if (display != null) display.destroy();
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
	private void initBus()
	{
		events = new MessageBus();
		events.subscribe(this);

		events.createChannel(UpdateEvent.class, UpdateEvent.Listener.class);
	}


	/**
	 * initialize sound system
	 */
	private void initSound()
	{
		sounds = new SoundSystem(this);
		sounds.setMasterVolume(1);
	}


	/**
	 * initialize inputs
	 */
	private void initInput()
	{
		input = new InputSystem(this);

		input.bindKeyStroke(new KeyStroke(Keyboard.KEY_F2), new Runnable() {

			@Override
			public void run()
			{
				Log.f3("F2, taking screenshot.");
				scheduledScreenshot = true;
			}
		});

		input.bindKeyStroke(new KeyStroke(false, Keyboard.KEY_F11), new Runnable() {

			@Override
			public void run()
			{
				Log.f3("F11, toggling fullscreen.");
				display.switchFullscreen();
			}
		});

		input.bindKeyStroke(new KeyStroke(Keyboard.KEY_LCONTROL, Keyboard.KEY_Q), new Runnable() {

			@Override
			public void run()
			{
				Log.f3("CTRL+Q, shutting down.");
				shutdown();
			}
		});
	}


	/**
	 * initialize display
	 */
	private void initDisplay()
	{
		display = new DisplaySystem(this);
		display.createMainWindow(Const.WINDOW_W, Const.WINDOW_H, true, Config.START_IN_FS, Const.TITLEBAR);
		display.setTargetFps(Const.FPS_RENDER);
	}


	/**
	 * initialize main logger
	 */
	private void initLogger()
	{
		LogInstance li = Log.create("runtime", Paths.LOGS, 10);
		li.enable(Config.LOGGING_ENABLED);
		li.enableSysout(Config.LOG_TO_STDOUT);
	}


	private void start()
	{
		initialize();
		mainLoop();
		shutdown();
	}

	/** timer */
	private TimerDelta timerRender;


	private void mainLoop()
	{
		screen = new ScreenTestAnimations(this);
		screen.setActive(true);

		timerRender = new TimerDelta();

		while (!display.isCloseRequested()) {
			display.beginFrame();

			events.broadcast(new UpdateEvent(timerRender.getDelta()));

			if (scheduledScreenshot) {
				takeScreenshot();
				scheduledScreenshot = false;
			}

			display.endFrame();
		}
	}


	/**
	 * Do take a screenshot
	 */
	public void takeScreenshot()
	{
		sounds.getEffect("gui.shutter").play(1);
		Utils.runAsThread(new TaskTakeScreenshot(display));
	}


	//
	//  static accessors
	//

	/**
	 * @return sound system of the running instance
	 */
	@Override
	public SoundSystem snd()
	{
		return sounds;
	}


	/**
	 * @return input system of the running instance
	 */
	@Override
	public InputSystem input()
	{
		return input;
	}


	/**
	 * @return display system of the running instance
	 */
	@Override
	public DisplaySystem disp()
	{
		return display;
	}


	/**
	 * @return event bus
	 */
	@Override
	public MessageBus bus()
	{
		return events;
	}

}
