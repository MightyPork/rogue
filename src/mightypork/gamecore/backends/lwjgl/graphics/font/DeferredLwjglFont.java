package mightypork.gamecore.backends.lwjgl.graphics.font;


import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import mightypork.gamecore.graphics.fonts.DeferredFont;
import mightypork.gamecore.graphics.fonts.IFont;
import mightypork.gamecore.resources.loading.MustLoadInRenderingContext;
import mightypork.utils.annotations.Alias;
import mightypork.utils.files.FileUtil;
import mightypork.utils.math.color.Color;
import mightypork.utils.math.constraints.vect.Vect;


/**
 * Font obtained from a resource file (TTF).
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@MustLoadInRenderingContext
@Alias(name = "Font")
public class DeferredLwjglFont extends DeferredFont {
	
	private IFont font = null;
	
	
	/**
	 * A font from resource
	 * 
	 * @param resourcePath resource to load
	 * @param chars chars to load; null to load basic chars only
	 * @param size size (px)
	 */
	public DeferredLwjglFont(String resourcePath, String chars, double size) {
		super(resourcePath);
		this.size = size;
		this.chars = chars;
	}
	
	
	@Override
	protected synchronized final void loadResource(String path) throws IOException
	{
		final Font awtFont = getAwtFont(path, (float) size, style.numval);
		
		font = new LwjglTextureBackedFont(awtFont, antialias, filter, chars);
		font.setDiscardRatio(discardTop, discardBottom);
	}
	
	
	/**
	 * Get a font for a resource path / name
	 * 
	 * @param resource resource to load
	 * @param size font size (pt)
	 * @param style font style
	 * @return the {@link Font}
	 * @throws IOException
	 */
	protected Font getAwtFont(String resource, float size, int style) throws IOException
	{
		try (InputStream in = FileUtil.getResource(resource)) {
			
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, in);
			
			awtFont = awtFont.deriveFont(size);
			awtFont = awtFont.deriveFont(style);
			
			return awtFont;
		} catch (final FontFormatException e) {
			throw new IOException("Could not load font, bad format.", e);
		}
	}
	
	
	/**
	 * Draw string
	 * 
	 * @param str string to draw
	 * @param color draw color
	 */
	@Override
	public void draw(String str, Color color)
	{
		if (!ensureLoaded()) return;
		
		font.draw(str, color);
	}
	
	
	/**
	 * Get size needed to render give string
	 * 
	 * @param text string to check
	 * @return coord (width, height)
	 */
	@Override
	public Vect getNeededSpace(String text)
	{
		if (!ensureLoaded()) return Vect.ZERO;
		
		return font.getNeededSpace(text);
	}
	
	
	/**
	 * @return font height
	 */
	@Override
	public int getLineHeight()
	{
		if (!ensureLoaded()) return 0;
		
		return font.getLineHeight();
	}
	
	
	@Override
	public int getFontSize()
	{
		if (!ensureLoaded()) return 0;
		
		return font.getFontSize();
	}
	
	
	@Override
	public int getWidth(String text)
	{
		return font.getWidth(text);
	}
	
	
	@Override
	public void destroy()
	{
		// this will have to suffice
		font = null;
	}
}
