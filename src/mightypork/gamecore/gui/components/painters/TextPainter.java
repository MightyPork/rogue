package mightypork.gamecore.gui.components.painters;


import mightypork.dynmath.num.Num;
import mightypork.dynmath.rect.Rect;
import mightypork.dynmath.vect.Vect;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.BaseComponent;
import mightypork.gamecore.gui.components.DynamicWidthComponent;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.fonts.FontRenderer;
import mightypork.gamecore.resources.fonts.GLFont;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.strings.StringProvider;
import mightypork.gamecore.util.strings.StringWrapper;


/**
 * Text painting component.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class TextPainter extends BaseComponent implements DynamicWidthComponent {
	
	private static final boolean DEBUG_FONT_RENDER = false;
	private final FontRenderer font;
	private Color color;
	private AlignX align;
	private StringProvider text;
	private boolean shadow;
	
	private double yPaddingPerc = 0;
	
	private Color shadowColor = RGB.BLACK;
	private Vect shadowOffset = Vect.make(2, 2);
	
	
	/**
	 * @param font font to use
	 */
	public TextPainter(GLFont font)
	{
		this(font, AlignX.LEFT, RGB.WHITE);
	}
	
	
	public TextPainter(GLFont font, Color color, String text)
	{
		this(font, AlignX.LEFT, color, new StringWrapper(text));
	}
	
	
	public TextPainter(GLFont font, Color color, StringProvider text)
	{
		this(font, AlignX.LEFT, color, text);
	}
	
	
	public TextPainter(GLFont font, Color color)
	{
		this(font, AlignX.LEFT, color, (StringProvider) null);
	}
	
	
	public TextPainter(GLFont font, AlignX align, Color color, String text)
	{
		this(font, align, color, new StringWrapper(text));
	}
	
	
	public TextPainter(GLFont font, AlignX align, Color color, StringProvider text)
	{
		this.font = new FontRenderer(font);
		this.color = color;
		this.align = align;
		this.text = text;
	}
	
	
	public TextPainter(GLFont font, AlignX align, Color color)
	{
		this(font, align, color, (StringProvider) null);
	}
	
	
	@Override
	public void renderComponent()
	{
		if (text == null) return;
		
		final String str = text.getString();
		
		final Num shrY = height().perc(yPaddingPerc);
		
		final Rect rect = getRect().shrink(Num.ZERO, shrY);
		
		if (shadow) {
			font.draw(str, rect.round(), align, shadowColor);
		}
		
		final Rect r = (shadow ? rect.move(shadowOffset.neg()) : rect).round();
		font.draw(str, r, align, color);
		
		if (DEBUG_FONT_RENDER) Render.quadColor(r, RGB.PINK.withAlpha(0.4));
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
	
	
	public void setVPaddingPercent(double percY)
	{
		yPaddingPerc = percY;
	}
	
	
	@Override
	public double computeWidth(double height)
	{
		return font.getWidth(this.text.getString(), height * ((100 - yPaddingPerc * 2) / 100D));
	}
}
