package mightypork.utils.math.coord;


import mightypork.utils.math.Calc;


/**
 * Rectangle determined by two coordinates - min and max.
 * 
 * @author MightyPork
 */
public class Rect {
	
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
	 * @param x min X
	 * @param y min Y
	 * @param size rect size
	 * @return the rect
	 */
	public static Rect fromSize(int x, int y, Coord size)
	{
		return fromSize(x, y, size.x, size.y);
	}
	
	
	/**
	 * Make rect from min coord and size
	 * 
	 * @param xMin min x
	 * @param yMin min y
	 * @param width size x
	 * @param height size y
	 * @return the new rect
	 */
	public static Rect fromSize(double xMin, double yMin, double width, double height)
	{
		return new Rect(xMin, yMin, xMin + width, yMin + height);
	}
	
	/** Lowest coordinates xy */
	protected Coord min = new Coord();
	
	/** Highest coordinates xy */
	protected Coord max = new Coord();
	
	
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
	 * @param x1 lower x
	 * @param y1 lower y
	 * @param x2 upper x
	 * @param y2 upper y
	 */
	public Rect(double x1, double y1, double x2, double y2) {
		setTo(x1, y1, x2, y2);
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
	 * Get copy with the same center and height=0
	 * 
	 * @return line
	 */
	public Rect getAxisH()
	{
		return new Rect(getCenterLeft(), getCenterRight());
	}
	
	
	/**
	 * Get copy with the same center and width=0
	 * 
	 * @return line
	 */
	public Rect getAxisV()
	{
		return new Rect(getCenterBottom(), getCenterTop());
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
	public Coord getCenterBottom()
	{
		return new Coord((max.x + min.x) / 2, min.y);
	}
	
	
	/**
	 * Get center of the left edge.
	 * 
	 * @return center
	 */
	public Coord getCenterLeft()
	{
		return new Coord(min.x, (max.y + min.y) / 2);
	}
	
	
	/**
	 * Get center of the right edge.
	 * 
	 * @return center
	 */
	public Coord getCenterRight()
	{
		return new Coord(max.x, (max.y + min.y) / 2);
	}
	
	
	/**
	 * Get center of the top edge.
	 * 
	 * @return center
	 */
	public Coord getCenterTop()
	{
		return new Coord((max.x + min.x) / 2, max.y);
	}
	
	
	/**
	 * Get bottom edge rect
	 * 
	 * @return line
	 */
	public Rect getEdgeBottom()
	{
		return new Rect(getLeftBottom(), getRightBottom());
	}
	
	
	/**
	 * Get left edge rect
	 * 
	 * @return line
	 */
	public Rect getEdgeLeft()
	{
		return new Rect(getLeftBottom(), getLeftTop());
	}
	
	
	/**
	 * Get right edge rect
	 * 
	 * @return line
	 */
	public Rect getEdgeRight()
	{
		return new Rect(getRightBottom(), getRightTop());
	}
	
	
	/**
	 * Get top edge rect
	 * 
	 * @return line
	 */
	public Rect getEdgeTop()
	{
		return new Rect(getLeftTop(), getRightTop());
	}
	
	
	/**
	 * Get left bottom
	 * 
	 * @return center
	 */
	public Coord getLeftBottom()
	{
		return new Coord(min.x, min.y);
	}
	
	
	/**
	 * Get left top
	 * 
	 * @return center
	 */
	public Coord getLeftTop()
	{
		return new Coord(min.x, max.y);
	}
	
	
	/**
	 * @return highest coordinates xy
	 */
	public Coord getMax()
	{
		return getRightTop();
	}
	
	
	/**
	 * @return lowest coordinates xy
	 */
	public Coord getOrigin()
	{
		return getLeftBottom();
	}
	
	
	/**
	 * Get right bottom
	 * 
	 * @return center
	 */
	public Coord getRightBottom()
	{
		return new Coord(max.x, min.y);
	}
	
	
	/**
	 * Get right top
	 * 
	 * @return center
	 */
	public Coord getRightTop()
	{
		return new Coord(max.x, max.y);
	}
	
	
	/**
	 * Get size (width, height) as (x,y)
	 * 
	 * @return coord of width,height
	 */
	public Coord getSize()
	{
		return new Coord(Math.abs(min.x - max.x), Math.abs(min.y - max.y));
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
	 * @param x x to add
	 * @param y y to add
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
	 * @param left left shrink
	 * @param top top shrink
	 * @param right right shrink
	 * @param bottom bottom shrink
	 * @return changed copy
	 */
	public Rect shrink(double left, double top, double right, double bottom)
	{
		return copy().shrink_ip(left, top, right, bottom);
	}
	
	
	/**
	 * Shrink the rect in place
	 * 
	 * @param left left shrink
	 * @param top top shrink
	 * @param right right shrink
	 * @param bottom bottom shrink
	 * @return this
	 */
	public Rect shrink_ip(double left, double top, double right, double bottom)
	{
		min.add_ip(left, bottom);
		max.add_ip(-right, -top);
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
	 * @param x x to add
	 * @param y y to add
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
	 * @param x x to add
	 * @param y y to add
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
	 * @param left left growth
	 * @param top top growth
	 * @param right right growth
	 * @param bottom bottom growth
	 * @return changed copy
	 */
	public Rect grow(double left, double top, double right, double bottom)
	{
		return copy().grow_ip(left, top, right, bottom);
	}
	
	
	/**
	 * Grow the rect in place
	 * 
	 * @param left left growth
	 * @param top top growth
	 * @param right right growth
	 * @param bottom bottom growth
	 * @return this
	 */
	public Rect grow_ip(double left, double top, double right, double bottom)
	{
		min.add_ip(-left, -bottom);
		max.add_ip(right, top);
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
	 * @param x1 lower x
	 * @param y1 lower y
	 * @param x2 upper x
	 * @param y2 upper y
	 */
	public void setTo(double x1, double y1, double x2, double y2)
	{
		min.x = Calc.min(x1, x2);
		min.y = Calc.min(y1, y2);
		max.x = Calc.max(x1, x2);
		max.y = Calc.max(y1, y2);
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
}
