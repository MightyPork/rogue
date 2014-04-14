package mightypork.utils.math.rect;


import mightypork.utils.annotations.FactoryMethod;
import mightypork.utils.math.constraints.RectBound;
import mightypork.utils.math.num.Num;
import mightypork.utils.math.num.NumConst;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectConst;


/**
 * Common methods for all kinds of Rects
 * 
 * @author MightyPork
 */
public abstract class Rect implements RectBound {
	
	public static final RectConst ZERO = new RectConst(0, 0, 0, 0);
	public static final RectConst ONE = new RectConst(0, 0, 1, 1);
	
	
	@FactoryMethod
	public static Rect make(Num width, Num height)
	{
		final Vect origin = Vect.ZERO;
		final Vect size = Vect.make(width, height);
		
		return Rect.make(origin, size);
	}
	
	
	@FactoryMethod
	public static Rect make(Num x, Num y, Num width, Num height)
	{
		final Vect origin = Vect.make(x, y);
		final Vect size = Vect.make(width, height);
		
		return Rect.make(origin, size);
	}
	
	
	@FactoryMethod
	public static Rect make(final Vect origin, final Vect size)
	{
		return new RectVectAdapter(origin, size);
	}
	
	
	@FactoryMethod
	public static RectConst make(NumConst width, NumConst height)
	{
		final VectConst origin = Vect.ZERO;
		final VectConst size = Vect.make(width, height);
		
		return Rect.make(origin, size);
	}
	
	
	@FactoryMethod
	public static RectConst make(NumConst x, NumConst y, NumConst width, NumConst height)
	{
		final VectConst origin = Vect.make(x, y);
		final VectConst size = Vect.make(width, height);
		
		return Rect.make(origin, size);
	}
	
	
	@FactoryMethod
	public static RectConst make(final VectConst origin, final VectConst size)
	{
		return new RectConst(origin, size);
	}
	
	
	@FactoryMethod
	public static RectConst make(double width, double height)
	{
		return Rect.make(0, 0, width, height);
	}
	
	
	@FactoryMethod
	public static RectConst make(double x, double y, double width, double height)
	{
		return new RectConst(x, y, width, height);
	}
	
	
	@FactoryMethod
	public static RectVar makeVar(double x, double y)
	{
		return Rect.makeVar(0, 0, x, y);
	}
	
	
	@FactoryMethod
	public static RectVar makeVar(Rect copied)
	{
		return Rect.makeVar(copied.origin(), copied.size());
	}
	
	
	@FactoryMethod
	public static RectVar makeVar(Vect origin, Vect size)
	{
		return Rect.makeVar(origin.x(), origin.y(), size.x(), size.y());
	}
	
	
	@FactoryMethod
	public static RectVar makeVar(double x, double y, double width, double height)
	{
		return new RectVar(x, y, width, height);
	}
	
	
	@FactoryMethod
	public static RectVar makeVar()
	{
		return Rect.makeVar(Rect.ZERO);
	}
	
	
	/**
	 * Get a copy of current value
	 * 
	 * @return copy
	 */
	public RectConst freeze()
	{
		// must NOT call RectVal.make, it'd cause infinite recursion.
		return new RectConst(this);
	}
	
	
	@Override
	public Rect getRect()
	{
		return this;
	}
	
	
	@Override
	public String toString()
	{
		return String.format("Rect { %s - %s }", origin(), origin().freeze().add(size()));
	}
	
	
	/**
	 * Origin (top left).
	 * 
	 * @return origin (top left)
	 */
	public abstract Vect origin();
	
	
	/**
	 * Size (spanning right down from Origin).
	 * 
	 * @return size vector
	 */
	public abstract Vect size();
	
	
	/**
	 * Add vector to origin
	 * 
	 * @param move offset vector
	 * @return result
	 */
	public Rect move(final Vect move)
	{
		return new Rect() {
			
			private final Rect t = Rect.this;
			
			
			@Override
			public Vect size()
			{
				return t.size();
			}
			
			
			@Override
			public Vect origin()
			{
				return t.origin().add(move);
			}
			
		};
	}
	
	
	/**
	 * Add X and Y to origin
	 * 
	 * @param x x to add
	 * @param y y to add
	 * @return result
	 */
	public Rect move(final double x, final double y)
	{
		return new Rect() {
			
			private final Rect t = Rect.this;
			
			
			@Override
			public Vect size()
			{
				return t.size();
			}
			
			
			@Override
			public Vect origin()
			{
				return t.origin().add(x, y);
			}
			
		};
	}
	
	
	public Rect move(final Num x, final Num y)
	{
		return new Rect() {
			
			private final Rect t = Rect.this;
			
			
			@Override
			public Vect size()
			{
				return t.size();
			}
			
			
			@Override
			public Vect origin()
			{
				return t.origin().add(x, y);
			}
			
		};
	}
	
	
	/**
	 * Shrink to sides
	 * 
	 * @param shrink shrink size (horisontal and vertical)
	 * @return result
	 */
	
