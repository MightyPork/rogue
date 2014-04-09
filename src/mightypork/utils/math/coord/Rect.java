package mightypork.utils.math.coord;


import mightypork.gamecore.gui.constraints.RectConstraint;
import mightypork.utils.math.Calc;


/**
 * Rectangle determined by two coordinates - min and max.
 * 
 * @author MightyPork
 */
public class Rect implements RectConstraint {
	
	/**
	 * Rectangle from size
	 * 
	 * @param min min coord
	 * @param size rect size
	 * @return the rect
	 */
	public static Rect fromSize(Coord min, Coord size)
	{
		return fromSize(min, size.xi(), size.yi());
	}
	
	
	/**
	 * Make rect from min coord and size
	 * 
	 * @param min min coord
	 * @param width size x
	 * @param height size y
	 * @return the new rect
	 */
	public static Rect fromSize(Coord min, double width, double height)
	{
		return new Rect(min, min.add(width, height));
	}
	
	
	/**
	 * Rectangle from size
	 * 
	 * @param sizeX size x
	 * @param sizeY size y
	 * @return rect
	 */
	public static Rect fromSize(double sizeX, double sizeY)
	{
		return fromSize(0, 0, sizeX, sizeY);
	}
	
	
	/**
	 * Get rect from size
	 * 
	 * @param size size
	 * @return rect
	 */
	public static Rect fromSize(Coord size)
	{
		return fromSize(0, 0, size.x, size.y);
	}
	
	
	/**
	 * Make rect from min coord and size
	 * 
	 * @param xmin min x
	 * @param ymin min y
	 * @param width size x
	 * @param height size y
	 * @return the new rect
	 */
	public static Rect fromSize(double xmin, double ymin, double width, double height)
	{
		return new Rect(xmin, ymin, xmin + width, ymin + height);
	}
	
	/** Lowest coordinates xy */
	private final Coord min = new Coord();
	
