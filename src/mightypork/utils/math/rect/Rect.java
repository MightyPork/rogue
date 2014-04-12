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
	RectVal getValue();
	
	
	/**
	 * Get a proxying view
	 * 
	 * @return proxy
	 */
	RectProxy getView();
	
	
	/**
	 * @return origin
	 */
	VectVal origin();
	
	
	VectVal size();
	
	
	double width();
	
	
	double height();
	
	
	VectVal topLeft();
	
	
	VectVal topCenter();
	
	
	VectVal topRight();
	
	
	VectVal centerLeft();
	
	
	VectVal center();
	
	
	VectVal centerRight();
	
	
	VectVal bottomLeft();
	
	
	VectVal bottomCenter();
	
	
	VectVal bottomRight();
	
	
	double getLeft();
	
	
	double right();
	
	
	double top();
	
	
	double bottom();
	
	
	/**
	 * Check if point is inside this rectangle
	 * 
	 * @param point point to test
	 * @return is inside
	 */
	boolean contains(Vect point);
	
}
