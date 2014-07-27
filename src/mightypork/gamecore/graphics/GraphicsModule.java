package mightypork.gamecore.graphics;


import mightypork.gamecore.core.BackendModule;
import mightypork.gamecore.graphics.textures.DeferredTexture;
import mightypork.gamecore.graphics.textures.TxQuad;
import mightypork.gamecore.gui.events.ViewportChangeEvent;
import mightypork.utils.math.color.Color;
import mightypork.utils.math.color.Grad;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.math.constraints.vect.VectConst;
import mightypork.utils.math.timing.FpsMeter;


/**
 * Render and display backend module.<br>
 * This module takes care of setting and getting screen size and parameters,
 * drawing on screen and timing render frames.
 * 
 * @author MightyPork
 */
public abstract class GraphicsModule extends BackendModule {
	
	protected static final VectConst AXIS_X = Vect.make(1, 0, 0);
	protected static final VectConst AXIS_Y = Vect.make(0, 1, 0);
	protected static final VectConst AXIS_Z = Vect.make(0, 0, 1);
	
	
	/**
	 * Set drawing color
	 * 
	 * @param color color
	 */
	public abstract void setColor(Color color);
	
	
	/**
	 * Set drawing color, adjust alpha
	 * 
	 * @param color color
	 * @param alpha alpha multiplier
	 */
	public abstract void setColor(Color color, double alpha);
	
	
	/**
	 * Translate by x, y
	 * 
	 * @param x x offset
	 * @param y y offset
	 */
	public abstract void translate(double x, double y);
	
	
	/**
	 * Translate by x, y, z
	 * 
	 * @param x x offset
	 * @param y y offset
	 * @param z z offset
	 */
	public abstract void translate(double x, double y, double z);
	
	
	/**
	 * Translate by offset vector
	 * 
	 * @param offset offset coordinate
	 */
	public abstract void translate(Vect offset);
	
	
	/**
	 * Translate by offset vector, ignore Z
	 * 
	 * @param offset offset coordinate
	 */
	public abstract void translateXY(Vect offset);
	
	
	/**
	 * Set scale for translations and coordinates
	 * 
	 * @param x x scale
	 * @param y y scale
	 */
	public abstract void scale(double x, double y);
	
	
	/**
	 * Set scale for translations and coordinates
	 * 
	 * @param x x scale
	 * @param y y scale
	 * @param z z scale
	 */
	public abstract void scale(double x, double y, double z);
	
	
	/**
	 * Set scale for translations and coordinates
	 * 
	 * @param scale vector
	 */
	public abstract void scale(Vect scale);
	
	
	/**
	 * Set scale for translations and coordinates (same value for X and Y scale)
	 * 
	 * @param scale scaling factor
	 */
	public abstract void scaleXY(double scale);
	
	
	/**
	 * Set X scale for translations and coordinates
	 * 
	 * @param scale scaling factor
	 */
	public abstract void scaleX(double scale);
	
	
	/**
	 * Set Y scale for translations and coordinates
	 * 
	 * @param scale scaling factor
	 */
	public abstract void scaleY(double scale);
	
	
	/**
	 * Set Z scale for translations and coordinates
	 * 
	 * @param scale scaling factor
	 */
	public abstract void scaleZ(double scale);
	
	
	/**
	 * Rotate coordinate system around X axis
	 * 
	 * @param angle rotation (in degrees)
	 */
	public abstract void rotateX(double angle);
	
	
	/**
	 * Rotate coordinate system around Y axis
	 * 
	 * @param angle rotation (in degrees)
	 */
	public abstract void rotateY(double angle);
	
	
	/**
	 * Rotate coordinate system around Z axis
	 * 
	 * @param angle rotation (in degrees)
	 */
	public abstract void rotateZ(double angle);
	
	
	/**
	 * Rotate coordinate system around given axis
	 * 
	 * @param angle rotation angle
	 * @param axis rotation axis (unit vector)
	 */
	public abstract void rotate(double angle, Vect axis);
	
	
	/**
	 * Store render state on stack<br>
	 * This includes pushGeometry and pushColor.
	 */
	public abstract void pushState();
	
	
	/**
	 * Restore state from stack (must be pushed first)<br>
	 * This includes popColor and popGeometry.
	 */
	public abstract void popState();
	
	
	/**
	 * Store current rotation and translation on stack
	 */
	public abstract void pushGeometry();
	
	
	/**
	 * Restore rotation and translation from stack
	 */
	public abstract void popGeometry();
	
	
	/**
	 * Store color on stack (so it can later be restored)
	 */
	public abstract void pushColor();
	
	
	/**
	 * Restore color from stack (must be pushed first)
	 */
	public abstract void popColor();
	
	
	/**
	 * Render 2D quad with currently set color
	 * 
	 * @param rect drawn rect
	 */
	public abstract void quad(Rect rect);
	
	
	/**
	 * Render 2D quad with given color.<br>
	 * This may change current drawing color.
	 * 
	 * @param rect rectangle
	 * @param color draw color
	 */
	public abstract void quad(Rect rect, Color color);
	
	
	/**
	 * Render 2D quad with gradient.<br>
	 * This may change current drawing color.
	 * 
	 * @param rect rectangle
	 * @param grad gradient
	 */
	public abstract void quad(Rect rect, Grad grad);
	
	
	/**
	 * Render textured quad with current color
	 * 
	 * @param rect rectangle to draw
	 * @param txquad texture quad
	 */
	public abstract void quad(Rect rect, TxQuad txquad);
	
	
	/**
	 * Render textured quad with given color
	 * 
	 * @param rect rectangle to draw
	 * @param txquad texture instance
	 * @param color color tint
	 */
	public abstract void quad(Rect rect, TxQuad txquad, Color color);
	
	
	/**
	 * Setup projection for 2D graphics, using current scren size
	 */
	public abstract void setupProjection();
	
	
	/**
	 * Get backend-flavoured lazy texture
	 * 
	 * @param path path to texture
	 * @return the lazy texture
	 */
	public abstract DeferredTexture getLazyTexture(String path);
	
	
	/**
	 * Set target fps (for syncing in endFrame() call).<br>
	 * With vsync enabled, the target fps may not be met.
	 * 
	 * @param fps requested fps
	 */
	public abstract void setTargetFps(int fps);
	
	
	/**
	 * Set fullscreen. The fullscreen state will be changed when possible (eg.
	 * at the end of the current frame) and a {@link ViewportChangeEvent} will
	 * be fired.
	 * 
	 * @param fs true for fullscreen
	 */
	public abstract void setFullscreen(boolean fs);
	
	
	/**
	 * Request fullscreen toggle. See setFullscreen() for more info)
	 */
	public abstract void switchFullscreen();
	
	
	/**
	 * Get fullscreen state (note that methods changing fullscreen may not have
	 * immediate effect, so this method may report the old state if the
	 * fullscreen state has not yet been changed).
	 * 
	 * @return is fullscreen
	 */
	public abstract boolean isFullscreen();
	
	
	/**
	 * Take screenshot (expensive processing should be done in separate thread
	 * when screenshot is saved).<br>
	 * This method is utilized by the Screenshot plugin.
	 * 
	 * @return screenshot object
	 */
	public abstract Screenshot takeScreenshot();
	
	
	/**
	 * Start a render frame - clear buffers, prepare rendering context etc.
	 */
	public abstract void beginFrame();
	
	
	/**
	 * End a render frame: flip buffers, sync to fps...
	 */
	public abstract void endFrame();
	
	
	/**
	 * Set display dimensions
	 * 
	 * @param width display width (pixels)
	 * @param height display height (pixels)
	 */
	public abstract void setSize(int width, int height);
	
	
	/**
	 * Set window titlebar text
	 * 
	 * @param title titlebar text
	 */
	public abstract void setTitle(String title);
	
	
	/**
	 * Enable or disable VSync
	 * 
	 * @param vsync true for vsync enabled
	 */
	public abstract void setVSync(boolean vsync);
	
	
	/**
	 * Set window resizable / fixed
	 * 
	 * @param resizable true for resizable
	 */
	public abstract void setResizable(boolean resizable);
	
	
	/**
	 * Get screen rect. Should always return the same Rect instance.
	 * 
	 * @return the rect
	 */
	public abstract Rect getRect();
	
	
	/**
	 * Get current FPS (eg. measured by a {@link FpsMeter})
	 * 
	 * @return current FPS
	 */
	public abstract long getFps();
	
	
	/**
	 * Get screen center. Should always return the same Vect instance.
	 * 
	 * @return screen center.
	 */
	public abstract Vect getCenter();
	
	
	/**
	 * Get screen size. Should always return the same Vect instance.
	 * 
	 * @return size
	 */
	public abstract Vect getSize();
	
	
	/**
	 * @return screen width
	 */
	public abstract int getWidth();
	
	
	/**
	 * @return screen height
	 */
	public abstract int getHeight();
}