	public Rect shrink(Vect shrink)
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
	public Rect shrink(final double left, final double right, final double top, final double bottom)
	{
		return new Rect() {
			
			private final Rect t = Rect.this;
			
			
			@Override
			public Vect size()
			{
				return t.size().sub(left + right, top + bottom);
			}
			
			
			@Override
			public Vect origin()
			{
				return t.origin().add(left, top);
			}
			
		};
	}
	
	
	/**
	 * Grow to sides
	 * 
	 * @param grow grow size (added to each side)
	 * @return grown copy
	 */
	public final Rect grow(Vect grow)
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
	public final Rect grow(double x, double y)
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
	public Rect grow(final double left, final double right, final double top, final double bottom)
	{
		return new Rect() {
			
			private final Rect t = Rect.this;
			
			
			@Override
			public Vect size()
			{
				return t.size().add(left + right, top + bottom);
			}
			
			
			@Override
			public Vect origin()
			{
				return t.origin().sub(left, top);
			}
			
		};
	}
	
	
	public Rect shrink(final Num left, final Num right, final Num top, final Num bottom)
	{
		return new Rect() {
			
			private final Rect t = Rect.this;
			
			
			@Override
			public Vect size()
			{
				return t.size().sub(left.add(right), top.add(bottom));
			}
			
			
			@Override
			public Vect origin()
			{
				return t.origin().add(left, top);
			}
			
		};
	}
	
	
	public Rect grow(final Num left, final Num right, final Num top, final Num bottom)
	{
		
		return new Rect() {
			
			private final Rect t = Rect.this;
			
			
			@Override
			public Vect size()
			{
				return t.size().add(left.add(right), top.add(bottom));
			}
			
			
			@Override
			public Vect origin()
			{
				return t.origin().sub(left, top);
			}
			
		};
	}
	
	
	/**
	 * Round coords
	 * 
	 * @return result
	 */
	public Rect round()
	{
		
		return new Rect() {
			
			private final Rect t = Rect.this;
			
			
			@Override
			public Vect size()
			{
				return t.size().round();
			}
			
			
			@Override
			public Vect origin()
			{
				return t.origin().round();
			}
			
		};
	}
	
	
	public Num x()
	{
		return origin().xn();
	}
	
	
	public Num y()
	{
		return origin().yn();
	}
	
	
	public Num width()
	{
		return size().xn();
	}
	
	
	public Num height()
	{
		return size().yn();
	}
	
	
	public Num left()
	{
		return origin().yn();
	}
	
	
	public Num right()
	{
		return origin().xn().add(size().xn());
	}
	
	
	public Num top()
	{
		return origin().yn();
	}
	
	
	public Num bottom()
	{
		return origin().yn().add(size().yn());
	}
	
	
	public Vect topLeft()
	{
		return origin();
	}
	
	
	public Vect topCenter()
	{
		return origin().add(size().xn().half(), Num.ZERO);
	}
	
	
	public Vect topRight()
	{
		return origin().add(size().xn(), Num.ZERO);
	}
	
	
	public Vect centerLeft()
	{
		return origin().add(Num.ZERO, size().yn().half());
	}
	
	
	public Vect center()
	{
		return origin().add(size().half());
	}
	
	
	public Vect centerRight()
	{
		return origin().add(size().xn(), size().yn().half());
	}
	
	
	public Vect bottomLeft()
	{
		return origin().add(Num.ZERO, size().yn());
	}
	
	
	public Vect bottomCenter()
	{
		return origin().add(size().xn().half(), size().yn());
	}
	
	
	public Vect bottomRight()
	{
		return origin().add(size().xn(), size().yn());
	}
	
	
	/**
	 * Center to given point
	 * 
	 * @param point new center
	 * @return centered
	 */
	public Rect centerTo(final Vect point)
	{
		return new Rect() {
			
			Rect t = Rect.this;
			
			
			@Override
			public Vect size()
			{
				return t.size();
			}
			
			
			@Override
			public Vect origin()
			{
				return point.sub(t.size().half());
			}
		};
	}
	
	
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
