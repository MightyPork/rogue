package mightypork.gamecore.resources.fonts;


import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import mightypork.utils.logging.LoggedName;


/**
 * Font obtained from the OS
 * 
 * @author MightyPork
 */
@LoggedName(name = "FontNative")
public class DeferredFontNative extends DeferredFont {
	
	/**
	 * A font from OS, found by name
	 * 
	 * @param fontName font family name
	 * @param extraChars extra chars (0-255 loaded by default)
	 * @param size size (pt)
	 */
	public DeferredFontNative(String fontName, String extraChars, double size) {
		super(fontName, extraChars, size);
	}
	
	
	/**
	 * A font from OS, found by name
	 * 
	 * @param fontName font family name
	 * @param extraChars extra chars (0-255 loaded by default)
	 * @param size size (pt)
	 * @param style font style
	 */
	public DeferredFontNative(String fontName, String extraChars, double size, FontStyle style) {
		super(fontName, extraChars, size, style);
	}
	
	
	/**
	 * A font from OS, found by name
	 * 
	 * @param fontName font family name
	 * @param extraChars extra chars (0-255 loaded by default)
	 * @param size size (pt)
	 * @param style font style
	 * @param antialias use antialiasing
	 */
	public DeferredFontNative(String fontName, String extraChars, double size, FontStyle style, boolean antialias) {
		super(fontName, extraChars, size, style, antialias);
	}
	
	
	@Override
	protected Font getAwtFont(String resource, float size, int style) throws FontFormatException, IOException
	{
		return new Font(resource, style, (int) size);
	}
	
}
