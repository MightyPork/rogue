package mightypork.gamecore.render;


import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import mightypork.gamecore.render.textures.FilterMode;
import mightypork.gamecore.render.textures.GLTexture;
import mightypork.gamecore.render.textures.TxQuad;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.caching.RectDigest;
import mightypork.util.constraints.vect.Vect;
import mightypork.util.constraints.vect.VectConst;
import mightypork.util.files.FileUtils;
import mightypork.util.logging.Log;
import mightypork.util.math.color.Color;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


/**
 * Render utilities
 * 
 * @author MightyPork
 */
public class Render {
	
	public static final VectConst AXIS_X = Vect.make(1, 0, 0);
	public static final VectConst AXIS_Y = Vect.make(0, 1, 0);
	public static final VectConst AXIS_Z = Vect.make(0, 0, 1);
	
	
	/**
	 * Bind GL color
	 * 
	 * @param color Color color
	 */
	public static void setColor(Color color)
	{
		if (color != null) glColor4d(color.red(), color.green(), color.blue(), color.alpha());
	}
	
	
	/**
	 * Bind GL color
	 * 
	 * @param color Color color
	 * @param alpha alpha multiplier
	 */
	public static void setColor(Color color, double alpha)
	{
		if (color != null) glColor4d(color.red(), color.green(), color.blue(), color.alpha() * alpha);
	}
	
	
	/**
	 * Translate
	 * 
	 * @param x
	 * @param y
	 */
	public static void translate(double x, double y)
	{
		glTranslated(x, y, 0);
	}
	
	
	/**
	 * Translate
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void translate(double x, double y, double z)
	{
		glTranslated(x, y, z);
	}
	
	
	/**
	 * Translate with coord
	 * 
	 * @param coord coord
	 */
	public static void translate(Vect coord)
	{
		glTranslated(coord.x(), coord.y(), coord.z());
	}
	
	
	/**
	 * Translate with coord, discard Z
	 * 
	 * @param coord coord
	 */
	public static void translateXY(Vect coord)
	{
		glTranslated(coord.x(), coord.y(), 0);
	}
	
	
	/**
	 * Scale
	 * 
	 * @param x
	 * @param y
	 */
	public static void scale(double x, double y)
	{
		glScaled(x, y, 0);
	}
	
	
	/**
	 * Scale
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void scale(double x, double y, double z)
	{
		glScaled(x, y, z);
	}
	
	
	/**
	 * Scale
	 * 
	 * @param factor vector of scaling factors
	 */
	public static void scale(Vect factor)
	{
		glScaled(factor.x(), factor.y(), factor.z());
	}
	
	
	/**
	 * Scale by X factor
	 * 
	 * @param factor scaling factor
	 */
	public static void scaleXY(double factor)
	{
		glScaled(factor, factor, 1);
	}
	
	
	/**
	 * Scale by X factor
	 * 
	 * @param factor scaling factor
	 */
	public static void scaleX(double factor)
	{
		glScaled(factor, 1, 1);
	}
	
	
	/**
	 * Scale by Y factor
	 * 
	 * @param factor scaling factor
	 */
	public static void scaleY(double factor)
	{
		glScaled(1, factor, 1);
	}
	
	
	/**
	 * Scale by Z factor
	 * 
	 * @param factor scaling factor
	 */
	public static void scaleZ(double factor)
	{
		glScaled(1, 1, factor);
	}
	
	
	/**
	 * Rotate around X axis
	 * 
	 * @param angle deg
	 */
	public static void rotateX(double angle)
	{
		rotate(angle, AXIS_X);
	}
	
	
	/**
	 * Rotate around Y axis
	 * 
	 * @param angle deg
	 */
	public static void rotateY(double angle)
	{
		rotate(angle, AXIS_Y);
	}
	
	
	/**
	 * Rotate around Z axis
	 * 
	 * @param angle deg
	 */
	public static void rotateZ(double angle)
	{
		rotate(angle, AXIS_Z);
	}
	
	
	/**
	 * Rotate
	 * 
	 * @param angle rotate angle
	 * @param axis rotation axis
	 */
	public static void rotate(double angle, Vect axis)
	{
		final Vect vec = axis.norm(1);
		glRotated(angle, vec.x(), vec.y(), vec.z());
	}
	
