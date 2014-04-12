package mightypork.gamecore.render.fonts.impl;


import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import mightypork.gamecore.loading.DeferredResource;
import mightypork.gamecore.loading.MustLoadInMainThread;
import mightypork.gamecore.render.fonts.GLFont;
import mightypork.gamecore.render.textures.FilterMode;
import mightypork.utils.files.FileUtils;
import mightypork.utils.logging.LoggedName;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Vec;
import mightypork.utils.math.coord.VecView;


/**
 * Font obtained from a resource file (TTF).
 * 
 * @author MightyPork
 */
@MustLoadInMainThread
@LoggedName(name = "Font")
public class DeferredFont extends DeferredResource implements GLFont {
	
	public static enum FontStyle
	{
		PLAIN(Font.PLAIN), BOLD(Font.BOLD), ITALIC(Font.ITALIC), BOLD_ITALIC(Font.BOLD + Font.ITALIC);
		
		int numval;
		
		
		private FontStyle(int style) {
			this.numval = style;
		}
	}
	
	private GLFont font = null;
	private double size;
	private FontStyle style;
	private String chars;
	private FilterMode filter;
	private boolean antialias;
	
	
	/**
	 * A font from resource; setters shall be used to specify parameters in
	 * greater detail.
	 * 
	 * @param resourcePath resource to load
	 * @param chars chars to load; null to load basic chars only
	 * @param size size (px)
	 */
	public DeferredFont(String resourcePath, String chars, double size) {
		this(resourcePath, chars, size, FontStyle.PLAIN, true, FilterMode.LINEAR);
	}
	
	
	/**
	 * A font from resource
	 * 
	 * @param resourcePath resource to load
	 * @param chars chars to load; null to load basic chars only
	 * @param size size (px)
	 * @param style font style
	 * @param antialias use antialiasing for caching texture
	 * @param filter gl filtering mode
	 */
	public DeferredFont(String resourcePath, String chars, double size, FontStyle style, boolean antialias, FilterMode filter) {
		super(resourcePath);
		this.size = size;
		this.style = style;
		this.chars = chars;
		this.filter = filter;
		this.antialias = antialias;
	}
	
	
	public synchronized void setSize(double size)
	{
		this.size = size;
	}
	
	
	public synchronized void setStyle(FontStyle style)
	{
		this.style = style;
	}
	
	
	public synchronized void setChars(String chars)
	{
		this.chars = chars;
	}
	
	
	public synchronized void setFilter(FilterMode filter)
	{
		this.filter = filter;
	}
	
	
	public synchronized void setAntialias(boolean antialias)
	{
		this.antialias = antialias;
	}
	
	
	@Override
	protected synchronized final void loadResource(String path) throws FontFormatException, IOException
	{
		final Font awtFont = getAwtFont(path, (float) size, style.numval);
		
		font = new CachedFont(awtFont, antialias, filter, chars);
	}
	
	
	/**
	 * Get a font for a resource path / name
	 * 
	 * @param resource resource to load
	 * @param size font size (pt)
	 * @param style font style
	 * @return the {@link Font}
	 * @throws FontFormatException
	 * @throws IOException
	 */
	protected Font getAwtFont(String resource, float size, int style) throws FontFormatException, IOException
	{
		try(InputStream in = FileUtils.getResource(resource)) {
			
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, in);
			
			awtFont = awtFont.deriveFont(size);
			awtFont = awtFont.deriveFont(style);
			
			return awtFont;
		}
	}
	
	
	/**
	 * Draw string
	 * 
	 * @param str string to draw
	 * @param color draw color
	 */
	@Override
	public void draw(String str, RGB color)
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
	public VecView getNeededSpace(String text)
	{
		if (!ensureLoaded()) return Vec.ZERO;
		
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
