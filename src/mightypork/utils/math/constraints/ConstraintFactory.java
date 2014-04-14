package mightypork.utils.math.constraints;


import mightypork.gamecore.control.timing.Poller;
import mightypork.utils.math.num.Num;
import mightypork.utils.math.num.Num;
import mightypork.utils.math.rect.Rect;
import mightypork.utils.math.rect.RectVal;
import mightypork.utils.math.rect.RectView;
import mightypork.utils.math.rect.RectView;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectAdapter;
import mightypork.utils.math.vect.VectVal;
import mightypork.utils.math.vect.Vect;


/**
 * Constraint factory.<br>
 * Import statically for best experience.
 * 
 * @author MightyPork
 */
public class ConstraintFactory {
	
	public static RectCache cached(final Poller poller, final RectBound rc)
	{
		return new RectCache(poller, rc);
	}
	
	
	/**
	 * Convert {@link Number} to {@link NumBound} if needed
	 * 
	 * @param o unknown numeric value
	 * @return converted
	 */
	private static NumBound toNumberBound(final Object o)
	{
		if (o instanceof NumBound) return (NumBound) o;
		
		if (o instanceof Number) return new Num() {
			
			@Override
			public double value()
			{
				return ((Number) o).doubleValue();
			}
		};
		
		throw new IllegalArgumentException("Invalid numeric type.");
	}
	
	
	/**
	 * Convert {@link Number} or {@link NumBound} to double (current value)
	 * 
	 * @param o unknown numeric value
	 * @return double value
	 */
	private static double eval(final Object o)
	{
		return o == null ? 0 : toNumberBound(o).getNum().value();
	}
	
	
	/**
	 * Convert as {@link VectBound} to a {@link Vect}
	 * 
	 * @param vectBound vect bound
	 * @return contained vect
	 */
	private static Vect eval(final VectBound vectBound)
	{
		return vectBound == null ? Vect.ZERO : vectBound.getVect();
	}
	
	
	/**
	 * Convert a {@link RectBound} to a {@link Rect}
	 * 
	 * @param rectBound rect bound
	 * @return contained rect
	 */
	private static RectView eval(final RectBound rectBound)
	{
		return rectBound == null ? Rect.ZERO : rectBound.getRect();
	}
	
	
	public static Num min(final Object a, final Object b)
	{
		return new Num() {
			
			@Override
			public double value()
			{
				return Math.min(eval(a), eval(b));
			}
		};
	}
	
	
	public static Num max(final Object a, final Object b)
	{
		return new Num() {
			
			@Override
			public double value()
			{
				return Math.max(eval(a), eval(b));
			}
		};
	}
	
	
	public static Num abs(final NumBound a)
	{
		return new Num() {
			
			@Override
			public double value()
			{
				return Math.abs(a.value());
			}
		};
	}
	
	
	public static Num half(final NumBound a)
	{
		return new Num() {
			
			@Override
			public double value()
			{
				return a.value() / 2;
			}
		};
	}
	
	
	public static Num round(final NumBound a)
	{
		return new Num() {
			
			@Override
			public double value()
			{
				return Math.round(a.value());
			}
		};
	}
	
	
	public static RectBound round(final RectBound r)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return eval(r).round();
			}
		};
	}
	
	
	public static Num ceil(final NumBound a)
	{
		return new Num() {
			
			@Override
			public double value()
			{
				return Math.ceil(a.value());
			}
		};
	}
	
	
	public static Num floor(final NumBound a)
	{
		return new Num() {
			
			@Override
			public double value()
			{
				return Math.floor(a.value());
			}
		};
	}
	
	
	public static Num neg(final NumBound a)
	{
		return new Num() {
			
			@Override
			public double value()
			{
				return -a.value();
			}
		};
	}
	
	
	public static Num add(final Object a, final Object b)
	{
		return new Num() {
			
			@Override
			public double value()
			{
				return eval(a) + eval(b);
			}
		};
	}
	
	
	public static Num sub(final Object a, final Object b)
	{
		return new Num() {
			
			@Override
			public double value()
			{
				return eval(a) - eval(b);
			}
		};
	}
	
	
	public static Num mul(final Object a, final Object b)
	{
		return new Num() {
			
			@Override
			public double value()
			{
				return eval(a) * eval(b);
			}
		};
	}
	
	
	public static Num half(final Object a)
	{
		return mul(a, 0.5);
	}
	
	
	public static Num div(final Object a, final Object b)
	{
		return new Num() {
			
			@Override
			public double value()
			{
				return eval(a) / eval(b);
			}
		};
	}
	
	
	public static Num perc(final Object whole, final Object percent)
	{
		return new Num() {
			
			@Override
			public double value()
			{
				return eval(whole) * (eval(percent) / 100);
			}
		};
	}
	
	
	public static RectBound row(final RectBound r, final int rows, final int index)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				RectView rv = eval(r);
				
				final double height = rv.height();
				final double perRow = height / rows;
				
				final VectVal origin = rv.origin().add(0, perRow * index);
				final VectVal size = rv.size().setY(perRow);
				
				return RectVal.make(origin, size);
			}
		};
	}
	
	
	public static RectBound column(final RectBound r, final int columns, final int index)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				RectView rv = eval(r);
				
				final double width = rv.width();
				final double perCol = width / columns;
				
				final VectVal origin = rv.origin().add(perCol * index, 0);
				final VectVal size = rv.size().setX(perCol);
				
				return RectVal.make(origin, size);
			}
		};
	}
	
	
	public static RectBound tile(final RectBound r, final int rows, final int cols, final int left, final int top)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				RectView rv = eval(r);
				
				final double height = rv.height();
				final double width = rv.height();
				final double perRow = height / rows;
				final double perCol = width / cols;
				
				final VectVal origin = rv.origin().add(perCol * left, perRow * (rows - top - 1));
				
				return RectVal.make(origin, perCol, perRow);
			}
		};
	}
	
	
	public static RectBound shrink(RectBound r, Object shrink)
	{
		return shrink(r, shrink, shrink, shrink, shrink);
	}
	
	
	public static RectBound shrink(RectBound context, Object horiz, Object vert)
	{
		return shrink(context, horiz, vert, horiz, vert);
	}
	
	
	public static RectBound shrink(final RectBound r, final Object left, final Object top, final Object right, final Object bottom)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return eval(r).shrink(eval(left), eval(top), eval(right), eval(bottom));
			}
		};
	}
	
	
	public static RectBound shrinkTop(final RectBound r, final Object shrink)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return eval(r).shrink(0, eval(shrink), 0, 0);
			}
		};
	}
	
	
	public static RectBound shrinkBottom(final RectBound r, final Object shrink)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return eval(r).shrink(0, 0, 0, eval(shrink));
			}
		};
	}
	
	
	public static RectBound shrinkLeft(final RectBound r, final Object shrink)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return eval(r).shrink(eval(shrink), 0, 0, 0);
			}
		};
	}
	
	
	public static RectBound shrinkRight(final RectBound r, final Object shrink)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return eval(r).shrink(0, 0, eval(shrink), 0);
			}
		};
	}
	
	
	public static RectBound grow(RectBound r, Object grow)
	{
		return grow(r, grow, grow, grow, grow);
	}
	
	
	public static RectBound grow(RectBound r, Object horiz, Object vert)
	{
		return grow(r, horiz, vert, horiz, vert);
	}
	
	
	public static RectBound grow(final RectBound r, final Object left, final Object right, final Object top, final Object bottom)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return eval(r).grow(eval(left), eval(right), eval(top), eval(bottom));
			}
		};
	}
	
	
	public static RectBound growUp(final RectBound r, final Object grow)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return eval(r).grow(0, eval(grow), 0, 0);
			}
		};
	}
	
	
	public static RectBound growDown(final RectBound r, final Object grow)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return eval(r).grow(0, 0, 0, eval(grow));
			}
		};
	}
	
	
	public static RectBound growLeft(final RectBound r, final Object grow)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return eval(r).grow(eval(grow), 0, 0, 0);
			}
		};
	}
	
	
	public static RectBound growRight(final RectBound r, final Object grow)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return eval(r).grow(0, 0, eval(grow), 0);
			}
		};
	}
	
	
	public static RectBound box(final Object side)
	{
		return box(side, side);
	}
	
	
	public static RectBound box(final VectBound origin, final Object width, final Object height)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return RectVal.make(eval(origin), eval(width), eval(height));
			}
		};
	}
	
	
	public static RectBound box(final Object width, final Object height)
	{
		return box(Vect.ZERO, width, height);
	}
	
	
	public static RectBound box(final RectBound r, final Object width, final Object height)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				final RectView origin = eval(r);
				
				return RectVal.make(origin.x(), origin.y(), eval(width), eval(height));
			}
		};
	}
	
	
	public static RectBound box(final RectBound r, final Object x, final Object y, final Object width, final Object height)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				final VectVal origin = eval(r).origin();
				
				return RectVal.make(origin.x() + eval(x), origin.y() + eval(y), eval(width), eval(height));
			}
		};
	}
	
	
	public static RectBound centerTo(final RectBound r, final RectBound centerTo)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				final VectVal size = eval(r).size();
				final VectVal center = eval(centerTo).center();
				
				return RectVal.make(center.sub(size.half()), size);
			}
		};
	}
	
	
	public static RectBound centerTo(final RectBound r, final VectBound centerTo)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				final VectVal size = eval(r).size();
				
				return RectVal.make(eval(centerTo).sub(size.half()), size);
			}
		};
	}
	
	
	public static RectBound centerTo(final RectBound r, final Object x, final Object y)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				final VectVal size = eval(r).size();
				final VectVal v = VectVal.make(eval(x), eval(y));
				
				return RectVal.make(v.sub(size.half()), size);
			}
		};
	}
	
	
	public static RectBound move(final RectBound r, final VectBound move)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return eval(r).move(eval(move));
			}
		};
	}
	
	
	public static RectBound move(final RectBound r, final Object x, final Object y)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return eval(r).move(eval(x), eval(y));
			}
		};
	}
	
	
	/**
	 * Make a rect around coord
	 * 
	 * @param c coord
	 * @param allSides size to grow on all sides
	 * @return rect constraint
	 */
	public static RectBound expand(final VectBound c, final Object allSides)
	{
		return expand(c, allSides, allSides, allSides, allSides);
	}
	
	
	/**
	 * Make a rect around coord
	 * 
	 * @param c coord
	 * @param horizontal horisontal grow (left, right)
	 * @param vertical vertical grow (top, bottom)
	 * @return rect constraint
	 */
	public static RectBound expand(final VectBound c, final Object horizontal, final Object vertical)
	{
		return expand(c, horizontal, vertical, horizontal, vertical);
	}
	
	
	/**
	 * Make a rect around coord, growing by given amounts
	 * 
	 * @param c coord
	 * @param top
	 * @param right
	 * @param bottom
	 * @param left
	 * @return rect constraint
	 */
	public static RectBound expand(final VectBound c, final Object top, final Object right, final Object bottom, final Object left)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				final double t = eval(top);
				final double r = eval(right);
				final double b = eval(bottom);
				final double l = eval(left);
				
				final Vect v = eval(c);
				
				return RectVal.make(v.x() - l, v.y() - t, l + r, t + b);
			}
		};
	}
	
	
	public static RectBound edgeLeft(final RectBound r)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				RectView v = eval(r);
				
				return v.shrink(NumBound.ZERO, NumBound.ZERO, v.width(), NumBound.ZERO);
			}
		};
	}
	
	
	public static RectBound edgeTop(final RectBound r)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				RectView rv = eval(r);
				
				return rv.shrink(NumBound.ZERO,NumBound.ZERO, NumBound.ZERO, rv.height());
			}
		};
	}
	
	
	public static RectBound edgeRight(final RectBound r)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				RectView rv = eval(r);
				return rv.shrink(rv.width(), NumBound.ZERO, NumBound.ZERO, NumBound.ZERO);
			}
		};
	}
	
	
	public static RectBound edgeBottom(final RectBound r)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				RectView rv = eval(r);
				return rv.shrink(NumBound.ZERO, rv.height(), NumBound.ZERO, NumBound.ZERO);
			}
		};
	}
	
	
	public static Vect neg(final VectBound c)
	{
		return mul(c, -1);
	}
	
	
	public static Vect half(final VectBound c)
	{
		return mul(c, 0.5);
	}
	
	
	public static Vect add(final VectBound c1, final VectBound c2)
	{
		return new VectAdapter() {
			
			@Override
			public Vect getSource()
			{
				return eval(c1).add(eval(c2));
			}
		};
	}
	
	
	public static Vect add(final VectBound c, final Object x, final Object y)
	{
		return add(c, x, y, 0);
	}
	
	
	public static Vect add(final VectBound c, final Object x, final Object y, final Object z)
	{
		return new VectAdapter() {
			
			@Override
			public Vect getSource()
			{
				return eval(c).add(eval(x), eval(y), eval(z));
			}
		};
	}
	
	
	public static Vect sub(final VectBound c1, final VectBound c2)
	{
		return new VectAdapter() {
			
			@Override
			public Vect getSource()
			{
				return eval(c1).sub(eval(c2));
			}
		};
	}
	
	
	public static Vect sub(final VectBound c, final Object x, final Object y)
	{
		return sub(c, x, y, 0);
	}
	
	
	public static Vect sub(final VectBound c, final Object x, final Object y, final Object z)
	{
		return new VectAdapter() {
			
			@Override
			public Vect getSource()
			{
				return eval(c).sub(eval(x), eval(y), eval(z));
			}
			
		};
	}
	
	
	public static Vect mul(final VectBound c, final Object mul)
	{
		return new VectAdapter() {
			
			@Override
			public Vect getSource()
			{
				return eval(c).mul(eval(mul));
			}
			
		};
	}
	
	
	public static Vect norm(final VectBound c, final Object norm)
	{
		return new VectAdapter() {
			
			@Override
			public Vect getSource()
			{
				return eval(c).norm(eval(norm));
			}
			
		};
	}
	
	
	public static Vect origin(final RectBound r)
	{
		return new VectAdapter() {
			
			@Override
			public Vect getSource()
			{
				return eval(r).origin();
			}
		};
	}
	
	
	public static Vect size(final RectBound r)
	{
		return new VectAdapter() {
			
			@Override
			public Vect getSource()
			{
				return eval(r).size();
			}
		};
	}
	
	
	public static Num height(final RectBound r)
	{
		return size(r).yC();
	}
	
	
	public static Num width(final RectBound r)
	{
		return size(r).xC();
	}
	
	
	public static Vect center(final RectBound r)
	{
		return add(origin(r), half(size(r)));
	}
	
	
	public static Vect topLeft(final RectBound r)
	{
		return origin(r);
	}
	
	
	public static Vect topRight(final RectBound r)
	{
		return add(origin(r), width(r), 0);
	}
	
	
	public static Vect bottomLeft(final RectBound r)
	{
		return add(origin(r), 0, width(r));
	}
	
	
	public static Vect bottomRight(final RectBound r)
	{
		return add(origin(r), size(r));
	}
	
	
	public static Vect topCenter(final RectBound r)
	{
		return add(origin(r), half(width(r)), 0);
	}
	
	
	public static Vect bottomCenter(final RectBound r)
	{
		return add(origin(r), half(width(r)), width(r));
	}
	
	
	public static Vect centerLeft(final RectBound r)
	{
		return add(origin(r), 0, half(width(r)));
	}
	
	
	public static Vect centerRight(final RectBound r)
	{
		return add(origin(r), width(r), half(width(r)));
	}
	
	
	/**
	 * Zero-sized RectView at given coord
	 * 
	 * @param c coord
	 * @return rect
	 */
	public static RectBound zeroRect(final VectBound c)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				Vect cv = eval(c);
				
				return new RectView() {
					
					@Override
					public Vect size()
					{
						return Vect.ZERO;
					}
					
					
					@Override
					public Vect origin()
					{
						return null;
					}
				};RectVal.make.make(cv.x(), cv.y(), 0, 0);
			}
		};
	}
	
}
