package mightypork.utils.math.rect;


import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectVal;
import mightypork.utils.math.vect.VectView;


/**
 * Common methods for all kinds of Rects
 * 
 * @author MightyPork
 */
public interface Rect extends RectConstraint {
	
	RectVal ONE = new RectVal(0, 0, 1, 1);
	RectVal ZERO = new RectVal(0, 0, 0, 0);
	
	
	/**
	 * Get a writable copy
	 * 
	 * @return writable copy
	 */
	RectMutable mutable();
	
	
	/**
	 * Get a copy of current value
	 * 
	 * @return copy
	 */
	RectVal value();
	
	
	/**
	 * Get a proxying view
	 * 
	 * @return proxy
	 */
	RectProxy view();
	
	
	/**
	 * @return origin
	 */
	VectVal getOrigin();
	
	
	VectVal getSize();
	
	
	double getWidth();
	
	
	double getHeight();
	
	
	VectVal getTopLeft();
	
	
	VectVal getTopCenter();
	
	
	VectVal getTopRight();
	
	
	VectVal getCenterLeft();
	
	
	VectVal getCenter();
	
	
	VectVal getCenterRight();
	
	
	VectVal getBottomLeft();
	
	
	VectVal getBottomCenter();
	
	
	VectVal getBottomRight();
	
	
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
	boolean contains(Vect point);
	
}
