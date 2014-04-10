package mightypork.gamecore.render.fonts;


import mightypork.gamecore.render.textures.FilterMode;
import mightypork.utils.math.color.RGB;
import mightypork.utils.math.coord.Coord;


/**
 * Interface bor drawable font.
 * 
 * @author MightyPork
 */
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

	/**
	 * Set used filtering
	 * 
	 * @param filter font filtering mode
	 */
	void setFiltering(FilterMode filter);
	
	
	/**
	 * Get used filter mode
	 * 
	 * @return filter mode
	 */
	FilterMode getFiltering();
	
}
