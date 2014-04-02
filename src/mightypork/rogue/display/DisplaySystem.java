package mightypork.rogue.display;


import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import mightypork.rogue.AppAccess;
import mightypork.rogue.bus.Subsystem;
import mightypork.rogue.bus.events.ScreenChangeEvent;
import mightypork.utils.logging.Log;
import mightypork.utils.math.constraints.ConstraintContext;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.coord.Rect;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class DisplaySystem extends Subsystem implements ConstraintContext {
	
	private DisplayMode windowDisplayMode;
	private int targetFps;
	
	
	public DisplaySystem(AppAccess app) {
		super(app);
		
		initChannels();
	}
	
	
	@Override
	protected void deinit()
	{
		Display.destroy();
	}
	
	
	/**
	 * Initialize event channels
	 */
	private void initChannels()
	{
		bus().createChannel(ScreenChangeEvent.class, ScreenChangeEvent.Listener.class);
	}
	
	
	public void setTargetFps(int fps)
	{
		this.targetFps = fps;
	}
	
	
	public void createMainWindow(int width, int height, boolean resizable, boolean fullscreen, String title)
	{
		try {
			Display.setDisplayMode(windowDisplayMode = new DisplayMode(width, height));
			Display.setResizable(resizable);
			Display.setVSyncEnabled(true);
			Display.setTitle(title);
			Display.create();
			
			if (fullscreen) {
				switchFullscreen();
				Display.update();
			}
		} catch (LWJGLException e) {
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
			
			bus().broadcast(new ScreenChangeEvent(true, Display.isFullscreen(), getSize()));
			
		} catch (Throwable t) {
			Log.e("Failed to toggle fullscreen mode.", t);
			try {
				Display.setDisplayMode(windowDisplayMode);
				Display.update();
			} catch (Throwable t1) {
				throw new RuntimeException("Failed to revert failed fullscreen toggle.", t1);
			}
		}
	}
	
	
	public Screenshot takeScreenshot()
	{
		glReadBuffer(GL_FRONT);
		int width = Display.getDisplayMode().getWidth();
		int height = Display.getDisplayMode().getHeight();
		int bpp = 4; // Assuming a 32-bit display with a byte each for red,
						// green, blue, and alpha.
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		Screenshot sc = new Screenshot(width, height, bpp, buffer);
		
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
	public boolean isFullscreen()
	{
		return Display.isFullscreen();
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
	
	
	/**
	 * Start a OpenGL frame
	 */
	public void beginFrame()
	{
		// handle resize
		if (Display.wasResized()) {
			bus().broadcast(new ScreenChangeEvent(false, Display.isFullscreen(), getSize()));
		}
		
		glLoadIdentity();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
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
	
	public static class Screenshot {
		
		public int width;
		public int height;
		public int bpp;
		public ByteBuffer bytes;
		
		
		public Screenshot(int width, int height, int bpp, ByteBuffer buffer) {
			this.width = width;
			this.height = height;
			this.bpp = bpp;
			this.bytes = buffer;
		}
	}
}
