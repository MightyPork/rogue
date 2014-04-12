package mightypork.utils.math.rect;


import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.coord.VecView;


/**
 * Common methods for all kinds of Rects
 * 
 * @author MightyPork
 */
public interface Rect extends RectConstraint {
	
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
	
}
