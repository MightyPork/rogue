package mightypork.rogue.fonts;


import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;

import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;


/**
 * Wrapper for slick font
 * 
 * @author MightyPork
 */
public class SlickFont implements GLFont {
	
	private final TrueTypeFont ttf;
	
	
	/**
	 * A font with ASCII and extra chars
	 * 
	 * @param font font to load
	 * @param antiAlias antialiasing
	 * @param extraChars extra chars to load
	 */
	public SlickFont(Font font, boolean antiAlias, String extraChars) {
		
		ttf = new TrueTypeFont(font, antiAlias, stripASCII(extraChars));
	}
	
	
	private static char[] stripASCII(String chars)
	{
		if (chars == null) return null;
		
		final StringBuilder sb = new StringBuilder();
		for (final char c : chars.toCharArray()) {
			if (c <= 255) continue; // already included in default set
			sb.append(c);
		}
		
		return sb.toString().toCharArray();
	}
	
	
	private static void prepareForRender()
	{
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	}
	
	
	/**
	 * Draw in color
	 * 
	 * @param str string to draw
	 * @param color text color
	 */
	@Override
	public void draw(String str, RGB color)
	{
		prepareForRender();
		ttf.drawString(0, 0, str, rgbToSlickColor(color));
	}
	
	
	/**
	 * Draw substring in color
	 * 
	 * @param str string to draw
	 * @param color text color
	 * @param startIndex first char to draw
	 * @param endIndex last char to draw (INCLUDING!)
	 */
	@Override
	public void draw(String str, RGB color, int startIndex, int endIndex)
	{
		prepareForRender();
		ttf.drawString(0, 0, str, rgbToSlickColor(color), startIndex, endIndex);
	}
	
	
	/**
	 * Draw in white
	 * 
	 * @param str chars to draw
	 */
	@Override
	public void draw(String str)
	{
		prepareForRender();
		ttf.drawString(0, 0, str);
	}
	
	
	private static Color rgbToSlickColor(RGB rgb)
	{
		return new Color((float) rgb.r, (float) rgb.g, (float) rgb.b, (float) rgb.a);
	}
	
	
	@Override
	public Coord getNeededSpace(String text)
	{
		return new Coord(getWidth(text), getHeight());
	}
	
	
	@Override
	public int getHeight()
	{
		return ttf.getHeight();
	}
	
	
	@Override
	public int getWidth(String text)
	{
		return ttf.getWidth(text);
	}
	
}
