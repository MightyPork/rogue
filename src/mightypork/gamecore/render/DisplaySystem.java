package mightypork.gamecore.render;


import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.RootBusNode;
import mightypork.gamecore.control.bus.events.ScreenChangeEvent;
import mightypork.utils.logging.Log;
import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.coord.Rect;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class DisplaySystem extends RootBusNode implements RectConstraint {
	
	private DisplayMode windowDisplayMode;
	private int targetFps;
	public static boolean yAxisDown = true;
	
	
	public DisplaySystem(AppAccess app) {
		super(app);
	}
	
	
	@Override
	protected void deinit()
	{
		Display.destroy();
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
			
			Render.init();
			
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
			
			bus().send(new ScreenChangeEvent(true, Display.isFullscreen(), getSize()));
			
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
	
	
	public static Screenshot takeScreenshot()
	{
		glReadBuffer(GL_FRONT);
		final int width = Display.getDisplayMode().getWidth();
		final int height = Display.getDisplayMode().getHeight();
		final int bpp = 4; // Assuming a 32-bit display with a byte each for red,
							// green, blue, and alpha.
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
	
	
	public static int getWidth()
	{
		return Display.getWidth();
	}
	
	
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
			bus().send(new ScreenChangeEvent(false, Display.isFullscreen(), getSize()));
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
		
		private int width;
		private int height;
		private int bpp;
		private ByteBuffer bytes;
		private BufferedImage image;
		
		
		public Screenshot(int width, int height, int bpp, ByteBuffer buffer) {
			this.width = width;
			this.height = height;
			this.bpp = bpp;
			this.bytes = buffer;
		}
		
		
		public BufferedImage getImage()
		{
			if (image != null) return image;
			
			image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
			
			// convert to a buffered image
			for (int x = 0; x < this.width; x++) {
				for (int y = 0; y < this.height; y++) {
					final int i = (x + (this.width * y)) * this.bpp;
					final int r = this.bytes.get(i) & 0xFF;
					final int g = this.bytes.get(i + 1) & 0xFF;
					final int b = this.bytes.get(i + 2) & 0xFF;
					image.setRGB(x, this.height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
				}
			}
			
			return image;
		}
		
		
		public void save(File file) throws IOException
		{
			ImageIO.write(getImage(), "PNG", file);
		}
	}
	
	
	public static void setupOrtho()
	{
		// fix projection for changed size
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		final Coord s = getSize();
		glViewport(0, 0, s.xi(), s.yi());
		glOrtho(0, s.x, (yAxisDown ? 1 : -1) * s.y, 0, -1000, 1000);
		
		// back to modelview
		glMatrixMode(GL_MODELVIEW);
		
	}
}
