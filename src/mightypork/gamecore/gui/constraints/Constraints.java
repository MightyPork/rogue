package mightypork.gamecore.gui.constraints;


import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.coord.Rect;


/**
 * Constraint factory.<br>
 * Import statically for best experience.
 * 
 * @author MightyPork
 */
public class Constraints {
	
	/* ================= Variables ================= */
	
	public static final NumberConstraint _mouseX = new NumberConstraint() {
		
		@Override
		public double getValue()
		{
			return InputSystem.getMousePos().x;
		}
	};
	
	public static final NumberConstraint _mouseY = new NumberConstraint() {
		
		@Override
		public double getValue()
		{
			return InputSystem.getMousePos().y;
		}
	};
	
	public static final NumberConstraint _screenW = new NumberConstraint() {
		
		@Override
		public double getValue()
		{
			return DisplaySystem.getWidth();
		}
	};
	
	public static final NumberConstraint _screenH = new NumberConstraint() {
		
		@Override
		public double getValue()
		{
			return DisplaySystem.getHeight();
		}
	};
	
	
	/* ================= Arithmetics ================= */
	
	public static NumberConstraint _min(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.min(_nv(a), _nv(b));
			}
		};
	}
	
	
	public static NumberConstraint _max(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.max(_nv(a), _nv(b));
			}
		};
	}
	
	
	public static NumberConstraint _abs(final NumberConstraint a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.abs(a.getValue());
			}
		};
	}
	
	
	public static NumberConstraint _half(final NumberConstraint a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return a.getValue() / 2;
			}
		};
	}
	
	
	public static NumberConstraint _round(final NumberConstraint a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.round(a.getValue());
			}
		};
	}
	
	
	public static RectConstraint _round(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().round();
			}
		};
	}
	
	
	public static NumberConstraint _ceil(final NumberConstraint a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.ceil(a.getValue());
			}
		};
	}
	
	
	public static NumberConstraint _floor(final NumberConstraint a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.floor(a.getValue());
			}
		};
	}
	
	
	public static NumberConstraint _neg(final NumberConstraint a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return -a.getValue();
			}
		};
	}
	
	
	public static NumberConstraint _add(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return _nv(a) + _nv(b);
			}
		};
	}
	
	
	public static NumberConstraint _sub(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return _nv(a) - _nv(b);
			}
		};
	}
	
	
	public static NumberConstraint _mul(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return _nv(a) * _nv(b);
			}
		};
	}
	
	
	public static NumberConstraint _div(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return _nv(a) / _nv(b);
			}
		};
	}
	
	
	public static NumberConstraint _percent(final Object whole, final Object percent)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return _nv(whole) * (_nv(percent) / 100);
			}
		};
	}
	
	
	public static NumberConstraint _width(final RectConstraint r)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return r.getRect().getSize().x;
			}
		};
	}
	
	
	public static NumberConstraint _height(final RectConstraint r)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return r.getRect().getSize().y;
			}
		};
	}
	
	
	/* ================= Layout utilities ================= */
	
	public static RectConstraint _row(final RectConstraint r, final int rows, final int index)
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
	
	
	public static RectConstraint _column(final RectConstraint r, final int columns, final int index)
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
	
	
	public static RectConstraint _tile(final RectConstraint r, final int rows, final int cols, final int left, final int top)
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
	
	
	/* ================= Rect manipulation ================= */
	
	public static RectConstraint _shrink(RectConstraint r, Object shrink)
	{
		final NumberConstraint n = _n(shrink);
		return _shrink(r, n, n, n, n);
	}
	
	
	public static RectConstraint _shrink(RectConstraint context, Object horiz, Object vert)
	{
		return _shrink(context, horiz, vert, horiz, vert);
	}
	
	
	public static RectConstraint _shrink(final RectConstraint r, final Object xmin, final Object ymin, final Object xmax, final Object ymax)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().shrink(_nv(xmin), _nv(ymin), _nv(xmax), _nv(ymax));
			}
		};
	}
	
	
	public static RectConstraint _shrink_up(final RectConstraint r, final Object shrink)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().shrink(0, _nv(shrink), 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _shrink_down(final RectConstraint r, final Object shrink)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().shrink(0, 0, 0, _nv(shrink));
			}
		};
	}
	
	
	public static RectConstraint _shrink_left(final RectConstraint r, final Object shrink)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().shrink(_nv(shrink), 0, 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _shrink_right(final RectConstraint r, final Object shrink)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().shrink(0, 0, _nv(shrink), 0);
			}
		};
	}
	
	
	public static RectConstraint _grow(RectConstraint r, Object grow)
	{
		final NumberConstraint n = _n(grow);
		return _grow(r, n, n, n, n);
	}
	
	
	public static RectConstraint _grow(RectConstraint r, Object horiz, Object vert)
	{
		return _grow(r, horiz, vert, horiz, vert);
	}
	
	
	public static RectConstraint _grow(final RectConstraint r, final Object xmin, final Object ymin, final Object xmax, final Object ymax)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().grow(_nv(xmin), _nv(ymin), _nv(xmax), _nv(ymax));
			}
		};
	}
	
	
	public static RectConstraint _grow_up(final RectConstraint r, final Object grow)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().grow(0, _nv(grow), 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _grow_down(final RectConstraint r, final Object grow)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().grow(0, 0, 0, _nv(grow));
			}
		};
	}
	
	
	public static RectConstraint _grow_left(final RectConstraint r, final Object grow)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().grow(_nv(grow), 0, 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _grow_right(final RectConstraint r, final Object grow)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().grow(0, 0, _nv(grow), 0);
			}
		};
	}
	
	
	/* ================= Box creation ================= */
	
	public static RectConstraint _box(final Object width, final Object height)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				//@formatter:off
				return Rect.fromSize(
						0,
						0,
						_nv(width),
						_nv(height)
				);
				//@formatter:on
			}
		};
	}
	
	
	public static RectConstraint _box(final RectConstraint r, final Object width, final Object height)
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
						_nv(width),
						_nv(height)
				);
				//@formatter:on
			}
		};
	}
	
	
	public static RectConstraint _box(final RectConstraint r, final Object x, final Object y, final Object width, final Object height)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				final Coord origin = r.getRect().getOrigin();
				
				//@formatter:off
				return Rect.fromSize(
						origin.x + _nv(x),
						origin.y + _nv(y),						
						_nv(width),
						_nv(height)
				);
				//@formatter:on
			}
		};
	}
	
	
	public static RectConstraint _box_abs(final RectConstraint r, final Object xmin, final Object ymin, final Object xmax, final Object ymax)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				final Coord origin = r.getRect().getOrigin();
				
				//@formatter:off
				return new Rect(origin.add(_nv(xmin), _nv(ymin)), origin.add(_nv(xmax), _nv(ymax)));
				//@formatter:on
			}
		};
	}
	
	
	/**
	 * Center rect around given coords
	 * 
	 * @param r rect
	 * @param x
	 * @param y
	 * @return centered
	 */
	public static RectConstraint _centered(final RectConstraint r, final Object x, final Object y)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				final Coord size = r.getRect().getSize();
				
				return Rect.fromSize(_nv(x) - size.x / 2D, _nv(y) - size.y / 2D, size.x, size.y);
			}
		};
	}
	
	
	public static RectConstraint _move(final RectConstraint r, final Object x, final Object y)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().add(_nv(x), _nv(y));
			}
		};
	}
	
	
	/* ================= Rect bounds ================= */
	
	/**
	 * Get zero-sized rect at the center
	 * 
	 * @param r context
	 * @return center
	 */
	public static RectConstraint _center(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return Rect.fromSize(r.getRect().getCenter(), 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _left_edge(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().shrink(0, 0, r.getRect().getWidth(), 0);
			}
		};
	}
	
	
	public static RectConstraint _top_edge(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().shrink(0, 0, 0, r.getRect().getHeight());
			}
		};
	}
	
	
	public static RectConstraint _right_edge(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().shrink(r.getRect().getWidth(), 0, 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _bottom_edge(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return r.getRect().shrink(0, r.getRect().getHeight(), 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _left_top(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return Rect.fromSize(r.getRect().getHMinVMin(), 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _left_bottom(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return Rect.fromSize(r.getRect().getHMinVMax(), 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _right_top(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return Rect.fromSize(r.getRect().getHMaxVMin(), 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _right_bottom(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return Rect.fromSize(r.getRect().getHMaxVMax(), 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _center_top(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return Rect.fromSize(r.getRect().getCenterVMin(), 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _center_bottom(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return Rect.fromSize(r.getRect().getCenterVMax(), 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _center_left(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return Rect.fromSize(r.getRect().getCenterHMin(), 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _center_right(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public Rect getRect()
			{
				return Rect.fromSize(r.getRect().getCenterHMax(), 0, 0);
			}
		};
	}
	
	
	/* ================= Helpers ================= */
	
	public static RectCache _cache(final RectConstraint rc)
	{
		return new RectCache(rc);
	}
	
	
	public static RectCache _cache(final Poller poller, final RectConstraint rc)
	{
		return new RectCache(poller, rc);
	}
	
	
	/**
	 * Convert {@link Number} to {@link NumberConstraint} if needed
	 * 
	 * @param o unknown numeric value
	 * @return converted
	 */
	public static NumberConstraint _n(final Object o)
	{
		
		if (o instanceof NumberConstraint) return (NumberConstraint) o;
		
		if (o instanceof Number) return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return ((Number) o).doubleValue();
			}
		};
		
		throw new IllegalArgumentException("Invalid numeric type.");
	}
	
	
	/**
	 * Convert {@link Number} or {@link NumberConstraint} to double (current
	 * value)
	 * 
	 * @param o unknown numeric value
	 * @return double value
	 */
	public static double _nv(final Object o)
	{
		return _n(o).getValue();
	}
	
}
