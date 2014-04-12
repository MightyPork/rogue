package mightypork.gamecore.render.fonts;


import mightypork.utils.math.color.RGB;
import mightypork.utils.math.vect.VectView;


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
	void draw(String text, RGB color);
	
	
	/**
	 * Get suize needed to render give string
	 * 
	 * @param text string to check
	 * @return coord (width, height)
	 */
	VectView getNeededSpace(String text);
	
	
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