	private static int pushed = 0;
	
	
	/**
	 * Store GL state
	 */
	public static void pushState()
	{
		pushed++;
		
		if (pushed >= 20) {
			Log.w("Suspicious number of state pushes: " + pushed);
		}
		
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glPushClientAttrib(GL11.GL_ALL_CLIENT_ATTRIB_BITS);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	
	/**
	 * Restore Gl state
	 */
	public static void popState()
	{
		if (pushed == 0) {
			Log.w("Pop without push.");
		}
		
		pushed--;
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		GL11.glPopClientAttrib();
		GL11.glPopAttrib();
	}
	
	
	/**
	 * Store matrix
	 */
	public static void pushMatrix()
	{
		GL11.glPushMatrix();
	}
	
	
	/**
	 * Restore Gl state
	 */
	public static void popMatrix()
	{
		GL11.glPopMatrix();
	}
	
	
	/**
	 * Load texture
	 * 
	 * @param resourcePath
	 * @param filtering filtering mode to use while loading.
	 * @return the loaded texture
	 */
	public synchronized static Texture loadTexture(String resourcePath, FilterMode filtering)
	{
		
		try {
			
			final String ext = FileUtils.getExtension(resourcePath).toUpperCase();
			
			final Texture texture = TextureLoader.getTexture(ext, ResourceLoader.getResourceAsStream(resourcePath), false, filtering.num);
			
			if (texture == null) {
				Log.w("Texture " + resourcePath + " could not be loaded.");
			}
			
			return texture;
			
		} catch (final IOException e) {
			Log.e("Loading of texture " + resourcePath + " failed.", e);
			throw new RuntimeException("Could not load texture " + resourcePath + ".", e);
		}
		
	}
	
	
	/**
	 * Bind texture
	 * 
	 * @param texture the texture
	 * @throws RuntimeException if not loaded yet
	 */
	private static void bindTexture(GLTexture texture) throws RuntimeException
	{
		texture.bind();
	}
	
	
	/**
	 * Unbind all
	 */
	private static void unbindTexture()
	{
		if (TextureImpl.getLastBind() != null) {
			TextureImpl.bindNone();
		}
	}
	
	
	/**
	 * Render quad 2D
	 * 
	 * @param rect rectangle
	 * @param color draw color
	 */
	public static void quad(Rect rect, Color color)
	{
		setColor(color);
		quad(rect);
	}
	
	
	/**
	 * Render quad
	 * 
	 * @param quad the quad to draw (px)
	 */
	public static void quad(Rect quad)
	{
		final RectDigest q = quad.digest();
		
		// draw with color

		glDisable(GL_TEXTURE_2D);
		
		// quad
		glBegin(GL_QUADS);
		glVertex2d(q.left, q.bottom);
		glVertex2d(q.right, q.bottom);
		glVertex2d(q.right, q.top);
		glVertex2d(q.left, q.top);
		glEnd();
	}
	
	
	/**
	 * Render textured rect (texture must be binded already)
	 * 
	 * @param quad rectangle (px)
	 * @param uvs texture coords (0-1)
	 */
	public static void quadUV(Rect quad, Rect uvs)
	{
		glBegin(GL_QUADS);
		quadUV_nobound(quad, uvs);
		glEnd();
	}
	
	
	/**
	 * Draw quad without glBegin and glEnd.
	 * 
	 * @param quad rectangle (px)
	 * @param uvs texture coords (0-1)
	 */
	public static void quadUV_nobound(Rect quad, Rect uvs)
	{
		final RectDigest q = quad.digest();
		
		final RectDigest u = uvs.digest();
		
		// quad with texture
		glTexCoord2d(u.left, u.bottom);
		glVertex2d(q.left, q.bottom);
		
		glTexCoord2d(u.right, u.bottom);
		glVertex2d(q.right, q.bottom);
		
		glTexCoord2d(u.right, u.top);
		glVertex2d(q.right, q.top);
		
		glTexCoord2d(u.left, u.top);
		glVertex2d(q.left, q.top);
	}
	
	
	/**
	 * Draw quad with horizontal gradient
	 * 
	 * @param quad drawn quad bounds
	 * @param color1 left color
	 * @param color2 right color
	 */
	public static void quadGradH(Rect quad, Color color1, Color color2)
	{
		quadColor(quad, color1, color2, color2, color1);
	}
	
	
	public static void quadColor(Rect quad, Color color)
	{
		quadColor(quad, color, color, color, color);
	}
	
	
	/**
	 * Draw quad with coloured vertices.
	 * 
	 * @param quad drawn quad bounds
	 * @param colorHMinVMin
	 * @param colorHMaxVMin
	 * @param colorHMaxVMax
	 * @param colorHMinVMax
	 */
	public static void quadColor(Rect quad, Color colorHMinVMin, Color colorHMaxVMin, Color colorHMaxVMax, Color colorHMinVMax)
	{
		final RectDigest r = quad.digest();
		
		// draw with color

		glDisable(GL_TEXTURE_2D);
		
		glBegin(GL_QUADS);
		setColor(colorHMinVMax);
		glVertex2d(r.left, r.bottom);
		
		setColor(colorHMaxVMax);
		glVertex2d(r.right, r.bottom);
		
		setColor(colorHMaxVMin);
		glVertex2d(r.right, r.top);
		
		setColor(colorHMinVMin);
		glVertex2d(r.left, r.top);
		glEnd();
	}
	
	
	/**
	 * Draw quad with vertical gradient
	 * 
	 * @param quad drawn quad bounds
	 * @param color1 top color
	 * @param color2 bottom color
	 */
	public static void quadGradV(Rect quad, Color color1, Color color2)
	{
		quadColor(quad, color1, color1, color2, color2);
	}
	
	
	/**
	 * Render textured rect
	 * 
	 * @param quad rectangle (px)
	 * @param uvs texture coords rectangle (0-1)
	 * @param texture texture instance
	 * @param tint color tint
	 */
	public static void quadTextured(Rect quad, Rect uvs, GLTexture texture, Color tint)
	{
		glEnable(GL_TEXTURE_2D);
		bindTexture(texture);
		setColor(tint);
		quadUV(quad, uvs);
		unbindTexture();
	}
	
	
	/**
	 * Render textured rect
	 * 
	 * @param quad rectangle (px)
	 * @param uvs texture coords rectangle (px)
	 * @param texture texture instance
	 */
	public static void quadTextured(Rect quad, Rect uvs, GLTexture texture)
	{
		quadTextured(quad, uvs, texture, Color.WHITE);
	}
	
	
	/**
	 * Render textured rect
	 * 
	 * @param quad rectangle (px)
	 * @param texture texture instance
	 */
	public static void quadTextured(Rect quad, GLTexture texture)
	{
		quadTextured(quad, Rect.ONE, texture, Color.WHITE);
	}
	
	
	/**
	 * Render textured rect
	 * 
	 * @param quad rectangle (px)
	 * @param txquad texture quad
	 */
	public static void quadTextured(Rect quad, TxQuad txquad)
	{
		quadTextured(quad, txquad, Color.WHITE);
	}
	
	
	/**
	 * Render textured rect
	 * 
	 * @param quad rectangle (px)
	 * @param txquad texture instance
	 * @param tint color tint
	 */
	public static void quadTextured(Rect quad, TxQuad txquad, Color tint)
	{
		quadTextured(quad, txquad.uvs, txquad.tx, tint);
	}
	
	
	/**
	 * Setup Ortho projection for 2D graphics
	 * 
	 * @param size viewport size (screen size)
	 */
	public static void setupOrtho(Vect size)
	{
		// fix projection for changed size
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glViewport(0, 0, size.xi(), size.yi());
		glOrtho(0, size.xi(), size.yi(), 0, -1000, 1000);
		
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
	
}
