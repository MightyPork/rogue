package mightypork.rogue.fonts;


import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;


public interface GLFont {
	
	/**
	 * Draw string at position
	 * 
	 * @param text string to draw
	 * @param color draw color
	 */
	abstract void draw(String text, RGB color);
	
	
	/**
	 * Draw string at position
	 * 
	 * @param text string to draw
	 * @param color draw color
	 * @param startIndex first drawn character index
	 * @param endIndex last drawn character index
	 */
	abstract void draw(String text, RGB color, int startIndex, int endIndex);
	
	
	/**
	 * Draw string at position
	 * 
	 * @param str string to draw
	 */
	abstract void draw(String str);
	
	
	/**
	 * Get suize needed to render give string
	 * 
	 * @param text string to check
	 * @return coord (width, height)
	 */
	abstract Coord getNeededSpace(String text);
	
	
	/**
	 * @return font height
	 */
	abstract int getHeight();
	
	
	/**
	 * @param text texted text
	 * @return space needed
	 */
	abstract int getWidth(String text);
	
}
