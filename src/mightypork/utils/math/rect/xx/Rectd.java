package mightypork.utils.math.rect.xx;


//package mightypork.utils.math.rect;
//
//
//import mightypork.utils.math.Calc;
//import mightypork.utils.math.constraints.NumberConstraint;
//import mightypork.utils.math.constraints.RectConstraint;
//import mightypork.utils.math.coord.ConstrCoord;
//import mightypork.utils.math.coord.VecValue;
//import mightypork.utils.math.coord.VecArith;
//import mightypork.utils.math.coord.VecMutable;
//
//
///**
// * Rectangle determined by two coordinates - min and max.
// * 
// * @author MightyPork
// */
//public class Rectd implements RectConstraint, IRect {
//	
//	public static final IRect ZERO = new FixedRect(0, 0, 0, 0).freeze();
//	public static final Rect ONE = new FixedRect(0, 0, 1, 1).freeze();
//	
//	
//	/**
//	 * Rectangle from size; coords are copied.
//	 * 
//	 * @param min min coord
//	 * @param size rect size
//	 * @return the rect
//	 */
//	public static Rect fromSize(VecArith min, VecValue size)
//	{
//		return fromSize(min, size.xi(), size.yi());
//	}
//	
//	
//	/**
//	 * Make rect from min coord and size; coords are copied.
//	 * 
//	 * @param min min coord
//	 * @param width size x
//	 * @param height size y
//	 * @return the new rect
//	 */
//	public static Rect fromSize(VecArith min, double width, double height)
//	{
//		return new FixedRect(min.copy(), min.add(width, height));
//	}
//	
//	
//	/**
//	 * Rectangle from size
//	 * 
//	 * @param sizeX size x
//	 * @param sizeY size y
//	 * @return rect
//	 */
//	public static IRect fromSize(double sizeX, double sizeY)
//	{
//		return fromSize(0, 0, sizeX, sizeY);
//	}
//	
//	
//	/**
//	 * Get rect from size
//	 * 
//	 * @param size size
//	 * @return rect
//	 */
//	public static IRect fromSize(VecValue size)
//	{
//		return fromSize(0, 0, size.x(), size.y());
//	}
//	
//	
//	/**
//	 * Make rect from min coord and size
//	 * 
//	 * @param xmin min x
//	 * @param ymin min y
//	 * @param width size x
//	 * @param height size y
//	 * @return the new rect
//	 */
//	public static Rect fromSize(double xmin, double ymin, double width, double height)
//	{
//		return new FixedRect(xmin, ymin, xmin + width, ymin + height);
//	}
//	
//	/** Lowest coordinates xy */
//	protected final VecArith min;
//	
//	/** Highest coordinates xy */
//	protected final VecArith max;
//	
//	// view of secondary corners.
//	
//	//@formatter:off
//	private final ConstrCoord HMinVMax = new ConstrCoord(
//			new NumberConstraint() {
//				@Override
//				public double getValue()
//				{
//					return min.x();
//				}
//			},
//			new NumberConstraint() {		
//				@Override
//				public double getValue()
//				{
//					return max.y();
//				}
//			},
//			null
//	);
//	
//	
//	private final ConstrCoord HMaxVMin = new ConstrCoord(			
//			new NumberConstraint() {
//				@Override
//				public double getValue() {
//					return max.x();
//				}
//			},			
//			new NumberConstraint() {
//				@Override
//				public double getValue()
//				{
//					return min.y();
//				}
//			},
//			null
//	);
//	//@formatter:on
//	
//	private boolean frozen;
//	
//	
//	public boolean isWritable()
//	{
//		return !frozen && !isView();
//	}
//	
//	
//	public boolean isView()
//	{
//		return false;
//	}
//	
//	
//	public Rect freeze()
//	{
//		min.copy();
//		max.copy();
//		frozen = true;
//		return this;
//	}
//	
//	
//	/**
//	 * Get a readonly view
//	 * 
//	 * @return view
//	 */
//	public RectView view()
//	{
//		return new RectView(this);
//	}
//	
//	
//	protected void assertWritable()
//	{
//		if (!isWritable()) {
//			throw new UnsupportedOperationException("This Rect is not writable.");
//		}
//	}
//	
//	
//	/**
//	 * New Rect [0, 0, 0, 0]
//	 */
//	public Rect() {
//		this(0, 0, 0, 0);
//	}
//	
//	
//	/**
//	 * Rect [0, 0, size.x, size.y]
//	 * 
//	 * @param size size coord
//	 */
//	public Rect(VecValue size) {
//		this(0, 0, size.x(), size.y());
//	}
//	
//	
//	/**
//	 * New rect of two coords; Coords are plugged in directly (ie. views can be
//	 * used for frozen rect representing another)
//	 * 
//	 * @param min coord 1
//	 * @param max coord 2
//	 */
//	public Rect(VecValue min, VecValue max) {
//		this.min = min; // must not copy
//		this.max = max; // must not copy
//	}
//	
//	
//	/**
//	 * New Rect
//	 * 
//	 * @param xmin lower x
//	 * @param ymin lower y
//	 * @param xmax upper x
//	 * @param ymax upper y
//	 */
//	public Rect(double xmin, double ymin, double xmax, double ymax) {
//		min = new VecArith(xmin, ymin);
//		max = new VecArith(xmax, ymax);
//	}
//	
//	
//	/**
//	 * Rect [0, 0, x, y]
//	 * 
//	 * @param x width
//	 * @param y height
//	 */
//	public Rect(double x, double y) {
//		this(0, 0, x, y);
//	}
//	
//	
//	/**
//	 * New rect as a copy of other rect
//	 * 
//	 * @param r other rect
//	 */
//	public Rect(Rect r) {
//		this(r.min.copy(), r.max.copy());
//	}
//	
//	
//	/**
//	 * Get offset copy (add)
//	 * 
//	 * @param move offset vector
//	 * @return offset copy
//	 */
//	@Override
//	public Rect move(VecMutable move)
//	{
//		return copy().add_ip(move);
//	}
//	
//	
//	/**
//	 * Add X and Y to all coordinates in a copy
//	 * 
//	 * @param x x to add
//	 * @param y y to add
//	 * @return copy changed
//	 */
//	@Override
//	public Rect move(double x, double y)
//	{
//		return move(new VecArith(x, y));
//	}
//	
//	
//	/**
//	 * Offset in place (add)
//	 * 
//	 * @param move offset vector
//	 * @return this
//	 */
//	public Rect add_ip(VecMutable move)
//	{
//		min.add_ip(move);
//		max.add_ip(move);
//		return this;
//	}
//	
//	
//	/**
//	 * Add X and Y to all coordinates in place
//	 * 
//	 * @param x x to add
//	 * @param y y to add
//	 * @return this
//	 */
//	public IRect add_ip(double x, double y)
//	{
//		return add_ip(new VecArith(x, y));
//	}
//	
//	
//	/**
//	 * Get a copy
//	 * 
//	 * @return copy
//	 */
//	@Override
//	public Rect copy()
//	{
//		return new FixedRect(this);
//	}
//	
//	
//	/**
//	 * Get rect center
//	 * 
//	 * @return center
//	 */
//	@Override
//	public VecValue getCenter()
//	{
//		return min.midTo(max).copy();
//	}
//	
//	
//	/**
//	 * Get center of the lower edge.
//	 * 
//	 * @return center
//	 */
//	@Override
//	public VecValue getCenterVMin()
//	{
//		return new VecArith((max.x() + min.x()) / 2D, min.y()).copy();
//	}
//	
//	
//	/**
//	 * Get center of the left edge.
//	 * 
//	 * @return center
//	 */
//	@Override
//	public VecValue getCenterHMin()
//	{
//		return new VecArith(min.x(), (max.y() + min.y()) / 2D).copy();
//	}
//	
//	
//	/**
//	 * Get center of the right edge.
//	 * 
//	 * @return center
//	 */
//	@Override
//	public VecValue getCenterHMax()
//	{
//		return new VecArith(max.x(), (max.y() + min.y()) / 2D).copy();
//	}
//	
//	
//	/**
//	 * Get center of the top edge.
//	 * 
//	 * @return center
//	 */
//	@Override
//	public VecValue getCenterVMax()
//	{
//		return new VecArith((max.x() + min.x()) / 2D, max.y()).copy();
//	}
//	
//	
//	/**
//	 * Get left bottom
//	 * 
//	 * @return center
//	 */
//	@Override
//	public VecArith getHMinVMin()
//	{
//		return min.view();
//	}
//	
//	
//	/**
//	 * Get left top
//	 * 
//	 * @return center
//	 */
//	@Override
//	public VecValue getHMinVMax()
//	{
//		return HMinVMax;
//	}
//	
//	
//	/**
//	 * Get right bottom
//	 * 
//	 * @return center
//	 */
//	@Override
//	public VecValue getHMaxVMin()
//	{
//		return HMaxVMin;
//	}
//	
//	
//	/**
//	 * Get right top
//	 * 
//	 * @return center
//	 */
//	@Override
//	public VecArith getHMaxVMax()
//	{
//		return max.view();
//	}
//	
//	
//	/**
//	 * Alias for getX2Y2
//	 * 
//	 * @return highest coordinates xy
//	 */
//	@Override
//	public VecMutable getMax()
//	{
//		return getHMaxVMax();
//	}
//	
//	
//	/**
//	 * Alias for getX1Y1
//	 * 
//	 * @return lowest coordinates xy
//	 */
//	@Override
//	public VecMutable getMin()
//	{
//		return getHMinVMin();
//	}
//	
//	
//	/**
//	 * Alias for getX1Y1
//	 * 
//	 * @return lowest coordinates xy
//	 */
//	public VecValue getOrigin()
//	{
//		return getHMinVMin();
//	}
//	
//	
//	/**
//	 * Shrink to sides in copy
//	 * 
//	 * @param shrink shrink size (added to each side)
//	 * @return shrinkn copy
//	 */
//	@Override
//	public IRect shrink(VecValue shrink)
//	{
//		return copy().shrink_ip(shrink);
//	}
//	
//	
//	/**
//	 * Shrink to sides in copy
//	 * 
//	 * @param x x to add
//	 * @param y y to add
//	 * @return changed copy
//	 */
//	@Override
//	public IRect shrink(double x, double y)
//	{
//		return copy().shrink_ip(x, y);
//	}
//	
//	
//	/**
//	 * Shrink to sides in place
//	 * 
//	 * @param shrink shrink size (added to each side)
//	 * @return this
//	 */
//	public IRect shrink_ip(VecValue shrink)
//	{
//		shrink_ip(shrink.x(), shrink.y());
//		return this;
//	}
//	
//	
//	/**
//	 * Shrink to sides in place
//	 * 
//	 * @param x horizontal shrink
//	 * @param y vertical shrink
//	 * @return this
//	 */
//	public IRect shrink_ip(double x, double y)
//	{
//		min.sub_ip(x, y);
//		max.add_ip(-x, -y);
//		return this;
//	}
//	
//	
//	/**
//	 * Shrink the rect
//	 * 
//	 * @param xmin shrink
//	 * @param ymin shrink
//	 * @param xmax shrink
//	 * @param ymax shrink
//	 * @return changed copy
//	 */
//	@Override
//	public Rect shrink(double xmin, double ymin, double xmax, double ymax)
//	{
//		return copy().shrink_ip(xmin, ymin, xmax, ymax);
//	}
//	
//	
//	/**
//	 * Shrink the rect in place
//	 * 
//	 * @param xmin shrink
//	 * @param ymin shrink
//	 * @param xmax shrink
//	 * @param ymax shrink
//	 * @return this
//	 */
//	public Rect shrink_ip(double xmin, double ymin, double xmax, double ymax)
//	{
//		min.add_ip(xmin, ymin);
//		max.add_ip(-xmax, -ymax);
//		return this;
//	}
//	
//	
//	/**
//	 * Grow to sides in copy
//	 * 
//	 * @param grow grow size (added to each side)
//	 * @return grown copy
//	 */
//	@Override
//	public IRect grow(VecValue grow)
//	{
//		return copy().grow_ip(grow);
//	}
//	
//	
//	/**
//	 * Grow to sides in copy
//	 * 
//	 * @param x horizontal grow
//	 * @param y vertical grow
//	 * @return changed copy
//	 */
//	@Override
//	public IRect grow(double x, double y)
//	{
//		return copy().grow_ip(x, y);
//	}
//	
//	
//	/**
//	 * Grow to sides in place
//	 * 
//	 * @param grow grow size (added to each side)
//	 * @return this
//	 */
//	public IRect grow_ip(VecValue grow)
//	{
//		grow_ip(grow.x(), grow.y());
//		return this;
//	}
//	
//	
//	/**
//	 * Grow to sides in place
//	 * 
//	 * @param x horizontal grow
//	 * @param y vertical grow
//	 * @return this
//	 */
//	public IRect grow_ip(double x, double y)
//	{
//		min.sub_ip(x, y);
//		max.add_ip(x, y);
//		return this;
//	}
//	
//	
//	/**
//	 * Grow the rect
//	 * 
//	 * @param xmin growth
//	 * @param ymin growth
//	 * @param xmax growth
//	 * @param ymax growth
//	 * @return changed copy
//	 */
//	@Override
//	public Rect grow(double xmin, double ymin, double xmax, double ymax)
//	{
//		return copy().grow_ip(xmin, ymin, xmax, ymax);
//	}
//	
//	
//	/**
//	 * Grow the rect in place
//	 * 
//	 * @param xmin growth
//	 * @param ymin growth
//	 * @param xmax growth
//	 * @param ymax growth
//	 * @return this
//	 */
//	public Rect grow_ip(double xmin, double ymin, double xmax, double ymax)
//	{
//		min.add_ip(-xmin, -ymin);
//		max.add_ip(xmax, ymax);
//		return this;
//	}
//	
//	
//	/**
//	 * Check if point is inside this rectangle
//	 * 
//	 * @param point point to test
//	 * @return is inside
//	 */
//	@Override
//	public boolean isInside(VecValue point)
//	{
//		return Calc.inRange(point.x(), min.x(), max.x()) && Calc.inRange(point.y(), min.y(), max.y());
//	}
//	
//	
//	/**
//	 * Multiply in copy
//	 * 
//	 * @param factor multiplier
//	 * @return offset copy
//	 */
//	@Override
//	public IRect mul(double factor)
//	{
//		return copy().mul_ip(factor);
//	}
//	
//	
//	/**
//	 * Multiply by number (useful for centered rects)
//	 * 
//	 * @param x x multiplier
//	 * @param y y multiplier
//	 * @return copy multiplied
//	 */
//	@Override
//	public IRect mul(double x, double y)
//	{
//		return copy().mul_ip(x, y);
//	}
//	
//	
//	/**
//	 * Multiply coord in place
//	 * 
//	 * @param factor multiplier
//	 * @return this
//	 */
//	public IRect mul_ip(double factor)
//	{
//		min.mul_ip(factor);
//		max.mul_ip(factor);
//		return this;
//	}
//	
//	
//	/**
//	 * Multiply coord in place
//	 * 
//	 * @param x multiplier x
//	 * @param y multiplier y
//	 * @return this
//	 */
//	public IRect mul_ip(double x, double y)
//	{
//		min.mul_ip(x, y, 1);
//		max.mul_ip(x, y, 1);
//		return this;
//	}
//	
//	
//	/**
//	 * Round coords in copy
//	 * 
//	 * @return copy, rounded
//	 */
//	@Override
//	public Rect round()
//	{
//		return new FixedRect(min.round(), max.round());
//	}
//	
//	
//	/**
//	 * Round this in place
//	 * 
//	 * @return this
//	 */
//	public IRect round_ip()
//	{
//		min.round_ip();
//		max.round_ip();
//		return this;
//	}
//	
//	
//	/**
//	 * Set to coordinates
//	 * 
//	 * @param xmin lower x
//	 * @param ymin lower y
//	 * @param xmax upper x
//	 * @param ymax upper y
//	 */
//	@Override
//	public void setTo(double xmin, double ymin, double xmax, double ymax)
//	{
//		min.setTo(Math.min(xmin, xmax), Math.min(ymin, ymax));
//		max.setTo(Math.max(xmin, xmax), Math.max(ymin, ymax));
//	}
//	
//	
//	/**
//	 * Set to other rect's coordinates
//	 * 
//	 * @param r other rect
//	 */
//	@Override
//	public void setTo(Rect r)
//	{
//		min.setTo(r.min);
//		max.setTo(r.max);
//	}
//	
//	
//	/**
//	 * Subtract X and Y from all coordinates in a copy
//	 * 
//	 * @param x x to subtract
//	 * @param y y to subtract
//	 * @return copy changed
//	 */
//	@Override
//	public IRect sub(double x, double y)
//	{
//		return sub(new VecArith(x, y));
//	}
//	
//	
//	/**
//	 * Get offset copy (subtract)
//	 * 
//	 * @param move offset vector
//	 * @return offset copy
//	 */
//	@Override
//	public IRect sub(VecMutable move)
//	{
//		return copy().sub_ip(move);
//	}
//	
//	
//	/**
//	 * Subtract X and Y from all coordinates in place
//	 * 
//	 * @param x x to subtract
//	 * @param y y to subtract
//	 * @return this
//	 */
//	public IRect sub_ip(double x, double y)
//	{
//		return sub_ip(new VecArith(x, y));
//	}
//	
//	
//	/**
//	 * Offset in place (subtract)
//	 * 
//	 * @param move offset vector
//	 * @return this
//	 */
//	public IRect sub_ip(VecMutable move)
//	{
//		min.sub_ip(move);
//		max.sub_ip(move);
//		return this;
//	}
//	
//	
//	@Override
//	public String toString()
//	{
//		return String.format("[%s-%s]", min.toString(), max.toString());
//	}
//	
//	
//	/**
//	 * @return lower x
//	 */
//	@Override
//	public double xMin()
//	{
//		return min.x();
//	}
//	
//	
//	/**
//	 * @return upper x
//	 */
//	@Override
//	public double xMax()
//	{
//		return max.x();
//	}
//	
//	
//	/**
//	 * @return lower y
//	 */
//	@Override
//	public double yMin()
//	{
//		return min.y();
//	}
//	
//	
//	/**
//	 * @return upper y
//	 */
//	@Override
//	public double yMax()
//	{
//		return max.y();
//	}
//	
//	
//	@Override
//	public double getHeight()
//	{
//		return max.y() - min.y();
//	}
//	
//	
//	@Override
//	public double getWidth()
//	{
//		return max.x() - min.x();
//	}
//	
//	
//	/**
//	 * Get size (width, height) as (x,y)
//	 * 
//	 * @return coord of width,height
//	 */
//	public VecValue getSize()
//	{
//		return new VecArith(max.x() - min.x(), max.y() - min.y());
//	}
//	
//	
//	@Override
//	public RectView getRect()
//	{
//		return this;
//	}
//	
//	
//	/**
//	 * Generate zero rect
//	 * 
//	 * @return zero
//	 */
//	public static IRect zero()
//	{
//		return ZERO.copy();
//	}
//	
//	
//	/**
//	 * Generate 0,0-1,1 rect
//	 * 
//	 * @return one
//	 */
//	public static IRect one()
//	{
//		return ZERO.copy();
//	}
//}
