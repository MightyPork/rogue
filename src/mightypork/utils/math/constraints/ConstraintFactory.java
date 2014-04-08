package mightypork.utils.math.constraints;


import mightypork.utils.math.animation.AnimDouble;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.coord.Rect;


/**
 * Constraint factory.<br>
 * Import statically for best experience.
 * 
 * @author MightyPork
 */
public class ConstraintFactory {
	
	public static NumberConstraint c_min(final NumberConstraint a, final NumberConstraint b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.min(a.getValue(), b.getValue());
			}
		};
	}
	
	
	public static NumberConstraint c_max(final NumberConstraint a, final NumberConstraint b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.max(a.getValue(), b.getValue());
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
	
	
	public static NumberConstraint c_add(final NumberConstraint a, final NumberConstraint b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return a.getValue() + b.getValue();
			}
		};
	}
	
	
	public static NumberConstraint c_sub(final NumberConstraint a, final NumberConstraint b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return a.getValue() - b.getValue();
			}
		};
	}
	
	
	public static NumberConstraint c_mul(final NumberConstraint a, final NumberConstraint b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return a.getValue() * b.getValue();
			}
		};
	}
	
	
	public static NumberConstraint c_div(final NumberConstraint a, final NumberConstraint b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return a.getValue() / b.getValue();
			}
		};
	}
	
	
	public static NumberConstraint c_percent(final NumberConstraint whole, final NumberConstraint percent)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return whole.getValue() * (percent.getValue() / 100);
			}
		};
	}
	
	
	public static NumberConstraint c_n(final double a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return a;
			}
		};
	}
	
	
	public static NumberConstraint c_n(final AnimDouble a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return a.now();
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
	
	
	public static RectConstraint c_shrink(RectConstraint r, NumberConstraint shrink)
	{
		return c_shrink(r, shrink, shrink, shrink, shrink);
	}
	
	
	public static RectConstraint c_shrink(RectConstraint context, NumberConstraint horiz, NumberConstraint vert)
	{
		return c_shrink(context, horiz, vert, horiz, vert);
	}
	
	
	public static RectConstraint c_shrink(final RectConstraint r, final NumberConstraint x1, final NumberConstraint y1, final NumberConstraint x2, final NumberConstraint y2)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().shrink(x1.getValue(), y1.getValue(), x2.getValue(), y2.getValue());
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
	
	
	public static RectConstraint c_grow(RectConstraint r, NumberConstraint grow)
	{
		return c_grow(r, grow, grow, grow, grow);
	}
	
	
	public static RectConstraint c_grow(RectConstraint r, NumberConstraint horiz, NumberConstraint vert)
	{
		return c_grow(r, horiz, vert, horiz, vert);
	}
	
	
	public static RectConstraint c_grow(final RectConstraint r, final NumberConstraint x1, final NumberConstraint y1, final NumberConstraint x2, final NumberConstraint y2)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().grow(x1.getValue(), y1.getValue(), x2.getValue(), y2.getValue());
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
	
	
	public static RectConstraint c_box(final RectConstraint r, final NumberConstraint width, final NumberConstraint height)
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
						width.getValue(),
						height.getValue()
				);
				//@formatter:on
			}
		};
	}
	
	
	public static RectConstraint c_box(final RectConstraint r, final NumberConstraint x, final NumberConstraint y, final NumberConstraint width, final NumberConstraint height)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				final Coord origin = r.getRect().getOrigin();
				
				//@formatter:off
				return Rect.fromSize(
						origin.x + x.getValue(),
						origin.y + y.getValue(),						
						width.getValue(),
						height.getValue()
				);
				//@formatter:on
			}
		};
	}
	
	
	public static RectConstraint c_centered(final RectConstraint r, final NumberConstraint x, final NumberConstraint y)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				final Coord size = r.getRect().getSize();
				
				return Rect.fromSize(x.getValue() - size.x / 2D, y.getValue() - size.y / 2D, size.x, size.y);
			}
		};
	}
	
	
	public static RectConstraint c_box_abs(final RectConstraint r, final NumberConstraint x1, final NumberConstraint y1, final NumberConstraint x2, final NumberConstraint y2)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				final Coord origin = r.getRect().getOrigin();
				
				//@formatter:off
				return new Rect(origin.add(x1.getValue(), y1.getValue()), origin.add(x2.getValue(), y2.getValue()));
				//@formatter:on
			}
		};
	}
	
	
	public static RectConstraint c_move(final RectConstraint r, final NumberConstraint x, final NumberConstraint y)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().add(x.getValue(), y.getValue());
			}
		};
	}
	
}
