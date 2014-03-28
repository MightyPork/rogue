package mightypork.rogue;


import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileLock;

import javax.swing.JOptionPane;

import mightypork.rogue.input.Keys;
import mightypork.rogue.screens.ScreenSplash;
import mightypork.rogue.sounds.SoundManager;
import mightypork.rogue.threads.ThreadSaveScreenshot;
import mightypork.rogue.threads.ThreadScreenshotTrigger;
import mightypork.utils.logging.Log;
import mightypork.utils.logging.LogInstance;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.time.TimerDelta;
import mightypork.utils.time.TimerInterpolating;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class App {

	/** instance */
	public static App inst;
	/** Current delta time (secs since last render) */
	public static double currentDelta = 0;

	private static DisplayMode windowDisplayMode = null;

	/** current screen */
	public static Screen screen = null;

	/** Flag that screenshot is scheduled to be taken next loop */
	public static boolean scheduledScreenshot = false;


	private static boolean lockInstance()
	{
		if (Config.SINGLE_INSTANCE == false) return true; // bypass lock

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
							System.out.println("Unable to remove lock file.");
							e.printStackTrace();
						}
					}
				});
				return true;
			}
		} catch (Exception e) {
			System.out.println("Unable to create and/or lock file.");
			e.printStackTrace();
		}
		return false;
	}


	/**
	 * Is if FS
	 * 
	 * @return is in fs
	 */
	public static boolean isFullscreen()
	{
		return Display.isFullscreen();
	}


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
	 * Show crash report dialog with error stack trace.
	 * 
	 * @param error
	 */
	public static void onCrash(Throwable error)
	{
		Log.e("The game has crashed.");

		Log.e(error);

		try {
			inst.deinit();
		} catch (Throwable t) {
			// ignore
		}
	}


	/**
	 * Quit to OS
	 */
	public void exit()
	{
		deinit();
		System.exit(0);
	}


	/**
	 * Get current screen
	 * 
	 * @return screen
	 */
	public Screen getScreen()
	{
		return screen;
	}


	/**
	 * Get screen size
	 * 
	 * @return size
	 */
	public Coord getSize()
	{
		return new Coord(Display.getWidth(), Display.getHeight());
	}


	private void init() throws LWJGLException
	{
		// initialize main logger
		LogInstance li = Log.create("runtime", Paths.LOGS, 10);
		li.enable(Config.LOGGING_ENABLED);
		li.enableSysout(Config.LOG_TO_STDOUT);

		// initialize display
		Display.setDisplayMode(windowDisplayMode = new DisplayMode(Const.WINDOW_SIZE_X, Const.WINDOW_SIZE_Y));
		Display.setResizable(true);
		Display.setVSyncEnabled(true);
		Display.setTitle(Const.TITLEBAR);
		Display.create();

		if (Config.START_IN_FS) {
			switchFullscreen();
			Display.update();
		}

		// initialize inputs		
		Mouse.create();
		Keyboard.create();
		Keyboard.enableRepeatEvents(false);

		// initialize sound system
		SoundManager.get().setListener(Const.LISTENER_POS);
		SoundManager.get().setMasterVolume(1F);

		// start async screenshot trigger listener
		(new ThreadScreenshotTrigger()).start();
	}


	private void start() throws LWJGLException
	{

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

			exit();
			return;
		}

		init();
		mainLoop();
		deinit();
	}


	private void deinit()
	{
		Display.destroy();
		Mouse.destroy();
		Keyboard.destroy();
		SoundManager.get().destroy();
		AL.destroy();
	}

	/** timer */
	private TimerDelta timerRender;
	private TimerInterpolating timerGui;

	private int timerAfterResize = 0;


	private void mainLoop()
	{
		screen = new ScreenSplash();

		screen.init();

		timerRender = new TimerDelta();
		timerGui = new TimerInterpolating(Const.FPS_GUI_UPDATE);

		while (!Display.isCloseRequested()) {
			glLoadIdentity();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			timerGui.sync();

			int ticks = timerGui.getSkipped();

			if (ticks >= 1) {
				screen.updateGui();
				timerGui.startNewFrame();
			}

			currentDelta = timerRender.getDelta();

			// RENDER
			screen.render(currentDelta);
			SoundManager.get().update(currentDelta);

			Display.update();

			if (scheduledScreenshot) {
				takeScreenshot();
				scheduledScreenshot = false;
			}

			if (Keys.justPressed(Keyboard.KEY_F11)) {
				Log.f2("F11, toggle fullscreen.");
				switchFullscreen();
				screen.onFullscreenChange();
				Keys.destroyChangeState(Keyboard.KEY_F11);
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
					Log.f2("Ctrl+Q, force quit.");
					Keys.destroyChangeState(Keyboard.KEY_Q);
					exit();
					return;
				}

//				if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
//					Log.f2("Ctrl+M, go to main menu.");
//					Keys.destroyChangeState(Keyboard.KEY_M);
//					replaceScreen(new ScreenMenuMain());
//				}

				if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
					Log.f2("Ctrl+F, switch fullscreen.");
					Keys.destroyChangeState(Keyboard.KEY_F);
					switchFullscreen();
					screen.onFullscreenChange();
				}
			}

			if (Display.wasResized()) {
				screen.onWindowResize();
				timerAfterResize = 0;
			} else {				// ensure screen has even size
				timerAfterResize++;
				if (timerAfterResize > Const.FPS_GUI_UPDATE * 0.3) {
					timerAfterResize = 0;
					int x = Display.getX();
					int y = Display.getY();

					int w = Display.getWidth();
					int h = Display.getHeight();
					if (w % 2 != 0 || h % 2 != 0) {
						try {
							Display.setDisplayMode(windowDisplayMode = new DisplayMode(w - w % 2, h - h % 2));
							screen.onWindowResize();
							Display.setLocation(x, y);
						} catch (LWJGLException e) {
							e.printStackTrace();
						}
					}
				}
			}

			try {
				Display.sync(Const.FPS_RENDER);
			} catch (Throwable t) {
				Log.e("Your graphics card driver does not support fullscreen properly.", t);

				try {
					Display.setDisplayMode(windowDisplayMode);
				} catch (LWJGLException e) {
					Log.e("Error going back from corrupted fullscreen.");
					onCrash(e);
				}
			}
		}
	}


