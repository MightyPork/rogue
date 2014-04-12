package mightypork.utils.math.rect;


import mightypork.utils.math.coord.Vec;


abstract class RectMath<T extends Rect> extends AbstractRect {
	
	/**
	 * Add vector to origin
	 * 
	 * @param move offset vector
	 * @return result
	 */
	public T move(Vec move)
	{
		return move(move.x(), move.y());
	}
	
	
	/**
	 * Add X and Y to origin
	 * 
	 * @param x x to add
	 * @param y y to add
	 * @return result
	 */
	public abstract T move(double x, double y);
	
	
	/**
	 * Shrink to sides
	 * 
	 * @param shrink shrink size (horisontal and vertical)
	 * @return result
	 */
	
	public T shrink(Vec shrink)
	{
		return shrink(shrink.x(), shrink.y());
	}
	
	
	/**
	 * Shrink to sides at sides
	 * 
	 * @param x horizontal shrink
	 * @param y vertical shrink
	 * @return result
	 */
	public T shrink(double x, double y)
	{
		return shrink(x, x, y, y);
	}
	
	
	/**
	 * Shrink the rect
	 * 
	 * @param left shrink
	 * @param right shrink
	 * @param top shrink
	 * @param bottom shrink
	 * @return result
	 */
	public abstract T shrink(double left, double right, double top, double bottom);
	
	
	/**
	 * Grow to sides
	 * 
	 * @param grow grow size (added to each side)
	 * @return grown copy
	 */
	public final T grow(Vec grow)
	{
		return grow(grow.x(), grow.y());
	}
	
	
	/**
	 * Grow to sides
	 * 
	 * @param x horizontal grow
	 * @param y vertical grow
	 * @return result
	 */
	public final T grow(double x, double y)
	{
		return grow(x, x, y, y);
	}
	
	
	/**
	 * Grow the rect
	 * 
	 * @param left growth
	 * @param right growth
	 * @param top growth
	 * @param bottom growth
	 * @return result
	 */
	public abstract T grow(double left, double right, double top, double bottom);
	
	
	/**
	 * Round coords
	 * 
	 * @return result
	 */
	public abstract T round();
}
