package mightypork.gamecore.backends.lwjgl.graphics;


import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;
import java.util.Stack;

import mightypork.gamecore.core.App;
import mightypork.gamecore.graphics.GraphicsModule;
import mightypork.gamecore.graphics.Screenshot;
import mightypork.gamecore.graphics.textures.DeferredTexture;
import mightypork.gamecore.graphics.textures.TxQuad;
import mightypork.gamecore.gui.events.ViewportChangeEvent;
import mightypork.utils.logging.Log;
import mightypork.utils.math.color.Color;
import mightypork.utils.math.color.Grad;
import mightypork.utils.math.color.pal.RGB;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.caching.RectDigest;
import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.math.timing.FpsMeter;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


/**
 * LWJGL rendering module
 * 
 * @author MightyPork
 */
public class LwjglGraphicsModule extends GraphicsModule {
	
	/** Currently binded color */
	private Color activeColor = null;
	/** Currently binded color's alpha multiplier */
	private double activeColorAlpha = 1;
	/** Stack of pushed colors */
	private Stack<Color> colorPushStack = new Stack<>();
	/** Currently binded texture */
	private SlickTexture activeTexture;
	
	/** Display mode used currently for the window */
	private DisplayMode windowDisplayMode;
	/** FPS the user wants */
	private int targetFps;
	/** FPS meter used for measuring actual FPS */
	private FpsMeter fpsMeter = new FpsMeter();
	
	/** Flag that at the end of frame, fullscreen should be toggled. */
	private boolean fullscreenToggleRequested;
	/** Flag that at the end of frame, fullscreen should be set. */
	private boolean fullscreenSetRequested;
	/** State to which fullscreen should be set. */
	private boolean fullscreenSetState;
	
	/** Current screen size */
	private static final Vect screenSize = new Vect() {
		
		@Override
		public double y()
		{
			return Display.getHeight();
		}
		
		
		@Override
		public double x()
		{
			return Display.getWidth();
		}
	};
	
