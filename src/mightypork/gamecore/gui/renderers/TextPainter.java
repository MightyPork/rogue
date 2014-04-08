package mightypork.gamecore.gui.renderers;


import mightypork.gamecore.render.fonts.FontRenderer;
import mightypork.gamecore.render.fonts.GLFont;
import mightypork.gamecore.render.fonts.FontRenderer.Align;
import mightypork.utils.math.color.RGB;
import mightypork.utils.string.StringProvider;
import mightypork.utils.string.StringProvider.StringWrapper;




public class TextPainter extends PluggableRenderer {
	
	private final FontRenderer font;
	private RGB color;
	private Align align;
	private StringProvider text;
	
	
	public TextPainter(GLFont font) {
		this(font, Align.LEFT, RGB.WHITE);
	}
	
	
	public TextPainter(GLFont font, Align align, RGB color, String text) {
		this(font, align, color, new StringWrapper(text));
	}
	
	
	public TextPainter(GLFont font, Align align, RGB color, StringProvider text) {
		this.font = new FontRenderer(font);
		this.color = color;
		this.align = align;
		this.text = text;
	}
	
	
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
	public void usePtSize(boolean enable) {
		font.usePtSize(enable);
	}
	
	
	@Override
	public void render()
	{
		if (getText() == null) return;
		
		font.draw(getText(), getRect(), getAlign(), getColor());
	}
	
	
	public final void setColor(RGB color)
	{
		this.color = color;
	}
	
	
	public final void setAlign(Align align)
	{
		this.align = align;
	}
	
	
	public final void setText(String text)
	{
		this.text = new StringWrapper(text);
	}
	
	
	public final void setText(StringProvider text)
	{
		this.text = text;
	}
	
	
	protected RGB getColor()
	{
		return color;
	}
	
	
	protected Align getAlign()
	{
		return align;
	}
	
	
	protected String getText()
	{
		return text.getString();
	}
	
}
