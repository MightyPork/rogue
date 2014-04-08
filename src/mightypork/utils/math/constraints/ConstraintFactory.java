package mightypork.utils.math.constraints;


import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.coord.Rect;


/**
 * Constraint factory.<br>
 * Import statically for best experience.
 * 
 * @author MightyPork
 */
public class ConstraintFactory {
	
	public static NumberConstraint c_min(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.min(n(a).getValue(), n(b).getValue());
			}
		};
	}
	
	public static NumberConstraint c_max(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.max(n(a).getValue(), n(b).getValue());
			}
		};
	}
	
	
	public static NumberConstraint c_abs(final NumberConstraint a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.abs(a.getValue());
			}
		};
	}
	
	
	public static NumberConstraint c_half(final NumberConstraint a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return a.getValue()/2;
			}
		};
	}
	
	
	public static NumberConstraint c_round(final NumberConstraint a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.round(a.getValue());
			}
		};
	}
	
	
	public static RectConstraint c_round(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().round();
			}
		};
	}
	
	
	public static NumberConstraint c_ceil(final NumberConstraint a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.ceil(a.getValue());
			}
		};
	}
	
	
	public static NumberConstraint c_floor(final NumberConstraint a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.floor(a.getValue());
			}
		};
	}
	
	
	public static NumberConstraint c_neg(final NumberConstraint a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return -a.getValue();
			}
		};
	}
	
	public static NumberConstraint c_add(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return n(a).getValue() + n(b).getValue();
			}
		};
	}
	
	public static NumberConstraint c_sub(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return n(a).getValue() - n(b).getValue();
			}
		};
	}

	
	public static NumberConstraint c_mul(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return n(a).getValue() * n(b).getValue();
			}
		};
	}

	
	public static NumberConstraint c_div(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return n(a).getValue() / n(b).getValue();
			}
		};
	}
	
	public static NumberConstraint c_percent(final Object whole, final Object percent)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return n(whole).getValue() * (n(percent).getValue() / 100);
			}
		};
	}
	
	
	public static NumberConstraint c_width(final RectConstraint r)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return r.getRect().getSize().x;
			}
		};
	}
	
	
	public static NumberConstraint c_height(final RectConstraint r)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return r.getRect().getSize().y;
			}
		};
	}
	
	
	public static RectConstraint c_row(final RectConstraint r, final int rows, final int index)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				final double height = r.getRect().getSize().y;
				final double perRow = height / rows;
				
				final Coord origin = r.getRect().getOrigin().add(0, perRow * index);
				final Coord size = r.getRect().getSize().setY(perRow);
				
				return Rect.fromSize(origin, size);
			}
		};
	}
	
	
	public static RectConstraint c_column(final RectConstraint r, final int columns, final int index)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				final double width = r.getRect().getSize().x;
				final double perCol = width / columns;
				
				final Coord origin = r.getRect().getOrigin().add(perCol * index, 0);
				final Coord size = r.getRect().getSize().setX(perCol);
				
				return Rect.fromSize(origin, size);
			}
		};
	}
	
	
	public static RectConstraint c_shrink(RectConstraint r, Object shrink)
	{
		NumberConstraint n = n(shrink);
		return c_shrink(r, n, n, n, n);
	}
	
	
	public static RectConstraint c_shrink(RectConstraint context, Object horiz, Object vert)
	{
		return c_shrink(context, horiz, vert, horiz, vert);
	}
	
	
	public static RectConstraint c_shrink(final RectConstraint r, final Object x1, final Object y1, final Object x2, final Object y2)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().shrink(n(x1).getValue(), n(y1).getValue(), n(x2).getValue(), n(y2).getValue());
			}
		};
	}
	
	
	public static RectConstraint c_center(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return Rect.fromSize(r.getRect().getCenter(), 0, 0);
			}
		};
	}
	
	public static RectConstraint c_grow(RectConstraint r, Object grow)
	{
		NumberConstraint n = n(grow);
		return c_grow(r, n, n, n, n);
	}
	
	
	public static RectConstraint c_grow(RectConstraint r, Object horiz, Object vert)
	{
		return c_grow(r, horiz, vert, horiz, vert);
	}
	
	
	public static RectConstraint c_grow(final RectConstraint r, final Object x1, final Object y1, final Object x2, final Object y2)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().grow(n(x1).getValue(), n(y1).getValue(), n(x2).getValue(), n(y2).getValue());
			}
		};
	}
	
	
	public static RectConstraint c_tile(final RectConstraint r, final int rows, final int cols, final int left, final int top)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				final double height = r.getRect().getSize().y;
				final double width = r.getRect().getSize().y;
				final double perRow = height / rows;
				final double perCol = width / cols;
				
				final Coord origin = r.getRect().getOrigin().add(perCol * left, perRow * (rows - top - 1));
				
				return Rect.fromSize(origin, perCol, perRow);
			}
		};
	}
	
	public static RectConstraint c_box(final RectConstraint r, final Object width, final Object height)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				final Coord origin = r.getRect().getOrigin();
				
				//@formatter:off
				return Rect.fromSize(
						origin.x,
						origin.y,
						n(width).getValue(),
						n(height).getValue()
				);
				//@formatter:on
			}
		};
	}
	
	
	public static RectConstraint c_box(final RectConstraint r, final Object x, final Object y, final Object width, final Object height)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				final Coord origin = r.getRect().getOrigin();
				
				//@formatter:off
				return Rect.fromSize(
						origin.x + n(x).getValue(),
						origin.y + n(y).getValue(),						
						n(width).getValue(),
						n(height).getValue()
				);
				//@formatter:on
			}
		};
	}
	
	
	/**
	 * Center rect around given coords
	 * @param r rect
	 * @param x
	 * @param y
	 * @return centered
	 */
	public static RectConstraint c_centered(final RectConstraint r, final Object x, final Object y)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				final Coord size = r.getRect().getSize();
				
				return Rect.fromSize(n(x).getValue() - size.x / 2D, n(y).getValue() - size.y / 2D, size.x, size.y);
			}
		};
	}
	
	
	public static RectConstraint c_box_abs(final RectConstraint r, final Object x1, final Object y1, final Object x2, final Object y2)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				final Coord origin = r.getRect().getOrigin();
				
				//@formatter:off
				return new Rect(origin.add(n(x1).getValue(), n(y1).getValue()), origin.add(n(x2).getValue(), n(y2).getValue()));
				//@formatter:on
			}
		};
	}

	public static RectConstraint c_move(final RectConstraint r, final Object x, final Object y)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().add(n(x).getValue(), n(y).getValue());
			}
		};
	}
	
	/**
	 * Convert {@link Double} to {@link NumberConstraint} if needed
	 * @param o unknown numeric value
	 * @return converted
	 */
	public static NumberConstraint n(final Object o) {
		
		if(o instanceof NumberConstraint) return (NumberConstraint) o;
		
		if(o instanceof Number) return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return ((Number) o).doubleValue();
			}
		};
		
		throw new IllegalArgumentException("Invalid numeric type.");
	}
	
}