	/** Current screen rectangle */
	private static final Rect rect = Rect.make(screenSize);
	
	
	@Override
	public void init()
	{
		try {
			Display.create();
		} catch (final Exception e) {
			throw new RuntimeException("Could not initialize display.", e);
		}
	}
	
	
	@Override
	public void setColor(Color color)
	{
		setColor(color, 1);
	}
	
	
	@Override
	public void setColor(Color color, double alpha)
	{
		if (color == null) color = RGB.WHITE;
		
		// color components can change over time - must use equals()
		if (activeColorAlpha == alpha && color.equals(activeColor)) return;
		
		activeColor = color;
		activeColorAlpha = alpha;
		GL11.glColor4d(color.r(), color.g(), color.b(), alpha * color.a());
	}
	
	
	@Override
	public void translate(double x, double y)
	{
		glTranslated(x, y, 0);
	}
	
	
	@Override
	public void translate(double x, double y, double z)
	{
		glTranslated(x, y, z);
	}
	
	
	@Override
	public void translate(Vect offset)
	{
		glTranslated(offset.x(), offset.y(), offset.z());
	}
	
	
	@Override
	public void translateXY(Vect offset)
	{
		glTranslated(offset.x(), offset.y(), 0);
	}
	
	
	@Override
	public void scale(double x, double y)
	{
		glScaled(x, y, 0);
	}
	
	
	@Override
	public void scale(double x, double y, double z)
	{
		glScaled(x, y, z);
	}
	
	
	@Override
	public void scale(Vect scale)
	{
		glScaled(scale.x(), scale.y(), scale.z());
	}
	
	
	@Override
	public void scaleXY(double scale)
	{
		glScaled(scale, scale, 1);
	}
	
	
	@Override
	public void scaleX(double scale)
	{
		glScaled(scale, 1, 1);
	}
	
	
	@Override
	public void scaleY(double scale)
	{
		glScaled(1, scale, 1);
	}
	
	
	@Override
	public void scaleZ(double scale)
	{
		glScaled(1, 1, scale);
	}
	
	
	@Override
	public void rotateX(double angle)
	{
		rotate(angle, AXIS_X);
	}
	
	
	@Override
	public void rotateY(double angle)
	{
		rotate(angle, AXIS_Y);
	}
	
	
	@Override
	public void rotateZ(double angle)
	{
		rotate(angle, AXIS_Z);
	}
	
	
	@Override
	public void rotate(double angle, Vect axis)
	{
		final Vect vec = axis.norm(1);
		glRotated(angle, vec.x(), vec.y(), vec.z());
	}
	
	
	@Override
	public void pushState()
	{
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glPushClientAttrib(GL11.GL_ALL_CLIENT_ATTRIB_BITS);
		GL11.glPushMatrix();
	}
	
	
	@Override
	public void popState()
	{
		GL11.glPopMatrix();
		GL11.glPopClientAttrib();
		GL11.glPopAttrib();
	}
	
	
	@Override
	public void pushGeometry()
	{
		GL11.glPushMatrix();
	}
	
	
	@Override
	public void popGeometry()
	{
		GL11.glPopMatrix();
	}
	
	
	@Override
	public void pushColor()
	{
		colorPushStack.push(activeColor);
	}
	
	
	@Override
	public void popColor()
	{
		setColor(colorPushStack.pop());
	}
	
	
	@Override
	public void quad(Rect rect)
	{
		final RectDigest q = rect.digest();
		
		// disable texture
		if (activeTexture != null) {
			activeTexture = null;
			glDisable(GL_TEXTURE_2D);
		}
		
		// quad
		glBegin(GL_QUADS);
		glVertex2d(q.left, q.bottom);
		glVertex2d(q.right, q.bottom);
		glVertex2d(q.right, q.top);
		glVertex2d(q.left, q.top);
		glEnd();
	}
	
	
	@Override
	public void quad(Rect rect, Color color)
	{
		setColor(color);
		quad(rect);
	}
	
	
	@Override
	public void quad(Rect rect, Grad grad)
	{
		final RectDigest r = rect.digest();
		
		// disable texture
		if (activeTexture != null) {
			activeTexture = null;
			glDisable(GL_TEXTURE_2D);
		}
		
		// quad
		glBegin(GL_QUADS);
		setColor(grad.leftBottom);
		glVertex2d(r.left, r.bottom);
		
		setColor(grad.rightBottom);
		glVertex2d(r.right, r.bottom);
		
		setColor(grad.rightTop);
		glVertex2d(r.right, r.top);
		
		setColor(grad.leftTop);
		glVertex2d(r.left, r.top);
		glEnd();
	}
	
	
	@Override
	public void quad(Rect rect, TxQuad txquad)
	{
		quad(rect, txquad, RGB.WHITE);
	}
	
	
	@Override
	public void quad(Rect rect, TxQuad txquad, Color color)
	{
		// texture is loaded uniquely -> can compare with ==
		if (activeTexture != txquad.tx) {
			glEnable(GL_TEXTURE_2D);
			activeTexture = (SlickTexture) txquad.tx;
			activeTexture.bind();
		}
		
		glBegin(GL_QUADS);
		setColor(color);
		
		final RectDigest q = rect.digest();
		final RectDigest u = txquad.uvs.digest();
		
		final double offs = 0.0001;// hack to avoid white stitching
		
		double tL = u.left + offs, tR = u.right - offs, tT = u.top + offs, tB = u.bottom - offs;
		
		// handle flip
		if (txquad.isFlippedY()) {
			final double swap = tT;
			tT = tB;
			tB = swap;
		}
		
		if (txquad.isFlippedX()) {
			final double swap = tL;
			tL = tR;
			tR = swap;
		}
		
		final double w = activeTexture.getWidth01();
		final double h = activeTexture.getHeight01();
		
		// quad with texture
		glTexCoord2d(tL * w, tB * h);
		glVertex2d(q.left, q.bottom);
		
		glTexCoord2d(tR * w, tB * h);
		glVertex2d(q.right, q.bottom);
		
		glTexCoord2d(tR * w, tT * h);
		glVertex2d(q.right, q.top);
		
		glTexCoord2d(tL * w, tT * h);
		glVertex2d(q.left, q.top);
		
		glEnd();
	}
	
	
	@Override
	public void setupProjection()
	{
		// fix projection for changed size
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		
		int w = Display.getWidth();
		int h = Display.getHeight();
		
		glViewport(0, 0, w, h);
		glOrtho(0, w, h, 0, -1000, 1000);
		
		// back to modelview
		glMatrixMode(GL_MODELVIEW);
		
		glLoadIdentity();
		
		glDisable(GL_LIGHTING);
		
		glClearDepth(1f);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		
		glEnable(GL_NORMALIZE);
		
		glShadeModel(GL_SMOOTH);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	
	@Override
	public DeferredTexture getLazyTexture(String path)
	{
		return new SlickTexture(path);
	}
	
	
	@Override
	public void destroy()
	{
		Display.destroy();
	}
	
	
	@Override
	public void setTargetFps(int fps)
	{
		this.targetFps = fps;
	}
	
	
	@Override
	public void setFullscreen(boolean fs)
	{
		fullscreenSetRequested = true;
		fullscreenSetState = fs;
	}
	
	
	@Override
	public void switchFullscreen()
	{
		fullscreenToggleRequested = true;
	}
	
	
	@Override
	public boolean isFullscreen()
	{
		return Display.isFullscreen();
	}
	
	
	private void doToggleFullscreen()
	{
		doSetFullscreen(!Display.isFullscreen());
	}
	
	
	private void doSetFullscreen(boolean fs)
	{
		try {
			
			if (Display.isFullscreen() == fs) return; // no work
				
			if (fs) {
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
			
			App.bus().send(new ViewportChangeEvent(getSize()));
			
		} catch (final Throwable t) {
			Log.e("Failed to change fullscreen mode.", t);
			try {
				Display.setDisplayMode(windowDisplayMode);
				Display.update();
			} catch (final Throwable t1) {
				throw new RuntimeException("Failed to revert failed fullscreen toggle.", t1);
			}
		}
	}
	
	
	@Override
	public Screenshot takeScreenshot()
	{
		glReadBuffer(GL_FRONT);
		final int width = Display.getWidth();
		final int height = Display.getHeight();
		final int bpp = 4;
		final ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		final AwtScreenshot sc = new AwtScreenshot(width, height, bpp, buffer);
		
		return sc;
	}
	
	
	@Override
	public void beginFrame()
	{
		// handle resize
		if (Display.wasResized()) {
			App.bus().send(new ViewportChangeEvent(getSize()));
		}
		
		if (fullscreenToggleRequested) {
			fullscreenToggleRequested = false;
			doToggleFullscreen();
		}
		
		if (fullscreenSetRequested) {
			fullscreenSetRequested = false;
			doSetFullscreen(fullscreenSetState);
		}
		
		glLoadIdentity();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		fpsMeter.frame();
	}
	
	
	@Override
	public void endFrame()
	{
		Display.update(false); // don't poll input devices
		Display.sync(targetFps);
	}
	
	
	@Override
	public void setSize(int width, int height)
	{
		try {
			Display.setDisplayMode(windowDisplayMode = new DisplayMode(width, height));
		} catch (LWJGLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public void setTitle(String title)
	{
		Display.setTitle(title);
	}
	
	
	@Override
	public void setVSync(boolean vsync)
	{
		Display.setVSyncEnabled(vsync);
	}
	
	
	@Override
	public void setResizable(boolean resizable)
	{
		Display.setResizable(resizable);
	}
	
	
	@Override
	public Rect getRect()
	{
		return rect;
	}
	
	
	@Override
	public long getFps()
	{
		return fpsMeter.getFPS();
	}
	
	
	@Override
	public Vect getCenter()
	{
		return rect.center();
	}
	
	
	@Override
	public int getWidth()
	{
		return Display.getWidth();
	}
	
	
	@Override
	public int getHeight()
	{
		return Display.getHeight();
	}
	
	
	@Override
	public Vect getSize()
	{
		return screenSize;
	}
	
}
