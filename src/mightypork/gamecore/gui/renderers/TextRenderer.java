package mightypork.gamecore.gui.renderers;


import mightypork.gamecore.render.fonts.FontRenderer;
import mightypork.gamecore.render.fonts.GLFont;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;


public class TextRenderer extends PluggableRenderer {
	
	public static enum Align
	{
		LEFT, CENTER, RIGHT;
	}
	
	private FontRenderer font;
	private String text;
	private RGB color;
	private Align align;
	
	
	public TextRenderer(GLFont font, RGB color, Align align) {
		this(font, "MISSINGNO", color, align);
	}
	
	
	public TextRenderer(GLFont font, String text, RGB color, Align align) {
		this.font = new FontRenderer(font);
		this.text = text;
		this.color = color;
		this.align = align;
	}
	
	
	public void setFont(FontRenderer font)
	{
		this.font = font;
	}
	
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	
	public void setColor(RGB color)
	{
		this.color = color;
	}
	
	
	public void setAlign(Align align)
	{
		this.align = align;
	}
	
	
	public String getText()
	{
		return text;
	}
	
	@Override
	public void render()
	{
		render(getText());
	}
	
	
	public void render(String text)
	{
		final double h = getRect().getHeight();
		
		final double w = font.getWidth(text, h);
		
		final Coord start;
		
		switch (align) {
			case LEFT:
				start = getRect().getMin();
				break;
			
			case CENTER:
				start = getRect().getCenterV1().sub_ip(w / 2D, 0);
				break;
			
			case RIGHT:
			default:
				start = getRect().getX2Y1().sub_ip(w, 0);
				break;
		}
		
		font.draw(text, start, h, color);
	}
	
}