	/** Highest coordinates xy */
	private final Coord max = new Coord();
	
	
	/**
	 * New Rect [0, 0, 0, 0]
	 */
	public Rect() {
		this(0, 0, 0, 0);
	}
	
	
	/**
	 * Rect [0, 0, size.x, size.y]
	 * 
	 * @param size size coord
	 */
	public Rect(Coord size) {
		this(0, 0, size.x, size.y);
	}
	
	
	/**
	 * New rect of two coords
	 * 
	 * @param c1 coord 1
	 * @param c2 coord 2
	 */
	public Rect(Coord c1, Coord c2) {
		this(c1.x, c1.y, c2.x, c2.y);
	}
	
	
	/**
	 * New Rect
	 * 
	 * @param xmin lower x
	 * @param ymin lower y
	 * @param xmax upper x
	 * @param ymax upper y
	 */
	public Rect(double xmin, double ymin, double xmax, double ymax) {
		setTo(xmin, ymin, xmax, ymax);
	}
	
	
	/**
	 * Rect [0, 0, x, y]
	 * 
	 * @param x width
	 * @param y height
	 */
	public Rect(double x, double y) {
		this(0, 0, x, y);
	}
	
	
	/**
	 * New rect as a copy of other rect
	 * 
	 * @param r other rect
	 */
	public Rect(Rect r) {
		this(r.min.x, r.min.y, r.max.x, r.max.y);
	}
	
	
	/**
	 * Get offset copy (add)
	 * 
	 * @param move offset vector
	 * @return offset copy
	 */
	public Rect add(Coord move)
	{
		return copy().add_ip(move);
	}
	
	
	/**
	 * Add X and Y to all coordinates in a copy
	 * 
	 * @param x x to add
	 * @param y y to add
	 * @return copy changed
	 */
	public Rect add(double x, double y)
	{
		return add(new Coord(x, y));
	}
	
	
	/**
	 * Offset in place (add)
	 * 
	 * @param move offset vector
	 * @return this
	 */
	public Rect add_ip(Coord move)
	{
		min.add_ip(move);
		max.add_ip(move);
		return this;
	}
	
	
	/**
	 * Add X and Y to all coordinates in place
	 * 
	 * @param x x to add
	 * @param y y to add
	 * @return this
	 */
	public Rect add_ip(double x, double y)
	{
		return add_ip(new Coord(x, y));
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
	 * Get rect center
	 * 
	 * @return center
	 */
	public Coord getCenter()
	{
		return min.midTo(max);
	}
	
	
	/**
	 * Get center of the lower edge.
	 * 
	 * @return center
	 */
	public Coord getCenterVMin()
	{
		return new Coord((max.x + min.x) / 2D, min.y);
	}
	
	
	/**
	 * Get center of the left edge.
	 * 
	 * @return center
	 */
	public Coord getCenterHMin()
	{
		return new Coord(min.x, (max.y + min.y) / 2D);
	}
	
	
	/**
	 * Get center of the right edge.
	 * 
	 * @return center
	 */
	public Coord getCenterHMax()
	{
		return new Coord(max.x, (max.y + min.y) / 2D);
	}
	
	
	/**
	 * Get center of the top edge.
	 * 
	 * @return center
	 */
	public Coord getCenterVMax()
	{
		return new Coord((max.x + min.x) / 2D, max.y);
	}
	
	
	/**
	 * Get left bottom
	 * 
	 * @return center
	 */
	public Coord getHMinVMin()
	{
		return new Coord(min.x, min.y);
	}
	
	
	/**
	 * Get left top
	 * 
	 * @return center
	 */
	public Coord getHMinVMax()
	{
		return new Coord(min.x, max.y);
	}
	
	
	/**
	 * Alias for getX2Y2
	 * 
	 * @return highest coordinates xy
	 */
	public Coord getMax()
	{
		return getHMaxVMax();
	}
	
	
	/**
	 * Alias for getX1Y1
	 * 
	 * @return lowest coordinates xy
	 */
	public Coord getMin()
	{
		return getHMinVMin();
	}
	
	
	/**
	 * Alias for getX1Y1
	 * 
	 * @return lowest coordinates xy
	 */
	public Coord getOrigin()
	{
		return getMin();
	}
	
	
	/**
	 * Get right bottom
	 * 
	 * @return center
	 */
	public Coord getHMaxVMin()
	{
		return new Coord(max.x, min.y);
	}
	
	
	/**
	 * Get right top
	 * 
	 * @return center
	 */
	public Coord getHMaxVMax()
	{
		return new Coord(max.x, max.y);
	}
	
	
	/**
	 * Shrink to sides in copy
	 * 
	 * @param shrink shrink size (added to each side)
	 * @return shrinkn copy
	 */
	public Rect shrink(Coord shrink)
	{
		return copy().shrink_ip(shrink);
	}
	
	
	/**
	 * Shrink to sides in copy
	 * 
	 * @param x x to add
	 * @param y y to add
	 * @return changed copy
	 */
	public Rect shrink(double x, double y)
	{
		return copy().shrink_ip(x, y);
	}
	
	
	/**
	 * Shrink to sides in place
	 * 
	 * @param shrink shrink size (added to each side)
	 * @return this
	 */
	public Rect shrink_ip(Coord shrink)
	{
		shrink_ip(shrink.x, shrink.y);
		return this;
	}
	
	
	/**
	 * Shrink to sides in place
	 * 
	 * @param x horizontal shrink
	 * @param y vertical shrink
	 * @return this
	 */
	public Rect shrink_ip(double x, double y)
	{
		min.sub_ip(x, y);
		max.add_ip(-x, -y);
		return this;
	}
	
	
	/**
	 * Shrink the rect
	 * 
	 * @param xmin shrink
	 * @param ymin shrink
	 * @param xmax shrink
	 * @param ymax shrink
	 * @return changed copy
	 */
	public Rect shrink(double xmin, double ymin, double xmax, double ymax)
	{
		return copy().shrink_ip(xmin, ymin, xmax, ymax);
	}
	
	
	/**
	 * Shrink the rect in place
	 * 
	 * @param xmin shrink
	 * @param ymin shrink
	 * @param xmax shrink
	 * @param ymax shrink
	 * @return this
	 */
	public Rect shrink_ip(double xmin, double ymin, double xmax, double ymax)
	{
		min.add_ip(xmin, ymin);
		max.add_ip(-xmax, -ymax);
		return this;
	}
	
	
	/**
	 * Grow to sides in copy
	 * 
	 * @param grow grow size (added to each side)
	 * @return grown copy
	 */
	public Rect grow(Coord grow)
	{
		return copy().grow_ip(grow);
	}
	
	
	/**
	 * Grow to sides in copy
	 * 
	 * @param x horizontal grow
	 * @param y vertical grow
	 * @return changed copy
	 */
	public Rect grow(double x, double y)
	{
		return copy().grow_ip(x, y);
	}
	
	
	/**
	 * Grow to sides in place
	 * 
	 * @param grow grow size (added to each side)
	 * @return this
	 */
	public Rect grow_ip(Coord grow)
	{
		grow_ip(grow.x, grow.y);
		return this;
	}
	
	
	/**
	 * Grow to sides in place
	 * 
	 * @param x horizontal grow
	 * @param y vertical grow
	 * @return this
	 */
	public Rect grow_ip(double x, double y)
	{
		min.sub_ip(x, y);
		max.add_ip(x, y);
		return this;
	}
	
	
	/**
	 * Grow the rect
	 * 
	 * @param xmin growth
	 * @param ymin growth
	 * @param xmax growth
	 * @param ymax growth
	 * @return changed copy
	 */
	public Rect grow(double xmin, double ymin, double xmax, double ymax)
	{
		return copy().grow_ip(xmin, ymin, xmax, ymax);
	}
	
	
	/**
	 * Grow the rect in place
	 * 
	 * @param xmin growth
	 * @param ymin growth
	 * @param xmax growth
	 * @param ymax growth
	 * @return this
	 */
	public Rect grow_ip(double xmin, double ymin, double xmax, double ymax)
	{
		min.add_ip(-xmin, -ymin);
		max.add_ip(xmax, ymax);
		return this;
	}
	
	
	/**
	 * Check if point is inside this rectangle
	 * 
	 * @param point point to test
	 * @return is inside
	 */
	public boolean isInside(Coord point)
	{
		return Calc.inRange(point.x, min.x, max.x) && Calc.inRange(point.y, min.y, max.y);
	}
	
	
	/**
	 * Multiply in copy
	 * 
	 * @param factor multiplier
	 * @return offset copy
	 */
	public Rect mul(double factor)
	{
		return copy().mul_ip(factor);
	}
	
	
	/**
	 * Multiply by number (useful for centered rects)
	 * 
	 * @param x x multiplier
	 * @param y y multiplier
	 * @return copy multiplied
	 */
	public Rect mul(double x, double y)
	{
		return copy().mul_ip(x, y);
	}
	
	
	/**
	 * Multiply coord in place
	 * 
	 * @param factor multiplier
	 * @return this
	 */
	public Rect mul_ip(double factor)
	{
		min.mul_ip(factor);
		max.mul_ip(factor);
		return this;
	}
	
	
	/**
	 * Multiply coord in place
	 * 
	 * @param x multiplier x
	 * @param y multiplier y
	 * @return this
	 */
	public Rect mul_ip(double x, double y)
	{
		min.mul_ip(x, y, 1);
		max.mul_ip(x, y, 1);
		return this;
	}
	
	
	/**
	 * Round coords in copy
	 * 
	 * @return copy, rounded
	 */
	public Rect round()
	{
		return new Rect(min.round(), max.round());
	}
	
	
	/**
	 * Round this in place
	 * 
	 * @return this
	 */
	public Rect round_ip()
	{
		min.round_ip();
		max.round_ip();
		return this;
	}
	
	
	/**
	 * Set to [0,0,coord.x,coord.y]
	 * 
	 * @param coord size coord
	 */
	public void setTo(Coord coord)
	{
		setTo(0, 0, coord.x, coord.y);
	}
	
	
	/**
	 * Set to coordinates
	 * 
	 * @param xmin lower x
	 * @param ymin lower y
	 * @param xmax upper x
	 * @param ymax upper y
	 */
	public void setTo(double xmin, double ymin, double xmax, double ymax)
	{
		min.x = Calc.min(xmin, xmax);
		min.y = Calc.min(ymin, ymax);
		max.x = Calc.max(xmin, xmax);
		max.y = Calc.max(ymin, ymax);
	}
	
	
	/**
	 * Set to other rect's coordinates
	 * 
	 * @param r other rect
	 */
	public void setTo(Rect r)
	{
		min.setTo(r.min);
		max.setTo(r.max);
	}
	
	
	/**
	 * Subtract X and Y from all coordinates in a copy
	 * 
	 * @param x x to subtract
	 * @param y y to subtract
	 * @return copy changed
	 */
	public Rect sub(double x, double y)
	{
		return sub(new Coord(x, y));
	}
	
	
	/**
	 * Get offset copy (subtract)
	 * 
	 * @param move offset vector
	 * @return offset copy
	 */
	public Rect sub(Coord move)
	{
		return copy().sub_ip(move);
	}
	
	
	/**
	 * Subtract X and Y from all coordinates in place
	 * 
	 * @param x x to subtract
	 * @param y y to subtract
	 * @return this
	 */
	public Rect sub_ip(double x, double y)
	{
		return sub_ip(new Coord(x, y));
	}
	
	
	/**
	 * Offset in place (subtract)
	 * 
	 * @param move offset vector
	 * @return this
	 */
	public Rect sub_ip(Coord move)
	{
		min.sub_ip(move);
		max.sub_ip(move);
		return this;
	}
	
	
	/**
	 * @return rect [0,0-1,1]
	 */
	public static Rect one()
	{
		return new Rect(0, 0, 1, 1);
	}
	
	
	/**
	 * @return rect [0,0-0,0]
	 */
	public static Rect zero()
	{
		return new Rect(0, 0, 0, 0);
	}
	
	
	@Override
	public String toString()
	{
		return String.format("[( %4.1f; %4.1f )-( %4.1f; %4.1f )]", min.x, min.y, max.x, max.y);
	}
	
	
	/**
	 * @return lower x
	 */
	public double xMin()
	{
		return min.x;
	}
	
	
	/**
	 * @return upper x
	 */
	public double xMax()
	{
		return max.x;
	}
	
	
	/**
	 * @return lower y
	 */
	public double yMin()
	{
		return min.y;
	}
	
	
	/**
	 * @return upper y
	 */
	public double yMax()
	{
		return max.y;
	}
	
	
	public double getHeight()
	{
		return max.y - min.y;
	}
	
	
	public double getWidth()
	{
		return max.x - min.x;
	}
	
	
	/**
	 * Get size (width, height) as (x,y)
	 * 
	 * @return coord of width,height
	 */
	public Coord getSize()
	{
		return new Coord(max.x - min.x, max.y - min.y);
	}
	
	
	@Override
	public Rect getRect()
	{
		return this;
	}
}
