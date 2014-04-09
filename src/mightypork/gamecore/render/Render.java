package mightypork.gamecore.render;


import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import mightypork.gamecore.render.textures.TxQuad;
import mightypork.utils.files.FileUtils;
import mightypork.utils.logging.Log;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.coord.Rect;

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
	
	private static final Coord AXIS_X = new Coord(1, 0, 0);
	private static final Coord AXIS_Y = new Coord(0, 1, 0);
	private static final Coord AXIS_Z = new Coord(0, 0, 1);
	
	
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
	 * Translate with coord
	 * 
	 * @param coord coord
	 */
	public static void translate(Coord coord)
	{
		glTranslated(coord.x, coord.y, coord.z);
	}
	
	
	/**
	 * Scale
	 * 
	 * @param factor vector of scaling factors
	 */
	public static void scale(Coord factor)
	{
		glScaled(factor.x, factor.y, factor.z);
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
	public static void rotate(double angle, Coord axis)
	{
		final Coord vec = axis.norm(1);
		glRotated(angle, vec.x, vec.y, vec.z);
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
		
//		Log.f3("push : "+pushed);
		
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glPushClientAttrib(GL11.GL_ALL_CLIENT_ATTRIB_BITS);
		GL11.glPushMatrix();
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
		
//		Log.f3("pop  : "+pushed);
		
		GL11.glPopMatrix();
		GL11.glPopClientAttrib();
		GL11.glPopAttrib();
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
	 * @param linear use linear interpolation for scaling
	 * @throws RuntimeException if not loaded yet
	 */
	private static void bindTexture(Texture texture, boolean linear) throws RuntimeException
	{
		texture.bind();
	}
	
	
	/**
	 * Bind texture with linear interpolation
	 * 
	 * @param texture the texture
	 * @throws RuntimeException if not loaded yet
	 */
	private static void bindTexture(Texture texture) throws RuntimeException
	{
		bindTexture(texture, false);
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
		final double x1 = quad.xMin();
		final double y1 = quad.yMin();
		final double x2 = quad.xMax();
		final double y2 = quad.yMax();
		
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
		final double x1 = quad.xMin();
		final double y1 = quad.yMin();
		final double x2 = quad.xMax();
		final double y2 = quad.yMax();
		
		final double tx1 = uvs.xMin();
		final double ty1 = uvs.yMin();
		final double tx2 = uvs.xMax();
		final double ty2 = uvs.yMax();
		
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
	 * @param colorLeft left color
	 * @param colorRight right color
	 */
	public static void quadGradH(Rect quad, RGB colorLeft, RGB colorRight)
	{
		final double x1 = quad.xMin();
		final double y1 = quad.yMin();
		final double x2 = quad.xMax();
		final double y2 = quad.yMax();
		
		// draw with color
		unbindTexture();
		
		glBegin(GL_QUADS);
		setColor(colorLeft);
		glVertex2d(x1, y2);
		setColor(colorRight);
		glVertex2d(x2, y2);
		
		setColor(colorRight);
		glVertex2d(x2, y1);
		setColor(colorLeft);
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
		final double x1 = quad.xMin();
		final double y1 = quad.yMin();
		final double x2 = quad.xMax();
		final double y2 = quad.yMax();
		
		// draw with color
		unbindTexture();
		
		glBegin(GL_QUADS);
		setColor(color1);
		glVertex2d(x1, y1);
		glVertex2d(x2, y1);
		
		setColor(color2);
		glVertex2d(x2, y2);
		glVertex2d(x1, y2);
		glEnd();
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
		pushState();
		bindTexture(texture);
		setColor(tint);
		quadUV(quad, uvs);
		popState();
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
		quadTextured(quad, Rect.one(), texture, RGB.WHITE);
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
	 */
	public static void setupOrtho()
	{
		// fix projection for changed size
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		final Coord s = DisplaySystem.getSize();
		glViewport(0, 0, s.xi(), s.yi());
		glOrtho(0, s.x, (DisplaySystem.yAxisDown ? 1 : -1) * s.y, 0, -1000, 1000);
		
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
