package mightypork.rogue.display.constraints;


import mightypork.utils.math.coord.Rect;


/**
 * Constraints can be based on this
 * 
 * @author MightyPork
 */
public interface ConstraintContext {

	/**
	 * @return bounding rectangle
	 */
	public Rect getRect();
}
