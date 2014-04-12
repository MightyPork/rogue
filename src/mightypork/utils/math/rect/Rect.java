package mightypork.utils.math.rect;


import mightypork.utils.math.coord.VecView;


/**
 * Common methods for all kinds of Rects
 * 
 * @author MightyPork
 */
public interface Rect {
	
	RectView ONE = new FixedRect(0, 0, 1, 1);
	RectView ZERO = new FixedRect(0, 0, 0, 0);
	
	
	/**
	 * Get a writable copy
	 * 
	 * @return copy
	 */
	RectMutable copy();
	
	
	/**
	 * Get a readonly copy
	 * 
	 * @return copy
	 */
	RectView view();
	
	
	/**
	 * @return origin
	 */
	VecView getOrigin();
	
	
	/**
	 * @return center
	 */
	VecView getCenter();
	
	
	/**
	 * @return rect size
	 */
	VecView getSize();
	
	
	/**
	 * @return rect width
	 */
	double getWidth();
	
	
	/**
	 * @return rect height
	 */
	double getHeight();
	
	
	double xMin();
	
	
	double xMax();
	
	
	double yMin();
	
	
	double yMax();
	
}
