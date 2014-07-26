package mightypork.gamecore.gui.components.painters;


import mightypork.gamecore.core.App;
import mightypork.gamecore.graphics.fonts.FontRenderer;
import mightypork.gamecore.graphics.fonts.IFont;
import mightypork.gamecore.gui.components.BaseComponent;
import mightypork.gamecore.gui.components.DynamicWidthComponent;
import mightypork.utils.math.AlignX;
import mightypork.utils.math.color.Color;
import mightypork.utils.math.color.pal.RGB;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.string.StringProvider;
import mightypork.utils.string.StringWrapper;


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
	public TextPainter(IFont font) {
		this(font, AlignX.LEFT, RGB.WHITE);
	}
	
	
	public TextPainter(IFont font, Color color, String text) {
		this(font, AlignX.LEFT, color, new StringWrapper(text));
	}
	
	
	public TextPainter(IFont font, Color color, StringProvider text) {
		this(font, AlignX.LEFT, color, text);
	}
	
	
	public TextPainter(IFont font, Color color) {
		this(font, AlignX.LEFT, color, (StringProvider) null);
	}
	
	
	public TextPainter(IFont font, AlignX align, Color color, String text) {
		this(font, align, color, new StringWrapper(text));
	}
	
	
	public TextPainter(IFont font, AlignX align, Color color, StringProvider text) {
		this.font = new FontRenderer(font);
		this.color = color;
		this.align = align;
		this.text = text;
	}
	
	
	public TextPainter(IFont font, AlignX align, Color color) {
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
		
		if (DEBUG_FONT_RENDER) App.gfx().quad(r, RGB.PINK.withAlpha(0.4));
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
