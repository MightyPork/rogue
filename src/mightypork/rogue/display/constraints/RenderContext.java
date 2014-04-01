package mightypork.rogue.display.constraints;


import mightypork.utils.math.coord.Rect;


/**
 * Bounding box provider - context for {@link RectConstraint}
 * 
 * @author MightyPork
 */
public interface RenderContext {
	
	/**
	 * @return bounding rectangle
	 */
	public Rect getRect();
}
