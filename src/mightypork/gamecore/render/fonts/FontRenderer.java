package mightypork.gamecore.render.fonts;


import static mightypork.utils.math.constraints.Constraints.*;
import mightypork.gamecore.render.Render;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Vec;
import mightypork.utils.math.coord.VecView;
import mightypork.utils.math.rect.Rect;


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
	public Vec getNeededSpace(String text, double height)
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
		return height / font.getHeight();
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
	public void draw(String text, Vec pos, double height, RGB color)
	{
		Render.pushMatrix();
		
		Render.translate(pos.view().round());
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
		VecView start;
		
		switch (align) {
			case LEFT:
				start = _top_left(bounds);
				break;
			
			case CENTER:
				start = _center_top(bounds);
				break;
			
			case RIGHT:
			default:
				start = _top_right(bounds);
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
	public void draw(String text, Vec pos, double height, Align align)
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
	public void draw(String text, Vec pos, double height, Align align, RGB color)
	{
		
		final double w = getWidth(text, height);
		
		final Vec start;
		
		switch (align) {
			case LEFT:
				start = pos.view();
				break;
			
			case CENTER:
				start = pos.view().sub(w / 2D, 0);
				break;
			
			case RIGHT:
			default:
				start = pos.view().sub(w, 0);
				break;
		}
		
		draw(text, start, height, color);
	}
	
}
