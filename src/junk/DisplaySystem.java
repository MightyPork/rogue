//package junk;
//
//
//import static org.lwjgl.opengl.GL11.*;
//
//import java.nio.ByteBuffer;
//
//import mightypork.gamecore.backend.lwjgl.AwtScreenshot;
//import mightypork.gamecore.core.modules.AppAccess;
//import mightypork.gamecore.core.modules.AppModule;
//import mightypork.gamecore.render.events.DisplayReadyEvent;
//import mightypork.gamecore.render.events.ViewportChangeEvent;
//import mightypork.utils.logging.Log;
//import mightypork.utils.math.constraints.rect.Rect;
//import mightypork.utils.math.constraints.rect.RectBound;
//import mightypork.utils.math.constraints.vect.Vect;
//import mightypork.utils.math.timing.FpsMeter;
//
//import org.lwjgl.BufferUtils;
//import org.lwjgl.LWJGLException;
//import org.lwjgl.opengl.Display;
//import org.lwjgl.opengl.DisplayMode;
//
//
///**
// * Display system
// * 
// * @author Ondřej Hruška (MightyPork)
// */
//@Deprecated
//public class DisplaySystem extends AppModule implements RectBound {
//	
//	private DisplayMode windowDisplayMode;
//	private int targetFps;
//	private FpsMeter fpsMeter;
//	private boolean fullscreenSwitchRequested;
//	
//	/** Current screen size */
//	private static final Vect screenSize = new Vect() {
//		
//		@Override
//		public double y()
//		{
//			return Display.getHeight();
//		}
//		
//		
//		@Override
//		public double x()
//		{
//			return Display.getWidth();
//		}
//	};
//	
//	private static final Rect rect = Rect.make(screenSize);
//	
//	
//	/**
//	 * @param app app access
//	 */
//	public DisplaySystem(AppAccess app) {
//		super(app);
//	}
//	
//	
//	@Override
//	protected void deinit()
//	{
//		Display.destroy();
//	}
//	
//	
//	/**
//	 * Set target fps (for syncing in endFrame() call).<br>
//	 * With vsync enabled, the target fps may not be met.
//	 * 
//	 * @param fps requested fps
//	 */
//	public void setTargetFps(int fps)
//	{
//		this.targetFps = fps;
//	}
//	
//	
//	/**
//	 * Create a main window
//	 * 
//	 * @param width requested width
//	 * @param height requested height
//	 * @param resizable is resizable by the user
//	 * @param fullscreen is in fullscreen
//	 * @param title window title
//	 */
//	public void createMainWindow(int width, int height, boolean resizable, boolean fullscreen, String title)
//	{
//		try {
//			Display.setDisplayMode(windowDisplayMode = new DisplayMode(width, height));
//			Display.setResizable(resizable);
//			Display.setVSyncEnabled(true);
//			Display.setTitle(title);
//			Display.create();
//			
//			fpsMeter = new FpsMeter();
//			
//			if (fullscreen) {
//				switchFullscreen();
//				Display.update();
//			}
//			
//			getEventBus().send(new DisplayReadyEvent());
//			
//		} catch (final LWJGLException e) {
//			throw new RuntimeException("Could not initialize screen", e);
//		}
//	}
//	
//	
//	/**
//	 * Toggle FS if possible
//	 */
//	public void switchFullscreen()
//	{
//		fullscreenSwitchRequested = true;
//	}
//	
//	
//	private void doSwitchFullscreen()
//	{
//		try {
//			
//			if (!Display.isFullscreen()) {
//				Log.f3("Entering fullscreen.");
//				// save window resize
//				windowDisplayMode = new DisplayMode(Display.getWidth(), Display.getHeight());
//				
//				Display.setDisplayMode(Display.getDesktopDisplayMode());
//				Display.setFullscreen(true);
//				Display.update();
//			} else {
//				Log.f3("Leaving fullscreen.");
//				Display.setDisplayMode(windowDisplayMode);
//				Display.update();
//			}
//			
//			getEventBus().send(new ViewportChangeEvent(getSize()));
//			
//		} catch (final Throwable t) {
//			Log.e("Failed to toggle fullscreen mode.", t);
//			try {
//				Display.setDisplayMode(windowDisplayMode);
//				Display.update();
//			} catch (final Throwable t1) {
//				throw new RuntimeException("Failed to revert failed fullscreen toggle.", t1);
//			}
//		}
//	}
//	
//	
//	/**
//	 * Take screenshot (expensive processing is done on-demand when screenshot
//	 * is processed).
//	 * 
//	 * @return screenshot object
//	 */
//	public static AwtScreenshot prepareScreenshot()
//	{
//		glReadBuffer(GL_FRONT);
//		final int width = Display.getWidth();
//		final int height = Display.getHeight();
//		final int bpp = 4;
//		final ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
//		glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
//		
//		final AwtScreenshot sc = new AwtScreenshot(width, height, bpp, buffer);
//		
//		return sc;
//	}
//	
//	
//	/**
//	 * @return true if close was requested (i.e. click on cross)
//	 */
//	public static boolean isCloseRequested()
//	{
//		return Display.isCloseRequested();
//	}
//	
//	
//	/**
//	 * Get fullscreen state
//	 * 
//	 * @return is fullscreen
//	 */
//	public static boolean isFullscreen()
//	{
//		return Display.isFullscreen();
//	}
//	
//	
//	/**
//	 * Get screen size. This Vect is final and views at it can safely be made.
//	 * 
//	 * @return size
//	 */
//	public static Vect getSize()
//	{
//		return screenSize;
//	}
//	
//	
//	/**
//	 * Get screen rect. Static version of getRect().
//	 * 
//	 * @return size
//	 */
//	public static Rect getBounds()
//	{
//		return rect;
//	}
//	
//	
//	/**
//	 * @return screen width
//	 */
//	public static int getWidth()
//	{
//		return screenSize.xi();
//	}
//	
//	
//	/**
//	 * @return screen height
//	 */
//	public static int getHeight()
//	{
//		return screenSize.yi();
//	}
//	
//	
//	/**
//	 * Start a OpenGL frame
//	 */
//	public void beginFrame()
//	{
//		// handle resize
//		if (Display.wasResized()) {
//			getEventBus().send(new ViewportChangeEvent(getSize()));
//		}
//		
//		if (fullscreenSwitchRequested) {
//			fullscreenSwitchRequested = false;
//			doSwitchFullscreen();
//		}
//		
//		glLoadIdentity();
//		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//		fpsMeter.frame();
//	}
//	
//	
//	/**
//	 * End an OpenGL frame, flip buffers, sync to fps.
//	 */
//	public void endFrame()
//	{
//		Display.update(false); // don't poll input devices
//		Display.sync(targetFps);
//	}
//	
//	
//	/**
//	 * Get screen rect. This Rect is final and views at it can safely be made.
//	 */
//	@Override
//	public Rect getRect()
//	{
//		return getBounds();
//	}
//	
//	
//	/**
//	 * @return current FPS
//	 */
//	public long getFps()
//	{
//		return fpsMeter.getFPS();
//	}
//	
//	
//	/**
//	 * Get screen center. This vect is final and views at it can safely be made.
//	 * 
//	 * @return screen center.
//	 */
//	public static Vect getCenter()
//	{
//		return rect.center();
//	}
//}