// UPDATE LOOP END

	/**
	 * Do take a screenshot
	 */
	public void takeScreenshot()
	{
		//Effects.play("gui.screenshot");

		glReadBuffer(GL_FRONT);
		int width = Display.getDisplayMode().getWidth();
		int height = Display.getDisplayMode().getHeight();
		int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		(new ThreadSaveScreenshot(buffer, width, height, bpp)).start();
	}


	/**
	 * Replace screen
	 * 
	 * @param newScreen new screen
	 */
	public void replaceScreen(Screen newScreen)
	{
		screen = newScreen;
		screen.init();
	}


	/**
	 * Replace screen, don't init it
	 * 
	 * @param newScreen new screen
	 */
	public void replaceScreenNoInit(Screen newScreen)
	{
		screen = newScreen;
	}


	/**
	 * Toggle FS if possible
	 */
	public void switchFullscreen()
	{
		try {
			if (!Display.isFullscreen()) {
				Log.f3("Entering fullscreen.");
				// save window resize
				windowDisplayMode = new DisplayMode(Display.getWidth(), Display.getHeight());

				Display.setDisplayMode(Display.getDesktopDisplayMode());
				Display.setFullscreen(true);
				Display.update();
//				
//				
//				DisplayMode mode = Display.getDesktopDisplayMode(); //findDisplayMode(WIDTH, HEIGHT);
//				Display.setDisplayModeAndFullscreen(mode);
			} else {
				Log.f3("Leaving fullscreen.");
				Display.setDisplayMode(windowDisplayMode);
				Display.update();
			}
		} catch (Throwable t) {
			Log.e("Failed to toggle fullscreen mode.", t);
			try {
				Display.setDisplayMode(windowDisplayMode);
				Display.update();
			} catch (Throwable t1) {
				onCrash(t1);
			}
		}
	}
}
