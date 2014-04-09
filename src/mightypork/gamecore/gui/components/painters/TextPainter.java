package mightypork.gamecore.gui.components.painters;


import mightypork.gamecore.gui.components.PluggableRenderer;
import mightypork.gamecore.render.fonts.FontRenderer;
import mightypork.gamecore.render.fonts.FontRenderer.Align;
import mightypork.gamecore.render.fonts.GLFont;
import mightypork.utils.math.color.RGB;
import mightypork.utils.string.StringProvider;
import mightypork.utils.string.StringProvider.StringWrapper;


/**
 * Text painting component
 * 
 * @author MightyPork
 */
public class TextPainter extends PluggableRenderer {
	
	private final FontRenderer font;
	private RGB color;
	private Align align;
	private StringProvider text;
	
	
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
	
	
	/**
	 * Use size specified during font init instead of size provided by
	 * {@link GLFont} instance (measured from tile heights.<br>
	 * This is better when the font is drawn in original size, but can cause
	 * weird artifacts if the font is scaled up.
	 * 
	 * @param enable use it
	 */
	public void usePtSize(boolean enable)
	{
		font.usePtSize(enable);
	}
	
	
	@Override
	public void render()
	{
		if (getText() == null) return;
		
		font.draw(getText(), getRect(), getAlign(), getColor());
	}
	
	
	/**
	 * Assign paint color
	 * 
	 * @param color paint color
	 */
	public void setColor(RGB color)
	{
		this.color = color;
	}
	
	
	/**
	 * Set text align
	 * 
	 * @param align text align
	 */
	public void setAlign(Align align)
	{
		this.align = align;
	}
	
	
	/**
	 * Set drawn text
	 * 
	 * @param text text
	 */
	public void setText(String text)
	{
		this.text = new StringWrapper(text);
	}
	
	
	/**
	 * Set drawn text provider
	 * 
	 * @param text text provider
	 */
	public void setText(StringProvider text)
	{
		this.text = text;
	}
	
	
	/**
	 * Get draw color.<br>
	 * <i>This getter is used for getting drawing color; so if it's overriden,
	 * the draw color can be adjusted in real time.</i>
	 * 
	 * @return drawing color
	 */
	public RGB getColor()
	{
		return color;
	}
	
	
	/**
	 * Get text align.<br>
	 * <i>This getter is used for getting align; so if it's overidden, the align
	 * can be adjusted in real time.</i>
	 * 
	 * @return text align
	 */
	public Align getAlign()
	{
		return align;
	}
	
	
	/**
	 * Get text to draw.<br>
	 * <i>This getter is used for getting text to draw; so if it's overidden,
	 * the text can be adjusted in real time. (alternative to using
	 * StringProvider)</i>
	 * 
	 * @return text align
	 */
	public String getText()
	{
		return text.getString();
	}
	
}
