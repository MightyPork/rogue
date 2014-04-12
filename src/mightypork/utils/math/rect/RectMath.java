package mightypork.utils.math.rect;


import mightypork.utils.math.coord.Vec;


/**
 * Operations available in rects
 * 
 * @author MightyPork
 */
interface RectMath<T extends Rect> extends Rect {
	
	/**
	 * Add vector to origin
	 * 
	 * @param move offset vector
	 * @return result
	 */
	T move(Vec move);
	
	
	/**
	 * Add X and Y to origin
	 * 
	 * @param x x to add
	 * @param y y to add
	 * @return result
	 */
	T move(double x, double y);
	
	
	/**
	 * Shrink to sides
	 * 
	 * @param shrink shrink size (horisontal and vertical)
	 * @return result
	 */
	T shrink(Vec shrink);
	
	
	/**
	 * Shrink to sides at sides
	 * 
	 * @param x horizontal shrink
	 * @param y vertical shrink
	 * @return result
	 */
	T shrink(double x, double y);
	
	
	/**
	 * Shrink the rect
	 * 
	 * @param left shrink
	 * @param right shrink
	 * @param top shrink
	 * @param bottom shrink
	 * @return result
	 */
	T shrink(double left, double right, double top, double bottom);
	
	
	/**
	 * Grow to sides
	 * 
	 * @param grow grow size (added to each side)
	 * @return grown copy
	 */
	T grow(Vec grow);
	
	
	/**
	 * Grow to sides
	 * 
	 * @param x horizontal grow
	 * @param y vertical grow
	 * @return result
	 */
	T grow(double x, double y);
	
	
	/**
	 * Grow the rect
	 * 
	 * @param left growth
	 * @param right growth
	 * @param top growth
	 * @param bottom growth
	 * @return result
	 */
	T grow(double left, double right, double top, double bottom);
	
	
	/**
	 * Check if point is inside this rectangle
	 * 
	 * @param point point to test
	 * @return is inside
	 */
	boolean contains(Vec point);
	
	
	/**
	 * Round coords
	 * 
	 * @return result
	 */
	T round();
	
}
