package mightypork.rogue.display.constraints;


import mightypork.utils.math.coord.Rect;


/**
 * Bounding box provider - context for {@link Constraint}
 * 
 * @author MightyPork
 */
public interface Bounding {

	/**
	 * @return bounding rectangle
	 */
	public Rect getRect();
}
