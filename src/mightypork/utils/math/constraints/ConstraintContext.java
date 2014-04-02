package mightypork.utils.math.constraints;


import mightypork.utils.math.coord.Rect;


/**
 * Context for constraints, with a bounding {@link Rect}.
 * 
 * @author MightyPork
 */
public interface ConstraintContext {
	
	/**
	 * Get context boundary
	 * 
	 * @return bounding rectangle
	 */
	public Rect getRect();
}
