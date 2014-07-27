package mightypork.gamecore.backends.lwjgl.graphics.font;


import java.awt.Font;
import java.io.IOException;

import mightypork.utils.annotations.Alias;


/**
 * Font obtained from the OS
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@Alias(name = "FontNative")
public class DeferredLwjglFontFromSystem extends DeferredLwjglFont {
	
	/**
	 * A font from OS, found by name
	 * 
	 * @param fontName font family name
	 * @param chars chars to load; null to load basic chars only
	 * @param size size (pt)
	 */
	public DeferredLwjglFontFromSystem(String fontName, String chars, double size) {
		super(fontName, chars, size);
	}
	
	
	@Override
	protected Font getAwtFont(String resource, float size, int style) throws IOException
	{
		return new Font(resource, style, (int) size);
	}
	
}
