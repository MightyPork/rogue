package mightypork.gamecore.render.fonts;


import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.render.Render;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.vect.Vect;
import mightypork.util.math.color.Color;


/**
 * Font renderer
 * 
 * @author MightyPork
 */
public class FontRenderer {
	
	private GLFont font;
	
	private Color color;
	
	
	/**
	 * @param font used font
	 */
	public FontRenderer(GLFont font) {
		this(font, Color.WHITE);
	}
	
	
	/**
	 * @param font used font
	 * @param color drawing color
	 */
	public FontRenderer(GLFont font, Color color) {
		this.font = font;
		this.color = color;
	}
	
	
	/**
	 * Get region needed to draw text at size
	 * 
	 * @param text text to draw
	 * @param height drawing height
	 * @return taken space (width, height)
	 */
	public Vect getNeededSpace(String text, double height)
	{
		return font.getNeededSpace(text).mul(getScale(height));
	}
	
	
	/**
	 * Get width needed to draw text at size
	 * 
	 * @param text text to draw
	 * @param height drawing height
	 * @return needed width
	 */
	public double getWidth(String text, double height)
	{
		return getNeededSpace(text, height).x();
	}
	
	
	private double getScale(double height)
	{
		return height / font.getLineHeight();
	}
	
	
	/**
	 * Change drawing font
	 * 
	 * @param font font to use for drawing
	 */
	public void setFont(GLFont font)
	{
		this.font = font;
	}
	
	
	/**
	 * Set drawing color
	 * 
	 * @param color color
	 */
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	
	/**
	 * Draw on screen
	 * 
	 * @param text text to draw
	 * @param pos origin (min coord)
	 * @param height drawing height
	 * @param color drawing color
	 */
	public void draw(String text, Vect pos, double height, Color color)
	{
		Render.pushMatrix();
		
		Render.translateXY(pos.round());
		Render.scaleXY(getScale(height));
		
		font.draw(text, color);
		
		Render.popMatrix();
	}
	
	
	/**
	 * Draw on screen
	 * 
	 * @param text text to draw
	 * @param bounds drawing bounds (height for font height, horizontal bounds
	 *            for align)
	 * @param align horizontal alignment (with respect to bounds)
	 */
	public void draw(String text, Rect bounds, AlignX align)
	{
		this.draw(text, bounds, align, this.color);
	}
	
	
	/**
	 * Draw on screen
	 * 
	 * @param text text to draw
	 * @param bounds drawing bounds (height for font height, horizontal bounds
	 *            for align)
	 * @param align horizontal alignment (with respect to bounds)
	 * @param color drawing color
	 */
	public void draw(String text, Rect bounds, AlignX align, Color color)
	{
		Vect start;
		
		switch (align) {
			case LEFT:
				start = bounds.topLeft();
				break;
			
			case CENTER:
				start = bounds.topCenter();
				break;
			
			case RIGHT:
			default:
				start = bounds.topRight();
				break;
		}
		
		draw(text, start, bounds.height().value(), align, color);
	}
	
	
	/**
	 * Draw on screen
	 * 
	 * @param text text to draw
	 * @param pos origin (min coord)
	 * @param height drawing height
	 * @param align horizontal alignment
	 */
	public void draw(String text, Vect pos, double height, AlignX align)
	{
		draw(text, pos, height, align, this.color);
	}
	
	
	/**
	 * Draw on screen
	 * 
	 * @param text text to draw
	 * @param pos origin (min coord)
	 * @param height drawing height
	 * @param align horizontal alignment
	 * @param color drawing color
	 */
	public void draw(String text, Vect pos, double height, AlignX align, Color color)
	{
		
		final double w = getWidth(text, height);
		
		Vect start;
		
		switch (align) {
			case LEFT:
				start = pos;
				break;
			
			case CENTER:
				start = pos.sub(w / 2D, 0);
				break;
			
			case RIGHT:
			default:
				start = pos.sub(w, 0);
				break;
		}
		
		draw(text, start, height, color);
	}
	
}
