package mightypork.gamecore.render.fonts;


import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;

import mightypork.gamecore.render.Render;
import mightypork.gamecore.render.textures.FilterMode;
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
	private FilterMode filter;
	private final int fsize;
	
	
	/**
	 * A font with ASCII and extra chars
	 * 
	 * @param font font to load
	 * @param filtering filtering mode
	 * @param extraChars extra chars to load
	 */
	public SlickFont(Font font, FilterMode filtering, String extraChars) {
		
		this.filter = filtering;
		this.fsize = font.getSize();
		ttf = new TrueTypeFont(font, true, stripASCII(extraChars));
	}
	
	
	public void setFiltering(FilterMode filter)
	{
		this.filter = filter;
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
	
	
	private void prepareForRender()
	{
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter.num);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter.num);
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
		Render.pushState();
		
		prepareForRender();
		ttf.drawString(0, 0, str, rgbToSlickColor(color));
		
		Render.popState();
	}
	
	
	private static Color rgbToSlickColor(RGB rgb)
	{
		return new Color((float) rgb.r, (float) rgb.g, (float) rgb.b, (float) rgb.a);
	}
	
	
	@Override
	public Coord getNeededSpace(String text)
	{
		return new Coord(getWidth(text), getGlyphHeight());
	}
	
	
	@Override
	public int getGlyphHeight()
	{
		return ttf.getHeight();
	}
	
	
	@Override
	public int getWidth(String text)
	{
		return ttf.getWidth(text);
	}
	
	
	@Override
	public int getSize()
	{
		return fsize;
	}
	
}
