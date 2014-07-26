package mightypork.gamecore.graphics.fonts;


import mightypork.utils.math.color.Color;
import mightypork.utils.math.constraints.vect.Vect;


/**
 * Interface bor drawable font.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public interface IFont {
	
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
	
	
	/**
	 * Set what vertical ratio of the font size is blank and should be cut off
	 * when rendering
	 * 
	 * @param top top ratio (0-1)
	 * @param bottom bottom ratio (0-1)
	 */
	void setDiscardRatio(double top, double bottom);
	
	
	/**
	 * Get top discard ratio (blank unused space)
	 * 
	 * @return ratio
	 */
	double getTopDiscardRatio();
	
	
	/**
	 * Get bottom discard ratio (blank unused space)
	 * 
	 * @return ratio
	 */
	double getBottomDiscardRatio();
}
