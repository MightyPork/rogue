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
	
	public static NumConstraint c_min(final NumConstraint a, final NumConstraint b)
	{
		return new NumConstraint(null) {
			
			@Override
			public double getValue()
			{
				return Math.min(a.getValue(), b.getValue());
			}
		};
	}
	
	
	public static NumConstraint c_max(final NumConstraint a, final NumConstraint b)
	{
		return new NumConstraint(null) {
			
			@Override
			public double getValue()
			{
				return Math.max(a.getValue(), b.getValue());
			}
		};
	}
	
	
	public static NumConstraint c_abs(final NumConstraint a)
	{
		return new NumConstraint(null) {
			
			@Override
			public double getValue()
			{
				return Math.abs(a.getValue());
			}
		};
	}
	
	
	public static NumConstraint c_round(final NumConstraint a)
	{
		return new NumConstraint(null) {
			
			@Override
			public double getValue()
			{
				return Math.round(a.getValue());
			}
		};
	}
	
	
	public static RectConstraint c_round(ConstraintContext context)
	{
		return new RectConstraint(context) {
			
			@Override
			public Rect getRect()
			{
				return getContext().getRect().round();
			}
		};
	}
	
	
	public static NumConstraint c_ceil(final NumConstraint a)
	{
		return new NumConstraint(null) {
			
			@Override
			public double getValue()
			{
				return Math.ceil(a.getValue());
			}
		};
	}
	
	
	public static NumConstraint c_floor(final NumConstraint a)
	{
		return new NumConstraint(null) {
			
			@Override
			public double getValue()
			{
				return Math.floor(a.getValue());
			}
		};
	}
	
	
	public static NumConstraint c_neg(final NumConstraint a)
	{
		return new NumConstraint(null) {
			
			@Override
			public double getValue()
			{
				return -a.getValue();
			}
		};
	}
	
	
	public static NumConstraint c_add(final NumConstraint a, final NumConstraint b)
	{
		return new NumConstraint(null) {
			
			@Override
			public double getValue()
			{
				return a.getValue() + b.getValue();
			}
		};
	}
	
	
	public static NumConstraint c_sub(final NumConstraint a, final NumConstraint b)
	{
		return new NumConstraint(null) {
			
			@Override
			public double getValue()
			{
				return a.getValue() - b.getValue();
			}
		};
	}
	
	
	public static NumConstraint c_mul(final NumConstraint a, final NumConstraint b)
	{
		return new NumConstraint(null) {
			
			@Override
			public double getValue()
			{
				return a.getValue() * b.getValue();
			}
		};
	}
	
	
	public static NumConstraint c_div(final NumConstraint a, final NumConstraint b)
	{
		return new NumConstraint(null) {
			
			@Override
			public double getValue()
			{
				return a.getValue() / b.getValue();
			}
		};
	}
	
	
	public static NumConstraint c_percent(final NumConstraint whole, final NumConstraint percent)
	{
		return new NumConstraint(null) {
			
			@Override
			public double getValue()
			{
				return whole.getValue() * (percent.getValue() / 100);
			}
		};
	}
	
	
	public static NumConstraint c_n(final double a)
	{
		return new NumConstraint(null) {
			
			@Override
			public double getValue()
			{
				return a;
			}
		};
	}
	
	
	public static NumConstraint c_n(final AnimDouble a)
	{
		return new NumConstraint(null) {
			
			@Override
			public double getValue()
			{
				return a.now();
			}
		};
	}
	
	
	public static NumConstraint c_width(final ConstraintContext context)
	{
		return new NumConstraint(context) {
			
			@Override
			public double getValue()
			{
				return getSize().x;
			}
		};
	}
	
	
	public static NumConstraint c_height(final ConstraintContext context)
	{
		return new NumConstraint(context) {
			
			@Override
			public double getValue()
			{
				return getSize().y;
			}
		};
	}
	
	
	public static RectConstraint c_row(ConstraintContext context, final int rows, final int index)
	{
		return new RectConstraint(context) {
			
			@Override
			public Rect getRect()
			{
				double height = getContext().getRect().size().y;
				double perRow = height / rows;
				
				return Rect.fromSize(getOrigin().add(0, perRow * index), getSize().setY(perRow));
			}
		};
	}
	
	
	public static RectConstraint c_column(ConstraintContext context, final int columns, final int index)
	{
		return new RectConstraint(context) {
			
			@Override
			public Rect getRect()
			{
				double width = getContext().getRect().size().x;
				double perCol = width / columns;
				
				return Rect.fromSize(getOrigin().add(perCol * index, 0), getSize().setX(perCol));
			}
		};
	}
	
	
	public static RectConstraint c_shrink(ConstraintContext context, NumConstraint shrink)
	{
		return c_shrink(context, shrink, shrink, shrink, shrink);
	}
	
	
	public static RectConstraint c_shrink(ConstraintContext context, NumConstraint horiz, NumConstraint vert)
	{
		return c_shrink(context, horiz, vert, horiz, vert);
	}
	
	
	public static RectConstraint c_shrink(ConstraintContext context, final NumConstraint left, final NumConstraint top, final NumConstraint right, final NumConstraint bottom)
	{
		return new RectConstraint(context) {
			
			@Override
			public Rect getRect()
			{
				return getContext().getRect().shrink(left.getValue(), top.getValue(), right.getValue(), bottom.getValue());
			}
		};
	}
	
	
	public static RectConstraint c_center(ConstraintContext context)
	{
		return new RectConstraint(context) {
			
			@Override
			public Rect getRect()
			{
				return Rect.fromSize(getContext().getRect().getCenter(), 0, 0);
			}
		};
	}
	
	
	public static RectConstraint c_grow(ConstraintContext context, NumConstraint grow)
	{
		return c_grow(context, grow, grow, grow, grow);
	}
	
	
	public static RectConstraint c_grow(ConstraintContext context, NumConstraint horiz, NumConstraint vert)
	{
		return c_grow(context, horiz, vert, horiz, vert);
	}
	
	
	public static RectConstraint c_grow(ConstraintContext context, final NumConstraint left, final NumConstraint top, final NumConstraint right, final NumConstraint bottom)
	{
		return new RectConstraint(context) {
			
			@Override
			public Rect getRect()
			{
				return getContext().getRect().grow(left.getValue(), top.getValue(), right.getValue(), bottom.getValue());
			}
		};
	}
	
	
	public static RectConstraint c_tile(ConstraintContext context, final int rows, final int cols, final int left, final int top)
	{
		return new RectConstraint(context) {
			
			@Override
			public Rect getRect()
			{
				double height = getSize().y;
				double width = getSize().y;
				double perRow = height / rows;
				double perCol = width / cols;
				
				return Rect.fromSize(getOrigin().add(perCol * left, perRow * (rows - top - 1)), perCol, perRow);
			}
		};
	}
	
	
	public static RectConstraint c_box_sized(ConstraintContext context, final NumConstraint width, final NumConstraint height)
	{
		return new RectConstraint(context) {
			
			@Override
			public Rect getRect()
			{
				Coord origin = getOrigin();
				
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
	
	
	public static RectConstraint c_box_sized(ConstraintContext context, final NumConstraint left, final NumConstraint bottom, final NumConstraint width, final NumConstraint height)
	{
		return new RectConstraint(context) {
			
			@Override
			public Rect getRect()
			{
				Coord origin = getOrigin();
				
				//@formatter:off
				return Rect.fromSize(
						origin.x + left.getValue(),
						origin.y + bottom.getValue(),
						width.getValue(),
						height.getValue()
				);
				//@formatter:on
			}
		};
	}
	
	
	public static RectConstraint c_box_abs(ConstraintContext context, final NumConstraint left, final NumConstraint bottom, final NumConstraint right, final NumConstraint top)
	{
		return new RectConstraint(context) {
			
			@Override
			public Rect getRect()
			{
				Coord origin = getOrigin();
				
				//@formatter:off
				return new Rect(
						origin.x + left.getValue(),
						origin.y + bottom.getValue(),
						origin.x + right.getValue(),
						origin.y + top.getValue()
				);
				//@formatter:on
			}
		};
	}
	
	
	public static RectConstraint c_move(ConstraintContext context, final NumConstraint x, final NumConstraint y)
	{
		return new RectConstraint(context) {
			
			@Override
			public Rect getRect()
			{
				return getContext().getRect().add(x.getValue(), y.getValue());
			}
		};
	}
	
}
