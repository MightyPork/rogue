package mightypork.utils.math.rect;


import mightypork.utils.math.constraints.RectConstraint;
import mightypork.utils.math.constraints.VecConstraint;
import mightypork.utils.math.coord.MutableCoord;
import mightypork.utils.math.coord.SynthCoord2D;
import mightypork.utils.math.coord.Vec;
import mightypork.utils.math.coord.VecMutable;
import mightypork.utils.math.coord.VecView;


public class Rect {
	
	public static final Rect ONE = new Rect(0, 0, 1, 1); // FIXME
	private final VecMutable pos = new MutableCoord();
	private final VecMutable size = new MutableCoord();
	private final VecView center = new SynthCoord2D() {
		
		@Override
		public double y()
		{
			return pos.x() + size.x() / 2;
		}
		
		
		@Override
		public double x()
		{
			return pos.y() + size.y() / 2;
		}
	};
	
	
	/**
	 * Create at 0,0 with zero size
	 */
	public Rect() {
		// keep default zeros
	}
	
	
	/**
	 * Create at 0,0 with given size
	 * 
	 * @param width
	 * @param height
	 */
	public Rect(double width, double height) {
		this.pos.setTo(0, 0);
		this.size.setTo(width, height);
		norm();
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param origin
	 * @param width
	 * @param height
	 */
	public Rect(Vec origin, double width, double height) {
		this.pos.setTo(origin);
		this.size.setTo(width, height);
		norm();
	}
	
	
	/**
	 * make sure the rect doesn't have negative size.
	 */
	private void norm()
	{
		if (size.x() < 0) {
			pos.sub(-size.x(), 0);
			size.mul(-1, 1);
		}
		
		if (size.y() < 0) {
			pos.sub(0, -size.y());
			size.mul(1, -1);
		}
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param origin
	 * @param size
	 */
	public Rect(Vec origin, Vec size) {
		this.pos.setTo(origin);
		this.size.setTo(size);
		norm();
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Rect(double x, double y, double width, double height) {
		pos.setTo(x, y);
		size.setTo(width, height);
		norm();
	}
	
	
	/**
	 * Create as copy of another
	 * 
	 * @param other copied
	 */
	public Rect(Rect other) {
		this(other.pos, other.size);
	}
	
	
	/**
	 * Set to other rect's coordinates
	 * 
	 * @param rect other rect
	 */
	public void setTo(Rect rect)
	{
		setTo(rect.pos, rect.size);
	}
	
	
	public void setTo(Vec origin, Vec size)
	{
		this.pos.setTo(origin);
		this.size.setTo(size);
		norm();
	}
	
	
	public void setOrigin(Vec origin)
	{
		this.pos.setTo(origin);
		norm();
	}
	
	
	public void setSize(Vec size)
	{
		this.size.setTo(size);
		norm();
	}
	
	
	/**
	 * Add vector to origin
	 * 
	 * @param move offset vector
	 * @return result
	 */
	public Rect move(Vec move)
	{
		move(move.x(), move.y());
		return this;
	}
	
	
	/**
	 * Add X and Y to origin
	 * 
	 * @param x x to add
	 * @param y y to add
	 * @return result
	 */
	public Rect move(double x, double y)
	{
		pos.add(x, y);
		return this;
	}
	
	
	/**
	 * Get a copy
	 * 
	 * @return copy
	 */
	public Rect copy()
	{
		return new Rect(this);
	}
	
	
	/**
	 * Shrink to sides
	 * 
	 * @param shrink shrink size (horisontal and vertical)
	 * @return result
	 */
	public Rect shrink(Vec shrink)
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
	public Rect shrink(double x, double y)
	{
		return shrink(x, y, x, y);
	}
	
	
	/**
	 * Shrink the rect
	 * 
	 * @param left shrink
	 * @param top shrink
	 * @param right shrink
	 * @param bottom shrink
	 * @return result
	 */
	public Rect shrink(double left, double top, double right, double bottom)
	{
		pos.add(left, top);
		size.sub(left + right, top + bottom);
		norm();
		return this;
	}
	
	
	/**
	 * Grow to sides
	 * 
	 * @param grow grow size (added to each side)
	 * @return grown copy
	 */
	public Rect grow(Vec grow)
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
	public Rect grow(double x, double y)
	{
		return grow(x, y, x, y);
	}
	
	
	/**
	 * Grow the rect
	 * 
	 * @param left growth
	 * @param top growth
	 * @param right growth
	 * @param bottom growth
	 * @return result
	 */
	public Rect grow(double left, double top, double right, double bottom)
	{
		pos.sub(left, top);
		size.add(left + right, top + bottom);
		norm();
		return this;
	}
	
	
	/**
	 * Check if point is inside this rectangle
	 * 
	 * @param point point to test
	 * @return is inside
	 */
	public boolean contains(Vec point)
	{
		final double x = point.x(), y = point.y();
		
		final double x1 = pos.x(), y1 = pos.y();
		final double x2 = x1 + size.x(), y2 = y1 + size.y();
		
		return x >= x1 && y >= y1 && x <= x2 && y <= y2;
	}
	
	
	/**
	 * Round coords
	 * 
	 * @return result
	 */
	public Rect round()
	{
		pos.round();
		size.round();
		return this;
	}
	
	
	/**
	 * Get offset copy (subtract)
	 * 
	 * @param move offset vector
	 * @return result
	 */
	public Rect sub(Vec move)
	{
		return sub(move.x(), move.y());
	}
	
	
	/**
	 * Subtract X and Y from all coordinates
	 * 
	 * @param x x to subtract
	 * @param y y to subtract
	 * @return result
	 */
	Rect sub(double x, double y)
	{
		pos.sub(x, y);
		norm();
		return this;
	}
	
	
	public VecView getOrigin()
	{
		return pos.view();
	}
	
	
	public VecView getSize()
	{
		return size.view();
	}
	
	
	public VecView getCenter()
	{
		return center;
	}
	
	
	public double getWidth()
	{
		return size.x();
	}
	
	
	public double getHeight()
	{
		return size.y();
	}
	
	
	@Override
	public String toString()
	{
		return String.format("[%s-%s]", pos.toString(), pos.view().add(size).toString());
	}
	
	
	public double xMin()
	{
		return pos.x();
	}
	
	
	public double xMax()
	{
		return pos.x() + size.x();
	}
	
	
	public double yMin()
	{
		return pos.y();
	}
	
	
	public double yMax()
	{
		return pos.y() + size.y();
	}
}
