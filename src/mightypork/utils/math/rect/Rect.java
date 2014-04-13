package mightypork.utils.math.rect;


import mightypork.utils.math.constraints.RectBound;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectVal;


/**
 * Common methods for all kinds of Rects
 * 
 * @author MightyPork
 */
public interface Rect extends RectBound {
	
	RectVal ONE = new RectVal(0, 0, 1, 1);
	RectVal ZERO = new RectVal(0, 0, 0, 0);
	
	
	/**
	 * Get a copy of current value
	 * 
	 * @return copy
	 */
	RectVal copy();
	
	
	/**
	 * Get a proxying view
	 * 
	 * @return proxy
	 */
	RectView view();
	
	
	/**
	 * @return origin (top left)
	 */
	VectVal origin();
	
	
	/**
	 * @return size vector
	 */
	VectVal size();
	
	
	/**
	 * @return current width
	 */
	double width();
	
	
	/**
	 * @return current height
	 */
	double height();
	
	
	/**
	 * @return origin X
	 */
	double x();
	
	
	/**
	 * @return origin Y
	 */
	double y();
	
	
	/**
	 * @return left X (low)
	 */
	double left();
	
	
	/**
	 * @return right X (high)
	 */
	double right();
	
	
	/**
	 * @return top Y (low)
	 */
	double top();
	
	
	/**
	 * @return bottom Y (high)
	 */
	double bottom();
	
	
	/**
	 * @return top left corner position
	 */
	VectVal topLeft();
	
	
	/**
	 * @return top center position
	 */
	VectVal topCenter();
	
	
	/**
	 * @return top right corner position
	 */
	VectVal topRight();
	
	
	/**
	 * @return left center position
	 */
	VectVal centerLeft();
	
	
	/**
	 * @return center position
	 */
	VectVal center();
	
	
	/**
	 * @return right center position
	 */
	VectVal centerRight();
	
	
	/**
	 * @return bottom left corner position
	 */
	VectVal bottomLeft();
	
	
	/**
	 * @return bottom center position
	 */
	VectVal bottomCenter();
	
	
	/**
	 * @return bottom right corner position
	 */
	VectVal bottomRight();
	
	
	/**
	 * Check if point is inside this rectangle
	 * 
	 * @param point point to test
	 * @return is inside
	 */
	boolean contains(Vect point);
	
}
