package mightypork.gamecore.render.fonts;


import mightypork.gamecore.render.Render;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.rect.Rect;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectMutable;
import mightypork.utils.math.vect.VectView;


/**
 * Font renderer
 * 
 * @author MightyPork
 */
public class FontRenderer {
	
	private GLFont font;
	
	public static enum Align
	{
		LEFT, CENTER, RIGHT;
	}
	
	private RGB color;
	
	
	/**
	 * @param font used font
	 */
	public FontRenderer(GLFont font) {
		this(font, RGB.WHITE);
	}
	
	
	/**
	 * @param font used font
	 * @param color drawing color
	 */
	public FontRenderer(GLFont font, RGB color) {
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
	public void setColor(RGB color)
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
	public void draw(String text, Vect pos, double height, RGB color)
	{
		Render.pushMatrix();
		
		Render.translate(pos.value().round());
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
	public void draw(String text, Rect bounds, Align align)
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
	public void draw(String text, Rect bounds, Align align, RGB color)
	{
		VectView start;
		
		switch (align) {
			case LEFT:
				start = bounds.getTopLeft();
				break;
			
			case CENTER:
				start = bounds.getTopCenter();
				break;
			
			case RIGHT:
			default:
				start = bounds.getTopRight();
				break;
		}
		
		draw(text, start, bounds.getHeight(), align, color);
	}
	
	
	/**
	 * Draw on screen
	 * 
	 * @param text text to draw
	 * @param pos origin (min coord)
	 * @param height drawing height
	 * @param align horizontal alignment
	 */
	public void draw(String text, Vect pos, double height, Align align)
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
	public void draw(String text, Vect pos, double height, Align align, RGB color)
	{
		
		final double w = getWidth(text, height);
		
		final VectMutable start = pos.mutable();
		
		switch (align) {
			case LEFT:
				break;
			
			case CENTER:
				start.sub(w / 2D, 0);
				break;
			
			case RIGHT:
			default:
				start.sub(w, 0);
				break;
		}
		
		draw(text, start, height, color);
	}
	
}
