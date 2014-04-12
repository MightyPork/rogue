package mightypork.utils.math.constraints;


import mightypork.gamecore.control.timing.Poller;
import mightypork.utils.math.rect.RectVal;
import mightypork.utils.math.rect.RectView;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectAdapter;
import mightypork.utils.math.vect.VectVal;
import mightypork.utils.math.vect.VectView;


/**
 * Constraint factory.<br>
 * Import statically for best experience.
 * 
 * @author MightyPork
 */
public class Bounds {
	
	public static RectCache cached(final Poller poller, final RectBound rc)
	{
		return new RectCache(poller, rc);
	}
	
	
	/**
	 * Convert {@link Number} to {@link NumberBound} if needed
	 * 
	 * @param o unknown numeric value
	 * @return converted
	 */
	private static NumberBound toNumberBound(final Object o)
	{
		if (o instanceof NumberBound) return (NumberBound) o;
		
		if (o instanceof Number) return new NumberBound() {
			
			@Override
			public double getValue()
			{
				return ((Number) o).doubleValue();
			}
		};
		
		throw new IllegalArgumentException("Invalid numeric type.");
	}
	
	
	/**
	 * Convert {@link Number} or {@link NumberBound} to double (current value)
	 * 
	 * @param o unknown numeric value
	 * @return double value
	 */
	private static double eval(final Object o)
	{
		return toNumberBound(o).getValue();
	}
	
	
	public static NumberBound min(final Object a, final Object b)
	{
		return new NumberBound() {
			
			@Override
			public double getValue()
			{
				return Math.min(eval(a), eval(b));
			}
		};
	}
	
	
	public static NumberBound max(final Object a, final Object b)
	{
		return new NumberBound() {
			
			@Override
			public double getValue()
			{
				return Math.max(eval(a), eval(b));
			}
		};
	}
	
	
	public static NumberBound abs(final NumberBound a)
	{
		return new NumberBound() {
			
			@Override
			public double getValue()
			{
				return Math.abs(a.getValue());
			}
		};
	}
	
	
	public static NumberBound half(final NumberBound a)
	{
		return new NumberBound() {
			
			@Override
			public double getValue()
			{
				return a.getValue() / 2;
			}
		};
	}
	
	
	public static NumberBound round(final NumberBound a)
	{
		return new NumberBound() {
			
			@Override
			public double getValue()
			{
				return Math.round(a.getValue());
			}
		};
	}
	
	
	public static RectBound round(final RectBound r)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return r.getRect().round();
			}
		};
	}
	
	
	public static NumberBound ceil(final NumberBound a)
	{
		return new NumberBound() {
			
			@Override
			public double getValue()
			{
				return Math.ceil(a.getValue());
			}
		};
	}
	
	
	public static NumberBound floor(final NumberBound a)
	{
		return new NumberBound() {
			
			@Override
			public double getValue()
			{
				return Math.floor(a.getValue());
			}
		};
	}
	
	
	public static NumberBound neg(final NumberBound a)
	{
		return new NumberBound() {
			
			@Override
			public double getValue()
			{
				return -a.getValue();
			}
		};
	}
	
	
	public static NumberBound add(final Object a, final Object b)
	{
		return new NumberBound() {
			
			@Override
			public double getValue()
			{
				return eval(a) + eval(b);
			}
		};
	}
	
	
	public static NumberBound sub(final Object a, final Object b)
	{
		return new NumberBound() {
			
			@Override
			public double getValue()
			{
				return eval(a) - eval(b);
			}
		};
	}
	
	
	public static NumberBound mul(final Object a, final Object b)
	{
		return new NumberBound() {
			
			@Override
			public double getValue()
			{
				return eval(a) * eval(b);
			}
		};
	}
	
	
	public static NumberBound half(final Object a)
	{
		return mul(a, 0.5);
	}
	
	
	public static NumberBound div(final Object a, final Object b)
	{
		return new NumberBound() {
			
			@Override
			public double getValue()
			{
				return eval(a) / eval(b);
			}
		};
	}
	
	
	public static NumberBound perc(final Object whole, final Object percent)
	{
		return new NumberBound() {
			
			@Override
			public double getValue()
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
				final double height = r.getRect().height();
				final double perRow = height / rows;
				
				final Vect origin = r.getRect().origin().add(0, perRow * index);
				final Vect size = r.getRect().size().setY(perRow);
				
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
				final double width = r.getRect().width();
				final double perCol = width / columns;
				
				final Vect origin = r.getRect().origin().add(perCol * index, 0);
				final Vect size = r.getRect().size().setX(perCol);
				
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
				final double height = r.getRect().height();
				final double width = r.getRect().height();
				final double perRow = height / rows;
				final double perCol = width / cols;
				
				final Vect origin = r.getRect().origin().add(perCol * left, perRow * (rows - top - 1));
				
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
				return r.getRect().shrink(eval(left), eval(top), eval(right), eval(bottom));
			}
		};
	}
	
	
	public static RectBound shrinkTop(final RectBound r, final Object shrink)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return r.getRect().shrink(0, eval(shrink), 0, 0);
			}
		};
	}
	
	
	public static RectBound shrinkBottom(final RectBound r, final Object shrink)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return r.getRect().shrink(0, 0, 0, eval(shrink));
			}
		};
	}
	
	
	public static RectBound shrinkLeft(final RectBound r, final Object shrink)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return r.getRect().shrink(eval(shrink), 0, 0, 0);
			}
		};
	}
	
	
	public static RectBound shrinkRight(final RectBound r, final Object shrink)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return r.getRect().shrink(0, 0, eval(shrink), 0);
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
				return r.getRect().grow(eval(left), eval(right), eval(top), eval(bottom));
			}
		};
	}
	
	
	public static RectBound growUp(final RectBound r, final Object grow)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return r.getRect().grow(0, eval(grow), 0, 0);
			}
		};
	}
	
	
	public static RectBound growDown(final RectBound r, final Object grow)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return r.getRect().grow(0, 0, 0, eval(grow));
			}
		};
	}
	
	
	public static RectBound growLeft(final RectBound r, final Object grow)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return r.getRect().grow(eval(grow), 0, 0, 0);
			}
		};
	}
	
	
	public static RectBound growRight(final RectBound r, final Object grow)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return r.getRect().grow(0, 0, eval(grow), 0);
			}
		};
	}
	
	
	public static RectBound box(final Object side)
	{
		return box(side, side);
	}
	
	
	public static RectBound box(final Vect origin, final Object width, final Object height)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return RectVal.make(origin, eval(width), eval(height));
			}
		};
	}
	
	
	public static RectBound box(final Object width, final Object height)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return RectVal.make(0, 0, eval(width), eval(height));
			}
		};
	}
	
	
	public static RectBound box(final RectBound r, final Object width, final Object height)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				final Vect origin = r.getRect().origin();
				
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
				final Vect origin = r.getRect().origin();
				
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
				final VectView size = r.getRect().size();
				final VectView center = centerTo.getRect().center();
				
				return RectVal.make(center.sub(size.half()), size);
			}
		};
	}
	
	
	public static RectBound centerTo(final RectBound r, final Vect centerTo)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				final VectView size = r.getRect().size();
				
				return RectVal.make(centerTo.getValue().sub(size.half()), size);
			}
		};
	}
	
	
	public static RectBound centerTo(final RectBound r, final Object x, final Object y)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				final VectView size = r.getRect().size();
				final VectView v = VectVal.make(eval(x), eval(y));
				
				return RectVal.make(v.sub(size.half()), size);
			}
		};
	}
	
	
	public static RectBound move(final RectBound r, final Vect move)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return r.getRect().move(move);
			}
		};
	}
	
	
	public static RectBound move(final RectBound r, final Object x, final Object y)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return r.getRect().move(eval(x), eval(y));
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
	public static RectBound expand(final Vect c, final Object allSides)
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
	public static RectBound expand(final Vect c, final Object horizontal, final Object vertical)
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
	public static RectBound expand(final Vect c, final Object top, final Object right, final Object bottom, final Object left)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				final double t = eval(top);
				final double r = eval(right);
				final double b = eval(bottom);
				final double l = eval(left);
				
				return RectVal.make(c.x() - l, c.y() - t, l + r, t + b);
			}
		};
	}
	
	
	public static RectBound edgeLeft(final RectBound r)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return r.getRect().shrink(0, 0, r.getRect().width(), 0);
			}
		};
	}
	
	
	public static RectBound edgeTop(final RectBound r)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return r.getRect().shrink(0, 0, 0, r.getRect().height());
			}
		};
	}
	
	
	public static RectBound edgeRight(final RectBound r)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return r.getRect().shrink(r.getRect().width(), 0, 0, 0);
			}
		};
	}
	
	
	public static RectBound edgeBottom(final RectBound r)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return r.getRect().shrink(0, r.getRect().height(), 0, 0);
			}
		};
	}
	
	
	public static VectView neg(final Vect c)
	{
		return mul(c, -1);
	}
	
	
	public static VectView half(final Vect c)
	{
		return mul(c, 0.5);
	}
	
	
	public static VectView add(final Vect c1, final Vect c2)
	{
		return new VectAdapter() {
			
			@Override
			public VectView getSource()
			{
				return c1.getValue().add(c2);
			}
		};
	}
	
	
	public static VectView add(final Vect c, final Object x, final Object y)
	{
		return add(c, x, y, 0);
	}
	
	
	public static VectView add(final Vect c, final Object x, final Object y, final Object z)
	{
		return new VectAdapter() {
			
			@Override
			public VectView getSource()
			{
				return c.getValue().add(eval(x), eval(y), eval(z));
			}
		};
	}
	
	
	public static VectView sub(final Vect c1, final Vect c2)
	{
		return new VectAdapter() {
			
			@Override
			public VectView getSource()
			{
				return c1.getValue().sub(c2);
			}
		};
	}
	
	
	public static VectView sub(final Vect c, final Object x, final Object y)
	{
		return sub(c, x, y, 0);
	}
	
	
	public static VectView sub(final Vect c, final Object x, final Object y, final Object z)
	{
		return new VectAdapter() {
			
			@Override
			public VectView getSource()
			{
				return c.getValue().sub(eval(x), eval(y), eval(z));
			}
			
		};
	}
	
	
	public static VectView mul(final Vect c, final Object mul)
	{
		return new VectAdapter() {
			
			@Override
			public VectView getSource()
			{
				return c.getValue().mul(eval(mul));
			}
			
		};
	}
	
	
	public static VectView norm(final Vect c, final Object norm)
	{
		return new VectAdapter() {
			
			@Override
			public VectView getSource()
			{
				return c.getValue().norm(eval(norm));
			}
			
		};
	}
	
	
	public static VectView origin(final RectBound r)
	{
		return new VectAdapter() {
			
			@Override
			public VectView getSource()
			{
				return r.getRect().origin();
			}
		};
	}
	
	
	public static VectView size(final RectBound r)
	{
		return new VectAdapter() {
			
			@Override
			public VectView getSource()
			{
				return r.getRect().size();
			}
		};
	}
	
	
	public static NumberBound height(final RectBound r)
	{
		return size(r).yc();
	}
	
	
	public static NumberBound width(final RectBound r)
	{
		return size(r).xc();
	}
	
	
	public static VectView center(final RectBound r)
	{
		return add(origin(r), half(size(r)));
	}
	
	
	public static VectView topLeft(final RectBound r)
	{
		return origin(r);
	}
	
	
	public static VectView topRight(final RectBound r)
	{
		return add(origin(r), width(r), 0);
	}
	
	
	public static VectView bottomLeft(final RectBound r)
	{
		return add(origin(r), 0, width(r));
	}
	
	
	public static VectView bottomRight(final RectBound r)
	{
		return add(origin(r), size(r));
	}
	
	
	public static VectView topCenter(final RectBound r)
	{
		return add(origin(r), half(width(r)), 0);
	}
	
	
	public static VectView bottomCenter(final RectBound r)
	{
		return add(origin(r), half(width(r)), width(r));
	}
	
	
	public static VectView centerLeft(final RectBound r)
	{
		return add(origin(r), 0, half(width(r)));
	}
	
	
	public static VectView centerRight(final RectBound r)
	{
		return add(origin(r), width(r), half(width(r)));
	}
	
	
	/**
	 * Zero-sized RectView at given coord
	 * 
	 * @param c coord
	 * @return rect
	 */
	public static RectBound zeroRect(final Vect c)
	{
		return new RectBound() {
			
			@Override
			public RectView getRect()
			{
				return RectVal.make(c.x(), c.y(), 0, 0);
			}
		};
	}
	
}
