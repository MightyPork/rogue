package mightypork.gamecore.render;


import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import mightypork.gamecore.render.textures.TxQuad;
import mightypork.utils.files.FileUtils;
import mightypork.utils.logging.Log;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.rect.Rect;
import mightypork.utils.math.rect.RectView;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectVal;
import mightypork.utils.math.vect.VectView;

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
	
	public static final VectView AXIS_X = VectVal.make(1, 0, 0);
	public static final VectView AXIS_Y = VectVal.make(0, 1, 0);
	public static final VectView AXIS_Z = VectVal.make(0, 0, 1);
	
	
	/**
	 * Bind GL color
	 * 
	 * @param color RGB color
	 */
	public static void setColor(RGB color)
	{
		if (color != null) glColor4d(color.r, color.g, color.b, color.a);
	}
	
	
	/**
	 * Bind GL color
	 * 
	 * @param color RGB color
	 * @param alpha alpha multiplier
	 */
	public static void setColor(RGB color, double alpha)
	{
		if (color != null) glColor4d(color.r, color.g, color.b, color.a * alpha);
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
		final Vect vec = axis.view().norm(1);
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
	 * @return the loaded texture
	 */
	public synchronized static Texture loadTexture(String resourcePath)
	{
		
		try {
			
			final String ext = FileUtils.getExtension(resourcePath).toUpperCase();
			
			final Texture texture = TextureLoader.getTexture(ext, ResourceLoader.getResourceAsStream(resourcePath));
			
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
	private static void bindTexture(Texture texture) throws RuntimeException
	{
		texture.bind();
	}
	
	
	/**
	 * Unbind all
	 */
	private static void unbindTexture()
	{
		//if (TextureImpl.getLastBind() != null) {
		TextureImpl.bindNone();
		//}
	}
	
	
	/**
	 * Render quad 2D
	 * 
	 * @param rect rectangle
	 * @param color draw color
	 */
	public static void quad(Rect rect, RGB color)
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
		RectView rv = quad.view();
		
		final double x1 = rv.left().value();
		final double y1 = rv.top().value();
		final double x2 = rv.right().value();
		final double y2 = rv.bottom().value();
		
		// draw with color
		unbindTexture();
		
		// quad
		glBegin(GL_QUADS);
		glVertex2d(x1, y2);
		glVertex2d(x2, y2);
		glVertex2d(x2, y1);
		glVertex2d(x1, y1);
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
		final double x1 = quad.left();
		final double y1 = quad.top();
		final double x2 = quad.right();
		final double y2 = quad.bottom();
		
		final double tx1 = uvs.left();
		final double ty1 = uvs.top();
		final double tx2 = uvs.right();
		final double ty2 = uvs.bottom();
		
		// quad with texture
		glTexCoord2d(tx1, ty2);
		glVertex2d(x1, y2);
		glTexCoord2d(tx2, ty2);
		glVertex2d(x2, y2);
		glTexCoord2d(tx2, ty1);
		glVertex2d(x2, y1);
		glTexCoord2d(tx1, ty1);
		glVertex2d(x1, y1);
	}
	
	
	/**
	 * Draw quad with horizontal gradient
	 * 
	 * @param quad drawn quad bounds
	 * @param color1 left color
	 * @param color2 right color
	 */
	public static void quadGradH(Rect quad, RGB color1, RGB color2)
	{
		quadColor(quad, color1, color2, color2, color1);
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
	public static void quadColor(Rect quad, RGB colorHMinVMin, RGB colorHMaxVMin, RGB colorHMaxVMax, RGB colorHMinVMax)
	{
		final double x1 = quad.left();
		final double y1 = quad.top();
		final double x2 = quad.right();
		final double y2 = quad.bottom();
		
		// draw with color
		unbindTexture();
		
		glBegin(GL_QUADS);
		setColor(colorHMinVMax);
		glVertex2d(x1, y2);
		setColor(colorHMaxVMax);
		glVertex2d(x2, y2);
		
		setColor(colorHMaxVMin);
		glVertex2d(x2, y1);
		setColor(colorHMinVMin);
		glVertex2d(x1, y1);
		glEnd();
	}
	
	
	/**
	 * Draw quad with vertical gradient
	 * 
	 * @param quad drawn quad bounds
	 * @param color1 top color
	 * @param color2 bottom color
	 */
	public static void quadGradV(Rect quad, RGB color1, RGB color2)
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
	public static void quadTextured(Rect quad, Rect uvs, Texture texture, RGB tint)
	{
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
	public static void quadTextured(Rect quad, Rect uvs, Texture texture)
	{
		quadTextured(quad, uvs, texture, RGB.WHITE);
	}
	
	
	/**
	 * Render textured rect
	 * 
	 * @param quad rectangle (px)
	 * @param texture texture instance
	 */
	public static void quadTextured(Rect quad, Texture texture)
	{
		quadTextured(quad, Rect.ONE, texture, RGB.WHITE);
	}
	
	
	/**
	 * Render textured rect
	 * 
	 * @param quad rectangle (px)
	 * @param txquad texture quad
	 */
	public static void quadTextured(Rect quad, TxQuad txquad)
	{
		quadTextured(quad, txquad, RGB.WHITE);
	}
	
	
	/**
	 * Render textured rect
	 * 
	 * @param quad rectangle (px)
	 * @param txquad texture instance
	 * @param tint color tint
	 */
	public static void quadTextured(Rect quad, TxQuad txquad, RGB tint)
	{
		quadTextured(quad, txquad.uvs, txquad.tx, tint);
	}
	
	
	/**
	 * Setup Ortho projection for 2D graphics
	 * 
	 * @param size viewport size (screen size)
	 */
	public static void setupOrtho(VectView size)
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
