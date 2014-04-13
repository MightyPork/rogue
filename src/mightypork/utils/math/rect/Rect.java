package mightypork.utils.math.rect;


import mightypork.utils.math.constraints.RectBound;
import mightypork.utils.math.num.Num;
import mightypork.utils.math.vect.Vect;


/**
 * Common methods for all kinds of Rects
 * 
 * @author MightyPork
 */
public interface Rect extends RectBound {
	
	Rect ZERO = new RectVal(0, 0, 0, 0);
	Rect ONE = new RectVal(0, 0, 1, 1);
	
	
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
	 * Origin (top left).
	 * 
	 * @return origin (top left)
	 */
	Vect origin();
	
	
	/**
	 * Size (spanning right down from Origin).
	 * 
	 * @return size vector
	 */
	Vect size();
	
	
	/**
	 * @return current width
	 */
	public abstract Num width();
	
	
	/**
	 * @return current height
	 */
	public abstract Num height();
	
	
	/**
	 * @return origin X
	 */
	public abstract Num x();
	
	
	/**
	 * @return origin Y
	 */
	public abstract Num y();
	
	
	/**
	 * @return left X (low)
	 */
	public abstract Num left();
	
	
	/**
	 * @return right X (high)
	 */
	public abstract Num right();
	
	
	/**
	 * @return top Y (low)
	 */
	public abstract Num top();
	
	
	/**
	 * @return bottom Y (high)
	 */
	public abstract Num bottom();
	
	
	/**
	 * @return top left corner position
	 */
	public abstract Vect topLeft();
	
	
	/**
	 * @return top center position
	 */
	public abstract Vect topCenter();
	
	
	/**
	 * @return top right corner position
	 */
	public abstract Vect topRight();
	
	
	/**
	 * @return left center position
	 */
	public abstract Vect centerLeft();
	
	
	/**
	 * @return center position
	 */
	public abstract Vect center();
	
	
	/**
	 * @return right center position
	 */
	public abstract Vect centerRight();
	
	
	/**
	 * @return bottom left corner position
	 */
	public abstract Vect bottomLeft();
	
	
	/**
	 * @return bottom center position
	 */
	public abstract Vect bottomCenter();
	
	
	/**
	 * @return bottom right corner position
	 */
	public abstract Vect bottomRight();
}
