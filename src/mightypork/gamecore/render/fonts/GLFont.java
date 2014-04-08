package mightypork.gamecore.render.fonts;


import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;


public interface GLFont {
	
	/**
	 * Draw string at position
	 * 
	 * @param text string to draw
	 * @param color draw color
	 */
	void draw(String text, RGB color);
	
	
	/**
	 * Get suize needed to render give string
	 * 
	 * @param text string to check
	 * @return coord (width, height)
	 */
	Coord getNeededSpace(String text);
	
	
	/**
	 * @return font height
	 */
	int getGlyphHeight();
	
	
	/**
	 * @param text texted text
	 * @return space needed
	 */
	int getWidth(String text);


	/**
	 * @return specified font size
	 */
	int getSize();
	
}
