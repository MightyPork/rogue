package mightypork.utils.math.rect;


import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.coord.Vec;
import mightypork.utils.math.coord.VecView;


/**
 * Common methods for all kinds of Rects
 * 
 * @author MightyPork
 */
public interface Rect extends RectConstraint {
	
	RectValue ONE = new ConstRect(0, 0, 1, 1);
	RectValue ZERO = new ConstRect(0, 0, 0, 0);
	
	
	/**
	 * Get a writable copy
	 * 
	 * @return copy
	 */
	RectMutable mutable();
	
	
	/**
	 * Get a copy of current value
	 * 
	 * @return copy
	 */
	RectValue value();
	
	
	/**
	 * Get a proxying view
	 * 
	 * @return copy
	 */
	RectValue view();
	
	
	/**
	 * @return origin
	 */
	VecView getOrigin();
	
	
	VecView getSize();
	
	
	double getWidth();
	
	
	double getHeight();
	
	
	VecView getTopLeft();
	
	
	VecView getTopCenter();
	
	
	VecView getTopRight();
	
	
	VecView getCenterLeft();
	
	
	VecView getCenter();
	
	
	VecView getCenterRight();
	
	
	VecView getBottomLeft();
	
	
	VecView getBottomCenter();
	
	
	VecView getBottomRight();
	
	
	double xMin();
	
	
	double xMax();
	
	
	double yMin();
	
	
	double yMax();
	
	
	/**
	 * Check if point is inside this rectangle
	 * 
	 * @param point point to test
	 * @return is inside
	 */
	boolean contains(Vec point);
	
}
