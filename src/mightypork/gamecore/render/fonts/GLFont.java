package mightypork.gamecore.render.fonts;


import mightypork.util.constraints.vect.Vect;
import mightypork.util.math.color.Color;


/**
 * Interface bor drawable font.
 * 
 * @author MightyPork
 */
public interface GLFont {
	
	/**
	 * Draw without scaling at (0, 0) in given color.
	 * 
	 * @param text text to draw
	 * @param color draw color
	 */
	void draw(String text, Color color);
	
	
	/**
	 * Get suize needed to render give string
	 * 
	 * @param text string to check
	 * @return coord (width, height)
	 */
	Vect getNeededSpace(String text);
	
	
	/**
	 * @return font height
	 */
	int getLineHeight();
	
	
	/**
	 * @param text texted text
	 * @return space needed
	 */
	int getWidth(String text);
	
	
	/**
	 * @return specified font size
	 */
	int getFontSize();
}
