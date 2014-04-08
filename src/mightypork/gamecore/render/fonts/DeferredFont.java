package mightypork.gamecore.render.fonts;


import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import mightypork.gamecore.loading.BaseDeferredResource;
import mightypork.gamecore.loading.MustLoadInMainThread;
import mightypork.gamecore.render.textures.FilterMode;
import mightypork.utils.files.FileUtils;
import mightypork.utils.logging.LoggedName;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;


/**
 * Font obtained from a resource file (TTF).
 * 
 * @author MightyPork
 */
@MustLoadInMainThread
@LoggedName(name = "Font")
public class DeferredFont extends BaseDeferredResource implements GLFont {
	
	public static enum FontStyle
	{
		PLAIN(Font.PLAIN), BOLD(Font.BOLD), ITALIC(Font.ITALIC), BOLD_ITALIC(Font.BOLD + Font.ITALIC);
		
		int numval;
		
		
		private FontStyle(int style) {
			this.numval = style;
		}
	}
	
	private SlickFont font = null;
	private final double size;
	private final FontStyle style;
	private final String extraChars;
	private FilterMode filter;
	
	
	/**
	 * A font from resource
	 * 
	 * @param resourcePath resource to load
	 * @param extraChars extra chars (0-255 loaded by default)
	 * @param size size (px)
	 */
	public DeferredFont(String resourcePath, String extraChars, double size) {
		this(resourcePath, extraChars, size, FontStyle.PLAIN, FilterMode.NEAREST);
	}
	
	
	/**
	 * A font from resource
	 * 
	 * @param resourcePath resource to load
	 * @param extraChars extra chars (0-255 loaded by default)
	 * @param size size (pt)
	 * @param style font style
	 */
	public DeferredFont(String resourcePath, String extraChars, double size, FontStyle style) {
		this(resourcePath, extraChars, size, style, FilterMode.NEAREST);
	}
	
	
	/**
	 * A font from resource
	 * 
	 * @param resourcePath resource to load
	 * @param extraChars extra chars (0-255 loaded by default)
	 * @param size size (pt)
	 * @param style font style
	 * @param filter gl filtering mode
	 */
	public DeferredFont(String resourcePath, String extraChars, double size, FontStyle style, FilterMode filter) {
		super(resourcePath);
		this.size = size;
		this.style = style;
		this.extraChars = extraChars;
		this.filter = filter;
	}
	
	
	@Override
	protected final void loadResource(String path) throws FontFormatException, IOException
	{
		final Font awtFont = getAwtFont(path, (float) size, style.numval);
		
		font = new SlickFont(awtFont, filter, extraChars);
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
		InputStream in = null;
		
		try {
			
			in = FileUtils.getResource(resource);
			
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, in);
			
			awtFont = awtFont.deriveFont(size);
			awtFont = awtFont.deriveFont(style);
			
			return awtFont;
			
		} finally {
			try {
				if (in != null) in.close();
			} catch (final IOException e) {
				//pass
			}
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
	public Coord getNeededSpace(String text)
	{
		if (!ensureLoaded()) return Coord.zero();
		
		return font.getNeededSpace(text);
	}
	
	
	/**
	 * @return font height
	 */
	@Override
	public int getGlyphHeight()
	{
		if (!ensureLoaded()) return 0;
		
		return font.getGlyphHeight();
	}
	
	@Override
	public int getSize()
	{
		if (!ensureLoaded()) return 0;
		
		return font.getSize();
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
