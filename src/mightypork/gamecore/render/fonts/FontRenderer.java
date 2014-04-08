package mightypork.gamecore.render.fonts;


import mightypork.gamecore.render.Render;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.coord.Rect;


public class FontRenderer {
	
	private final GLFont font;
	
	
	public FontRenderer(GLFont font) {
		
		if (font == null) throw new NullPointerException("Font cannot be null.");
		
		this.font = font;
	}
	
	
	public void draw(String text, Coord pos, double height, RGB color)
	{
		Render.pushState();
		
		Render.translate(pos.round());
		Render.scaleXY(getScale(height));
		
		font.draw(text, color);
		
		Render.popState();
	}
	
	
	public Coord getNeededSpace(String text, double height)
	{
		return font.getNeededSpace(text).mul(getScale(height));
	}
	
	
	public double getWidth(String text, double height)
	{
		return getNeededSpace(text, height).x;
	}
	
	
	public Rect getBounds(String text, Coord pos, double height)
	{
		return Rect.fromSize(getNeededSpace(text, height)).add(pos);
	}
	
	
	private double getScale(double height)
	{
		return height / font.getHeight();
	}
}
