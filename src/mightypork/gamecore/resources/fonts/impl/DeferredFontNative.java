package mightypork.gamecore.resources.fonts.impl;


import java.awt.Font;
import java.io.IOException;

import mightypork.gamecore.logging.LogAlias;
import mightypork.gamecore.resources.textures.FilterMode;


/**
 * Font obtained from the OS
 * 
 * @author MightyPork
 */
@LogAlias(name = "FontNative")
public class DeferredFontNative extends DeferredFont {
	
	/**
	 * A font from OS, found by name
	 * 
	 * @param fontName font family name
	 * @param extraChars extra chars (0-255 loaded by default)
	 * @param size size (pt)
	 * @param style font style
	 * @param antialias use antialiasing when drawn on the cache texture
	 * @param filter GL filtering mode
	 */
	public DeferredFontNative(String fontName, String extraChars, double size, FontStyle style, boolean antialias, FilterMode filter)
	{
		super(fontName, extraChars, size, style, antialias, filter);
	}
	
	
	@Override
	protected Font getAwtFont(String resource, float size, int style) throws IOException
	{
		return new Font(resource, style, (int) size);
	}
	
}
