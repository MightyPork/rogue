package mightypork.gamecore.gui.components.painters;


import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.VisualComponent;
import mightypork.gamecore.resources.fonts.FontRenderer;
import mightypork.gamecore.resources.fonts.GLFont;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.gamecore.util.strings.StringProvider;
import mightypork.gamecore.util.strings.StringWrapper;


/**
 * Text painting component.<br>
 * Drawing values are obtained through getters, so overriding getters can be
 * used to change parameters dynamically.
 * 
 * @author MightyPork
 */
public class TextPainter extends VisualComponent {
	
	private final FontRenderer font;
	private Color color;
	private AlignX align;
	private StringProvider text;
	private boolean shadow;
	
	private Color shadowColor = Color.BLACK;
	private Vect shadowOffset = Vect.make(2, 2);
	
	
	/**
	 * @param font font to use
	 */
	public TextPainter(GLFont font)
	{
		this(font, AlignX.LEFT, Color.WHITE);
	}
	
	
	/**
	 * Constructor for fixed text
	 * 
	 * @param font font to use
	 * @param align text align
	 * @param color default color
	 * @param text drawn text
	 */
	public TextPainter(GLFont font, AlignX align, Color color, String text)
	{
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
	public TextPainter(GLFont font, AlignX align, Color color, StringProvider text)
	{
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
	public TextPainter(GLFont font, AlignX align, Color color)
	{
		this(font, align, color, (StringProvider) null);
	}
	
	
	@Override
	public void renderComponent()
	{
		if (text == null) return;
		
		final String str = text.getString();
		final Rect rect = getRect();
		
		if (shadow) {
			font.draw(str, rect.round(), align, shadowColor);
		}
		
		font.draw(str, rect.move(shadowOffset.neg()).round(), align, color);
	}
	
	
	public void setShadow(Color color, Vect offset)
	{
		setShadow(true);
		setShadowColor(color);
		setShadowOffset(offset);
	}
	
	
	public void setShadow(boolean shadow)
	{
		this.shadow = shadow;
	}
	
	
	public void setShadowColor(Color shadowColor)
	{
		this.shadowColor = shadowColor;
	}
	
	
	public void setShadowOffset(Vect shadowOffset)
	{
		this.shadowOffset = shadowOffset;
	}
	
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	
	public void setAlign(AlignX align)
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
