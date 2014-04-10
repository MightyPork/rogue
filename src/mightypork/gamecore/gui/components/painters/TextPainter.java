package mightypork.gamecore.gui.components.painters;


import mightypork.gamecore.gui.components.PluggableRenderer;
import mightypork.gamecore.render.fonts.FontRenderer;
import mightypork.gamecore.render.fonts.FontRenderer.Align;
import mightypork.gamecore.render.fonts.GLFont;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.coord.Rect;
import mightypork.utils.string.StringProvider;
import mightypork.utils.string.StringProvider.StringWrapper;


/**
 * Text painting component.<br>
 * Drawing values are obtained through getters, so overriding getters can be
 * used to change parameters dynamically.
 * 
 * @author MightyPork
 */
public class TextPainter extends PluggableRenderer {
	
	private final FontRenderer font;
	private RGB color;
	private Align align;
	private StringProvider text;
	private boolean shadow;
	
	private RGB shadowColor = RGB.BLACK;
	private Coord shadowOffset = Coord.one();
	
	
	/**
	 * @param font font to use
	 */
	public TextPainter(GLFont font) {
		this(font, Align.LEFT, RGB.WHITE);
	}
	
	
	/**
	 * Constructor for fixed text
	 * 
	 * @param font font to use
	 * @param align text align
	 * @param color default color
	 * @param text drawn text
	 */
	public TextPainter(GLFont font, Align align, RGB color, String text) {
		this(font, align, color, new StringWrapper(text));
	}
	
	
	/**
	 * COnstructor for changeable text.
	 * 
	 * @param font font to use
	 * @param align text align
	 * @param color default color
	 * @param text text provider
	 */
	public TextPainter(GLFont font, Align align, RGB color, StringProvider text) {
		this.font = new FontRenderer(font);
		this.color = color;
		this.align = align;
		this.text = text;
	}
	
	
	/**
	 * @param font font to use
	 * @param align text align
	 * @param color default color
	 */
	public TextPainter(GLFont font, Align align, RGB color) {
		this(font, align, color, (StringProvider) null);
	}
	
	
	@Override
	public void render()
	{
		if (text == null) return;
		
		final String str = text.getString();
		final Rect rect = getRect();
		
		if (shadow) {
			font.draw(str, rect.add(shadowOffset), align, shadowColor);
		}
		font.draw(str, rect, align, color);
	}
	
	
	public void setShadow(RGB color, Coord offset)
	{
		setShadow(true);
		setShadowColor(color);
		setShadowOffset(offset);
	}
	
	
	public void setShadow(boolean shadow)
	{
		this.shadow = shadow;
	}
	
	
	public void setShadowColor(RGB shadowColor)
	{
		this.shadowColor = shadowColor;
	}
	
	
	public void setShadowOffset(Coord shadowOffset)
	{
		this.shadowOffset = shadowOffset;
	}
	
	
	public void setColor(RGB color)
	{
		this.color = color;
	}
	
	
	public void setAlign(Align align)
	{
		this.align = align;
	}
	
	
	public void setText(String text)
	{
		this.text = new StringWrapper(text);
	}
	
	
	public void setText(StringProvider text)
	{
		this.text = text;
	}
	
}
