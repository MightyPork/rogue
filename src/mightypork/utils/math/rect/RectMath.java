package mightypork.utils.math.rect;


import mightypork.utils.math.vect.Vect;


abstract class RectMath<R extends Rect> extends AbstractRect {
	
	/**
	 * Add vector to origin
	 * 
	 * @param move offset vector
	 * @return result
	 */
	public abstract R move(Vect move);
	
	
	/**
	 * Add X and Y to origin
	 * 
	 * @param x x to add
	 * @param y y to add
	 * @return result
	 */
	public abstract R move(double x, double y);
	
	
	/**
	 * Shrink to sides
	 * 
	 * @param shrink shrink size (horisontal and vertical)
	 * @return result
	 */
	
	public R shrink(Vect shrink)
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
	public R shrink(double x, double y)
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
	public abstract R shrink(double left, double right, double top, double bottom);
	
	
	/**
	 * Grow to sides
	 * 
	 * @param grow grow size (added to each side)
	 * @return grown copy
	 */
	public final R grow(Vect grow)
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
	public final R grow(double x, double y)
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
	public abstract R grow(double left, double right, double top, double bottom);
	
	
	/**
	 * Round coords
	 * 
	 * @return result
	 */
	public abstract R round();
	
	
	/**
	 * Center to given point
	 * 
	 * @param point new center
	 * @return centered
	 */
	public abstract R centerTo(final Vect point);
	
	
	/**
	 * Check if point is inside this rectangle
	 * 
	 * @param point point to test
	 * @return is inside
	 */
	public boolean contains(Vect point)
	{
		final double x = point.x();
		final double y = point.y();
		
		final double x1 = origin().x();
		final double y1 = origin().y();
		final double x2 = x1 + size().x();
		final double y2 = y1 + size().y();
		
		return x >= x1 && y >= y1 && x <= x2 && y <= y2;
	}
	
}
