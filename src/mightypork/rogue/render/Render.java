package mightypork.rogue.render;


import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import mightypork.rogue.render.textures.TxQuad;
import mightypork.utils.files.FileUtils;
import mightypork.utils.logging.Log;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.coord.Rect;

import org.newdawn.slick.opengl.SlickCallable;
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
	
	private static boolean inited = false;
	
	
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
	
	
	public static void rotateX(double angle)
	{
		rotate(angle, AXIS_X);
	}
	
	
	public static void rotateY(double angle)
	{
		rotate(angle, AXIS_Y);
	}
	
	
	public static void rotateZ(double angle)
	{
		rotate(angle, AXIS_Z);
	}
	
	
	public static void rotate(double angle, Coord axis)
	{
		Coord vec = axis.norm(1);
		glRotated(angle, vec.x, vec.y, vec.z);
	}
	
	
	public static void pushState()
	{
		SlickCallable.enterSafeBlock();
	}
	
	
	public static void popState()
	{
		SlickCallable.leaveSafeBlock();
	}
	
	
	/**
	 * Load texture
	 * 
	 * @param resourcePath
	 * @return the loaded texture
	 */
	public static Texture loadTexture(String resourcePath)
	{
		if (!inited) {
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			inited = true;
		}
		
		try {
			
			String ext = FileUtils.getExtension(resourcePath).toUpperCase();
			
			Log.f3("Loading texture " + ext + " at " + resourcePath);
			
			Texture texture = TextureLoader.getTexture(ext, ResourceLoader.getResourceAsStream(resourcePath));
			
			if (texture == null) {
				Log.w("Texture " + resourcePath + " could not be loaded.");
			}
			
			return texture;
			
		} catch (IOException e) {
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
		double left = quad.xMin();
		double bottom = quad.yMin();
		double right = quad.yMax();
		double top = quad.yMax();
		
		// draw with color
		unbindTexture();
		
		// quad
		glBegin(GL_QUADS);
		glVertex2d(left, top);
		glVertex2d(right, top);
		glVertex2d(right, bottom);
		glVertex2d(left, bottom);
		glEnd();
	}
	
	
	/**
	 * Render textured rect (texture must be binded already)
	 * 
	 * @param quad rectangle (px)
	 * @param uvs texture coords (0-1)
	 */
	private static void quadUV(Rect quad, Rect uvs)
	{
		double left = quad.xMin();
		double bottom = quad.yMin();
		double right = quad.xMax();
		double top = quad.yMax();
		
		double tleft = uvs.xMin();
		double tbottom = uvs.yMin();
		double tright = uvs.xMax();
		double ttop = uvs.yMax();
		
		// quad with texture
		glBegin(GL_QUADS);
		glTexCoord2d(tleft, ttop);
		glVertex2d(left, top);
		glTexCoord2d(tright, ttop);
		glVertex2d(right, top);
		glTexCoord2d(tright, tbottom);
		glVertex2d(right, bottom);
		glTexCoord2d(tleft, tbottom);
		glVertex2d(left, bottom);
		glEnd();
	}
	
	
	public static void quadGradH(Rect quad, RGB colorLeft, RGB colorRight)
	{
		double left = quad.xMin();
		double bottom = quad.yMin();
		double right = quad.yMax();
		double top = quad.yMax();
		
		// draw with color
		unbindTexture();
		
		glBegin(GL_QUADS);
		setColor(colorLeft);
		glVertex2d(left, top);
		setColor(colorRight);
		glVertex2d(right, top);
		
		setColor(colorRight);
		glVertex2d(right, bottom);
		setColor(colorLeft);
		glVertex2d(left, bottom);
		glEnd();
	}
	
	
	public static void quadGradV(Rect quad, RGB colorTop, RGB colorBottom)
	{
		double left = quad.xMin();
		double bottom = quad.yMin();
		double right = quad.yMax();
		double top = quad.yMax();
		
		// draw with color
		unbindTexture();
		
		glBegin(GL_QUADS);
		setColor(colorTop);
		glVertex2d(left, top);
		glVertex2d(right, top);
		
		setColor(colorBottom);
		glVertex2d(right, bottom);
		glVertex2d(left, bottom);
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
		bindTexture(texture);
		setColor(tint);
		quadUV(quad, uvs);
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
	
}
