package mightypork.gamecore.render;


import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;

import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.AppModule;
import mightypork.gamecore.control.bus.events.ScreenChangeEvent;
import mightypork.gamecore.control.timing.FpsMeter;
import mightypork.utils.logging.Log;
import mightypork.utils.math.constraints.NumberConstraint;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.coord.Rect;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


/**
 * Display system
 * 
 * @author MightyPork
 */
public class DisplaySystem extends AppModule implements RectConstraint {
	
	private DisplayMode windowDisplayMode;
	private int targetFps;
	/** Y axis goes down (not up); Used also to adjust inputs. */
	public static boolean yAxisDown = true;
	private FpsMeter fpsMeter;
	
	
	/**
	 * @param app app access
	 */
	public DisplaySystem(AppAccess app) {
		super(app);
	}
	
	
	@Override
	protected void deinit()
	{
		Display.destroy();
	}
	
	
	/**
	 * Set target fps (for syncing in endFrame() call).<br>
	 * With vsync enabled, the target fps may not be met.
	 * 
	 * @param fps requested fps
	 */
	public void setTargetFps(int fps)
	{
		this.targetFps = fps;
	}
	
	
	/**
	 * Create a main window
	 * 
	 * @param width requested width
	 * @param height requested height
	 * @param resizable is resizable by the user
	 * @param fullscreen is in fullscreen
	 * @param title window title
	 */
	public void createMainWindow(int width, int height, boolean resizable, boolean fullscreen, String title)
	{
		try {
			Display.setDisplayMode(windowDisplayMode = new DisplayMode(width, height));
			Display.setResizable(resizable);
			Display.setVSyncEnabled(true);
			Display.setTitle(title);
			Display.create();
			
			fpsMeter = new FpsMeter();
			
			if (fullscreen) {
				switchFullscreen();
				Display.update();
			}
			
		} catch (final LWJGLException e) {
			throw new RuntimeException("Could not initialize screen", e);
		}
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
			} else {
				Log.f3("Leaving fullscreen.");
				Display.setDisplayMode(windowDisplayMode);
				Display.update();
			}
			
			getEventBus().send(new ScreenChangeEvent(true, Display.isFullscreen(), getSize()));
			
		} catch (final Throwable t) {
			Log.e("Failed to toggle fullscreen mode.", t);
			try {
				Display.setDisplayMode(windowDisplayMode);
				Display.update();
			} catch (final Throwable t1) {
				throw new RuntimeException("Failed to revert failed fullscreen toggle.", t1);
			}
		}
	}
	
	
	/**
	 * Take screenshot (expensive processing is done on-demand when screenshot
	 * is processed).
	 * 
	 * @return screenshot object
	 */
	public static Screenshot takeScreenshot()
	{
		glReadBuffer(GL_FRONT);
		final int width = Display.getDisplayMode().getWidth();
		final int height = Display.getDisplayMode().getHeight();
		final int bpp = 4;
		final ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		final Screenshot sc = new Screenshot(width, height, bpp, buffer);
		
		return sc;
	}
	
	
	/**
	 * @return true if close was requested (i.e. click on cross)
	 */
	public boolean isCloseRequested()
	{
		return Display.isCloseRequested();
	}
	
	
	/**
	 * Get fullscreen state
	 * 
	 * @return is fullscreen
	 */
	public static boolean isFullscreen()
	{
		return Display.isFullscreen();
	}
	
	
	/**
	 * Get screen size
	 * 
	 * @return size
	 */
	public static Coord getSize()
	{
		return new Coord(getWidth(), getHeight());
	}
	
	
	/**
	 * @return screen width
	 */
	public static int getWidth()
	{
		return Display.getWidth();
	}
	
	
	/**
	 * @return screen height
	 */
	public static int getHeight()
	{
		return Display.getHeight();
	}
	
	
	/**
	 * Start a OpenGL frame
	 */
	public void beginFrame()
	{
		// handle resize
		if (Display.wasResized()) {
			getEventBus().send(new ScreenChangeEvent(false, Display.isFullscreen(), getSize()));
		}
		
		glLoadIdentity();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		fpsMeter.frame();
	}
	
	
	/**
	 * End an OpenGL frame, flip buffers, sync to fps.
	 */
	public void endFrame()
	{
		Display.update(false); // don't poll input devices
		Display.sync(targetFps);
	}
	
	
	@Override
	public Rect getRect()
	{
		return new Rect(getSize());
	}
	
	
	/**
	 * @return current FPS
	 */
	public long getFps()
	{
		return fpsMeter.getFPS();
	}
	
	/** Screen width constraint */
	public static final NumberConstraint width = new NumberConstraint() {
		
		@Override
		public double getValue()
		{
			return getWidth();
		}
	};
	
	/** Screen height constaint */
	public static final NumberConstraint height = new NumberConstraint() {
		
		@Override
		public double getValue()
		{
			return getHeight();
		}
	};
}
