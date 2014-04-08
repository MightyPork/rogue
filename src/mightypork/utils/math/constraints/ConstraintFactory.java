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
	
	public static NumEvaluable c_min(final NumEvaluable a, final NumEvaluable b)
	{
		return new NumEvaluable() {
			
			@Override
			public double getValue()
			{
				return Math.min(a.getValue(), b.getValue());
			}
		};
	}
	
	
	public static NumEvaluable c_max(final NumEvaluable a, final NumEvaluable b)
	{
		return new NumEvaluable() {
			
			@Override
			public double getValue()
			{
				return Math.max(a.getValue(), b.getValue());
			}
		};
	}
	
	
	public static NumEvaluable c_abs(final NumEvaluable a)
	{
		return new NumEvaluable() {
			
			@Override
			public double getValue()
			{
				return Math.abs(a.getValue());
			}
		};
	}
	
	
	public static NumEvaluable c_round(final NumEvaluable a)
	{
		return new NumEvaluable() {
			
			@Override
			public double getValue()
			{
				return Math.round(a.getValue());
			}
		};
	}
	
	
	public static RectEvaluable c_round(final RectEvaluable r)
	{
		return new RectEvaluable() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().round();
			}
		};
	}
	
	
	public static NumEvaluable c_ceil(final NumEvaluable a)
	{
		return new NumEvaluable() {
			
			@Override
			public double getValue()
			{
				return Math.ceil(a.getValue());
			}
		};
	}
	
	
	public static NumEvaluable c_floor(final NumEvaluable a)
	{
		return new NumEvaluable() {
			
			@Override
			public double getValue()
			{
				return Math.floor(a.getValue());
			}
		};
	}
	
	
	public static NumEvaluable c_neg(final NumEvaluable a)
	{
		return new NumEvaluable() {
			
			@Override
			public double getValue()
			{
				return -a.getValue();
			}
		};
	}
	
	
	public static NumEvaluable c_add(final NumEvaluable a, final NumEvaluable b)
	{
		return new NumEvaluable() {
			
			@Override
			public double getValue()
			{
				return a.getValue() + b.getValue();
			}
		};
	}
	
	
	public static NumEvaluable c_sub(final NumEvaluable a, final NumEvaluable b)
	{
		return new NumEvaluable() {
			
			@Override
			public double getValue()
			{
				return a.getValue() - b.getValue();
			}
		};
	}
	
	
	public static NumEvaluable c_mul(final NumEvaluable a, final NumEvaluable b)
	{
		return new NumEvaluable() {
			
			@Override
			public double getValue()
			{
				return a.getValue() * b.getValue();
			}
		};
	}
	
	
	public static NumEvaluable c_div(final NumEvaluable a, final NumEvaluable b)
	{
		return new NumEvaluable() {
			
			@Override
			public double getValue()
			{
				return a.getValue() / b.getValue();
			}
		};
	}
	
	
	public static NumEvaluable c_percent(final NumEvaluable whole, final NumEvaluable percent)
	{
		return new NumEvaluable() {
			
			@Override
			public double getValue()
			{
				return whole.getValue() * (percent.getValue() / 100);
			}
		};
	}
	
	
	public static NumEvaluable c_n(final double a)
	{
		return new NumEvaluable() {
			
			@Override
			public double getValue()
			{
				return a;
			}
		};
	}
	
	
	public static NumEvaluable c_n(final AnimDouble a)
	{
		return new NumEvaluable() {
			
			@Override
			public double getValue()
			{
				return a.now();
			}
		};
	}
	
	
	public static NumEvaluable c_width(final RectEvaluable r)
	{
		return new NumEvaluable() {
			
			@Override
			public double getValue()
			{
				return r.getRect().getSize().x;
			}
		};
	}
	
	
	public static NumEvaluable c_height(final RectEvaluable r)
	{
		return new NumEvaluable() {
			
			@Override
			public double getValue()
			{
				return r.getRect().getSize().y;
			}
		};
	}
	
	
	public static RectEvaluable c_row(final RectEvaluable r, final int rows, final int index)
	{
		return new RectEvaluable() {
			
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
	
	
	public static RectEvaluable c_column(final RectEvaluable r, final int columns, final int index)
	{
		return new RectEvaluable() {
			
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
	
	
	public static RectEvaluable c_shrink(RectEvaluable r, NumEvaluable shrink)
	{
		return c_shrink(r, shrink, shrink, shrink, shrink);
	}
	
	
	public static RectEvaluable c_shrink(RectEvaluable context, NumEvaluable horiz, NumEvaluable vert)
	{
		return c_shrink(context, horiz, vert, horiz, vert);
	}
	
	
	public static RectEvaluable c_shrink(final RectEvaluable r, final NumEvaluable x1, final NumEvaluable y1, final NumEvaluable x2, final NumEvaluable y2)
	{
		return new RectEvaluable() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().shrink(x1.getValue(), y1.getValue(), x2.getValue(), y2.getValue());
			}
		};
	}
	
	
	public static RectEvaluable c_center(final RectEvaluable r)
	{
		return new RectEvaluable() {
			
			@Override
			public Rect getRect()
			{
				return Rect.fromSize(r.getRect().getCenter(), 0, 0);
			}
		};
	}
	
	
	public static RectEvaluable c_grow(RectEvaluable r, NumEvaluable grow)
	{
		return c_grow(r, grow, grow, grow, grow);
	}
	
	
	public static RectEvaluable c_grow(RectEvaluable r, NumEvaluable horiz, NumEvaluable vert)
	{
		return c_grow(r, horiz, vert, horiz, vert);
	}
	
	
	public static RectEvaluable c_grow(final RectEvaluable r, final NumEvaluable x1, final NumEvaluable y1, final NumEvaluable x2, final NumEvaluable y2)
	{
		return new RectEvaluable() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().grow(x1.getValue(), y1.getValue(), x2.getValue(), y2.getValue());
			}
		};
	}
	
	
	public static RectEvaluable c_tile(final RectEvaluable r, final int rows, final int cols, final int left, final int top)
	{
		return new RectEvaluable() {
			
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
	
	
	public static RectEvaluable c_box(final RectEvaluable r, final NumEvaluable width, final NumEvaluable height)
	{
		return new RectEvaluable() {
			
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
	
	
	public static RectEvaluable c_box(final RectEvaluable r, final NumEvaluable x, final NumEvaluable y, final NumEvaluable width, final NumEvaluable height)
	{
		return new RectEvaluable() {
			
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
	
	
	public static RectEvaluable c_centered(final RectEvaluable r, final NumEvaluable x, final NumEvaluable y)
	{
		return new RectEvaluable() {
			
			@Override
			public Rect getRect()
			{
				final Coord size = r.getRect().getSize();
				
				return Rect.fromSize(x.getValue() - size.x / 2D, y.getValue() - size.y / 2D, size.x, size.y);
			}
		};
	}
	
	
	public static RectEvaluable c_box_abs(final RectEvaluable r, final NumEvaluable x1, final NumEvaluable y1, final NumEvaluable x2, final NumEvaluable y2)
	{
		return new RectEvaluable() {
			
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
	
	
	public static RectEvaluable c_move(final RectEvaluable r, final NumEvaluable x, final NumEvaluable y)
	{
		return new RectEvaluable() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().add(x.getValue(), y.getValue());
			}
		};
	}
	
}
