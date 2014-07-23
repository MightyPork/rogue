//package junk;
//
//
//import static org.lwjgl.opengl.GL11.*;
//
//import java.io.IOException;
//
//import mightypork.gamecore.resources.textures.FilterMode;
//import mightypork.gamecore.resources.textures.ITexture;
//import mightypork.gamecore.resources.textures.TxQuad;
//import mightypork.utils.files.FileUtils;
//import mightypork.utils.logging.Log;
//import mightypork.utils.math.color.Color;
//import mightypork.utils.math.color.pal.RGB;
//import mightypork.utils.math.constraints.rect.Rect;
//import mightypork.utils.math.constraints.rect.caching.RectDigest;
//import mightypork.utils.math.constraints.vect.Vect;
//import mightypork.utils.math.constraints.vect.VectConst;
//
//import org.lwjgl.opengl.GL11;
//import org.newdawn.slick.opengl.Texture;
//import org.newdawn.slick.opengl.TextureLoader;
//
//
///**
// * Render utilities
// * 
// * @author Ondřej Hruška (MightyPork)
// */
//@Deprecated
//public class Render {
//	
//	public static final VectConst AXIS_X = Vect.make(1, 0, 0);
//	public static final VectConst AXIS_Y = Vect.make(0, 1, 0);
//	public static final VectConst AXIS_Z = Vect.make(0, 0, 1);
//	
//	
//	/**
//	 * Bind GL color
//	 * 
//	 * @param color Color color
//	 */
//	public static void setColor(Color color)
//	{
//		if (color != null) glColor4d(color.r(), color.g(), color.b(), color.a());
//	}
//	
//	
//	/**
//	 * Bind GL color
//	 * 
//	 * @param color Color color
//	 * @param alpha alpha multiplier
//	 */
//	public static void setColor(Color color, double alpha)
//	{
//		if (color != null) glColor4d(color.r(), color.g(), color.b(), color.a() * alpha);
//	}
//	
//	
//	/**
//	 * Translate
//	 * 
//	 * @param x
//	 * @param y
//	 */
//	public static void translate(double x, double y)
//	{
//		glTranslated(x, y, 0);
//	}
//	
//	
//	/**
//	 * Translate
//	 * 
//	 * @param x
//	 * @param y
//	 * @param z
//	 */
//	public static void translate(double x, double y, double z)
//	{
//		glTranslated(x, y, z);
//	}
//	
//	
//	/**
//	 * Translate with coord
//	 * 
//	 * @param coord coord
//	 */
//	public static void translate(Vect coord)
//	{
//		glTranslated(coord.x(), coord.y(), coord.z());
//	}
//	
//	
//	/**
//	 * Translate with coord, discard Z
//	 * 
//	 * @param coord coord
//	 */
//	public static void translateXY(Vect coord)
//	{
//		glTranslated(coord.x(), coord.y(), 0);
//	}
//	
//	
//	/**
//	 * Scale
//	 * 
//	 * @param x
//	 * @param y
//	 */
//	public static void scale(double x, double y)
//	{
//		glScaled(x, y, 0);
//	}
//	
//	
//	/**
//	 * Scale
//	 * 
//	 * @param x
//	 * @param y
//	 * @param z
//	 */
//	public static void scale(double x, double y, double z)
//	{
//		glScaled(x, y, z);
//	}
//	
//	
//	/**
//	 * Scale
//	 * 
//	 * @param factor vector of scaling factors
//	 */
//	public static void scale(Vect factor)
//	{
//		glScaled(factor.x(), factor.y(), factor.z());
//	}
//	
//	
//	/**
//	 * Scale by X factor
//	 * 
//	 * @param factor scaling factor
//	 */
//	public static void scaleXY(double factor)
//	{
//		glScaled(factor, factor, 1);
//	}
//	
//	
//	/**
//	 * Scale by X factor
//	 * 
//	 * @param factor scaling factor
//	 */
//	public static void scaleX(double factor)
//	{
//		glScaled(factor, 1, 1);
//	}
//	
//	
//	/**
//	 * Scale by Y factor
//	 * 
//	 * @param factor scaling factor
//	 */
//	public static void scaleY(double factor)
//	{
//		glScaled(1, factor, 1);
//	}
//	
//	
//	/**
//	 * Scale by Z factor
//	 * 
//	 * @param factor scaling factor
//	 */
//	public static void scaleZ(double factor)
//	{
//		glScaled(1, 1, factor);
//	}
//	
//	
//	/**
//	 * Rotate around X axis
//	 * 
//	 * @param angle deg
//	 */
//	public static void rotateX(double angle)
//	{
//		rotate(angle, AXIS_X);
//	}
//	
//	
//	/**
//	 * Rotate around Y axis
//	 * 
//	 * @param angle deg
//	 */
//	public static void rotateY(double angle)
//	{
//		rotate(angle, AXIS_Y);
//	}
//	
//	
//	/**
//	 * Rotate around Z axis
//	 * 
//	 * @param angle deg
//	 */
//	public static void rotateZ(double angle)
//	{
//		rotate(angle, AXIS_Z);
//	}
//	
//	
//	/**
//	 * Rotate
//	 * 
//	 * @param angle rotate angle
//	 * @param axis rotation axis
//	 */
//	public static void rotate(double angle, Vect axis)
//	{
//		final Vect vec = axis.norm(1);
//		glRotated(angle, vec.x(), vec.y(), vec.z());
//	}
//	
//	private static int pushed = 0;
//	/** Can be used to avoid texture binding and glBegin/glEnd in textured quads */
//	public static boolean batchTexturedQuadMode;
//	
//	
//	/**
//	 * Store GL state
//	 */
//	public static void pushState()
//	{
//		pushed++;
//		
//		if (pushed >= 100) {
//			Log.w("Suspicious number of state pushes: " + pushed);
//		}
//		
//		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
//		GL11.glPushClientAttrib(GL11.GL_ALL_CLIENT_ATTRIB_BITS);
//		GL11.glMatrixMode(GL11.GL_MODELVIEW);
//		GL11.glPushMatrix();
//		GL11.glMatrixMode(GL11.GL_PROJECTION);
//		GL11.glPushMatrix();
//		GL11.glMatrixMode(GL11.GL_MODELVIEW);
//	}
//	
//	
//	/**
//	 * Restore Gl state
//	 */
//	public static void popState()
//	{
//		if (pushed == 0) {
//			Log.w("Pop without push.");
//		}
//		
//		pushed--;
//		
//		GL11.glMatrixMode(GL11.GL_PROJECTION);
//		GL11.glPopMatrix();
//		GL11.glMatrixMode(GL11.GL_MODELVIEW);
//		GL11.glPopMatrix();
//		GL11.glPopClientAttrib();
//		GL11.glPopAttrib();
//	}
//	
//	
//	/**
//	 * Store matrix
//	 */
//	public static void pushMatrix()
//	{
//		GL11.glPushMatrix();
//	}
//	
//	
//	/**
//	 * Restore Gl state
//	 */
//	public static void popMatrix()
//	{
//		GL11.glPopMatrix();
//	}
//	
//	
//	/**
//	 * Load texture
//	 * 
//	 * @param resourcePath
//	 * @param filtering filtering mode to use while loading.
//	 * @return the loaded texture
//	 */
//	public synchronized static Texture loadSlickTexture(String resourcePath, FilterMode filtering)
//	{
//		
//		try {
//			
//			final String ext = FileUtils.getExtension(resourcePath).toUpperCase();
//			
//			final Texture texture = TextureLoader.getTexture(ext, FileUtils.getResource(resourcePath), false, filtering.num);
//			
//			if (texture == null) {
//				Log.w("Texture " + resourcePath + " could not be loaded.");
//			}
//			
//			return texture;
//			
//		} catch (final IOException e) {
//			Log.e("Loading of texture " + resourcePath + " failed.", e);
//			throw new RuntimeException("Could not load texture " + resourcePath + ".", e);
//		}
//		
//	}
//	
//	
//	/**
//	 * Render quad 2D
//	 * 
//	 * @param rect rectangle
//	 * @param color draw color
//	 */
//	public static void quad(Rect rect, Color color)
//	{
//		setColor(color);
//		quad(rect);
//	}
//	
//	
//	/**
//	 * Render quad
//	 * 
//	 * @param quad the quad to draw (px)
//	 */
//	public static void quad(Rect quad)
//	{
//		final RectDigest q = quad.digest();
//		
//		// draw with color
//		
//		glDisable(GL_TEXTURE_2D);
//		
//		// quad
//		glBegin(GL_QUADS);
//		glVertex2d(q.left, q.bottom);
//		glVertex2d(q.right, q.bottom);
//		glVertex2d(q.right, q.top);
//		glVertex2d(q.left, q.top);
//		glEnd();
//	}
//	
//	
//	/**
//	 * Draw quad with horizontal gradient
//	 * 
//	 * @param quad drawn quad bounds
//	 * @param color1 left color
//	 * @param color2 right color
//	 */
//	public static void quadGradH(Rect quad, Color color1, Color color2)
//	{
//		quadColor(quad, color1, color2, color2, color1);
//	}
//	
//	
//	public static void quadColor(Rect quad, Color color)
//	{
//		quadColor(quad, color, color, color, color);
//	}
//	
//	
//	/**
//	 * Draw quad with coloured vertices.
//	 * 
//	 * @param quad drawn quad bounds
//	 * @param colorHMinVMin
//	 * @param colorHMaxVMin
//	 * @param colorHMaxVMax
//	 * @param colorHMinVMax
//	 */
//	public static void quadColor(Rect quad, Color colorHMinVMin, Color colorHMaxVMin, Color colorHMaxVMax, Color colorHMinVMax)
//	{
//		final RectDigest r = quad.digest();
//		
//		// draw with color
//		
//		glDisable(GL_TEXTURE_2D);
//		
//		glBegin(GL_QUADS);
//		setColor(colorHMinVMax);
//		glVertex2d(r.left, r.bottom);
//		
//		setColor(colorHMaxVMax);
//		glVertex2d(r.right, r.bottom);
//		
//		setColor(colorHMaxVMin);
//		glVertex2d(r.right, r.top);
//		
//		setColor(colorHMinVMin);
//		glVertex2d(r.left, r.top);
//		glEnd();
//	}
//	
//	
//	/**
//	 * Draw quad with vertical gradient
//	 * 
//	 * @param quad drawn quad bounds
//	 * @param color1 top color
//	 * @param color2 bottom color
//	 */
//	public static void quadGradV(Rect quad, Color color1, Color color2)
//	{
//		quadColor(quad, color1, color1, color2, color2);
//	}
//	
//	
//	/**
//	 * Render textured rect
//	 * 
//	 * @param quad rectangle (px)
//	 * @param txquad texture quad
//	 */
//	public static void quadTextured(Rect quad, TxQuad txquad)
//	{
//		quadTextured(quad, txquad, RGB.WHITE);
//	}
//	
//	
//	/**
//	 * Render textured rect
//	 * 
//	 * @param quad rectangle (px)
//	 * @param txquad texture instance
//	 * @param tint color tint
//	 */
//	public static void quadTextured(Rect quad, TxQuad txquad, Color tint)
//	{
//		if (!batchTexturedQuadMode) {
//			glEnable(GL_TEXTURE_2D);
//			txquad.tx.bind();
//			glBegin(GL_QUADS);
//			setColor(tint);
//		}
//		
//		final RectDigest q = quad.digest();
//		final RectDigest u = txquad.uvs.digest();
//		
//		final double offs = 0.0001;// hack to avoid white stitching
//		
//		double tL = u.left + offs, tR = u.right - offs, tT = u.top + offs, tB = u.bottom - offs;
//		
//		// handle flip
//		if (txquad.isFlippedY()) {
//			final double swap = tT;
//			tT = tB;
//			tB = swap;
//		}
//		
//		if (txquad.isFlippedX()) {
//			final double swap = tL;
//			tL = tR;
//			tR = swap;
//		}
//		
//		final double w = txquad.tx.getWidth01();
//		final double h = txquad.tx.getHeight01();
//		
//		// quad with texture
//		glTexCoord2d(tL * w, tB * h);
//		glVertex2d(q.left, q.bottom);
//		
//		glTexCoord2d(tR * w, tB * h);
//		glVertex2d(q.right, q.bottom);
//		
//		glTexCoord2d(tR * w, tT * h);
//		glVertex2d(q.right, q.top);
//		
//		glTexCoord2d(tL * w, tT * h);
//		glVertex2d(q.left, q.top);
//		
//		if (!batchTexturedQuadMode) glEnd();
//	}
//	
//	
//	/**
//	 * Setup Ortho projection for 2D graphics
//	 * 
//	 * @param size viewport size (screen size)
//	 */
//	public static void setupOrtho(Vect size)
//	{
//		// fix projection for changed size
//		glMatrixMode(GL_PROJECTION);
//		glLoadIdentity();
//		glViewport(0, 0, size.xi(), size.yi());
//		glOrtho(0, size.xi(), size.yi(), 0, -1000, 1000);
//		
//		// back to modelview
//		glMatrixMode(GL_MODELVIEW);
//		
//		glLoadIdentity();
//		
//		glDisable(GL_LIGHTING);
//		
//		glClearDepth(1f);
//		glEnable(GL_DEPTH_TEST);
//		glDepthFunc(GL_LEQUAL);
//		
//		glEnable(GL_NORMALIZE);
//		
//		glShadeModel(GL_SMOOTH);
//		
//		glEnable(GL_BLEND);
//		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//	}
//	
//	
//	public static void enterBatchTexturedQuadMode(ITexture texture)
//	{
//		texture.bind();
//		glBegin(GL11.GL_QUADS);
//		batchTexturedQuadMode = true;
//	}
//	
//	
//	public static void leaveBatchTexturedQuadMode()
//	{
//		glEnd();
//		batchTexturedQuadMode = false;
//	}
//}
