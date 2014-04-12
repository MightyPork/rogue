package mightypork.utils.math.constraints;


import mightypork.gamecore.control.timing.Poller;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.render.DisplaySystem;
import mightypork.utils.math.coord.FixedCoord;
import mightypork.utils.math.coord.SynthCoord3D;
import mightypork.utils.math.coord.Vec;
import mightypork.utils.math.coord.VecView;
import mightypork.utils.math.rect.FixedRect;
import mightypork.utils.math.rect.Rect;
import mightypork.utils.math.rect.RectView;


/**
 * Constraint factory.<br>
 * Import statically for best experience.
 * 
 * @author MightyPork
 */
public class Constraints {
	
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
	static double toDouble(final Object o)
	{
		return _n(o).getValue();
	}
	
	
	/**
	 * Convert {@link VecConstraint} to {@link VecArith}.
	 * 
	 * @param o unknown numeric value
	 * @return double value
	 */
	static VecView vec(final VecConstraint o)
	{
		return o.getVec();
	}
	
	
	/**
	 * Convert {@link RectConstraint} to {@link Rect}.
	 * 
	 * @param o unknown numeric value
	 * @return double value
	 */
	static RectView toRect(final RectConstraint o)
	{
		return o.getRect();
	}
	
	
	// =================== Number constraints ====================
	
	public static NumberConstraint _min(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.min(toDouble(a), toDouble(b));
			}
		};
	}
	
	
	public static NumberConstraint _max(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.max(toDouble(a), toDouble(b));
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
			public RectView getRect()
			{
				return toRect(r).round();
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
				return toDouble(a) + toDouble(b);
			}
		};
	}
	
	
	public static NumberConstraint _sub(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return toDouble(a) - toDouble(b);
			}
		};
	}
	
	
	public static NumberConstraint _mul(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return toDouble(a) * toDouble(b);
			}
		};
	}
	
	
	public static NumberConstraint _half(final Object a)
	{
		return _mul(a, 0.5);
	}
	
	
	public static NumberConstraint _div(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return toDouble(a) / toDouble(b);
			}
		};
	}
	
	
	public static NumberConstraint _percent(final Object whole, final Object percent)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return toDouble(whole) * (toDouble(percent) / 100);
			}
		};
	}
	
	
	public static RectConstraint _row(final RectConstraint r, final int rows, final int index)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				final double height = toRect(r).getHeight();
				final double perRow = height / rows;
				
				final Vec origin = toRect(r).getOrigin().add(0, perRow * index);
				final Vec size = toRect(r).getSize().setY(perRow);
				
				return new FixedRect(origin, size);
			}
		};
	}
	
	
	public static RectConstraint _column(final RectConstraint r, final int columns, final int index)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				final double width = toRect(r).getWidth();
				final double perCol = width / columns;
				
				final Vec origin = toRect(r).getOrigin().add(perCol * index, 0);
				final Vec size = toRect(r).getSize().setX(perCol);
				
				return new FixedRect(origin, size);
			}
		};
	}
	
	
	public static RectConstraint _tile(final RectConstraint r, final int rows, final int cols, final int left, final int top)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				final double height = toRect(r).getHeight();
				final double width = toRect(r).getHeight();
				final double perRow = height / rows;
				final double perCol = width / cols;
				
				final Vec origin = toRect(r).getOrigin().add(perCol * left, perRow * (rows - top - 1));
				
				return new FixedRect(origin, perCol, perRow);
			}
		};
	}
	
	
	/* ================= RectView manipulation ================= */
	
	public static RectConstraint _shrink(RectConstraint r, Object shrink)
	{
		final NumberConstraint n = _n(shrink);
		return _shrink(r, n, n, n, n);
	}
	
	
	public static RectConstraint _shrink(RectConstraint context, Object horiz, Object vert)
	{
		return _shrink(context, horiz, vert, horiz, vert);
	}
	
	
	public static RectConstraint _shrink(final RectConstraint r, final Object left, final Object top, final Object right, final Object bottom)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				return toRect(r).shrink(toDouble(left), toDouble(top), toDouble(right), toDouble(bottom));
			}
		};
	}
	
	
	public static RectConstraint _shrink_top(final RectConstraint r, final Object shrink)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				return toRect(r).shrink(0, toDouble(shrink), 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _shrink_bottom(final RectConstraint r, final Object shrink)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				return toRect(r).shrink(0, 0, 0, toDouble(shrink));
			}
		};
	}
	
	
	public static RectConstraint _shrink_left(final RectConstraint r, final Object shrink)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				return toRect(r).shrink(toDouble(shrink), 0, 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _shrink_right(final RectConstraint r, final Object shrink)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				return toRect(r).shrink(0, 0, toDouble(shrink), 0);
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
	
	
	public static RectConstraint _grow(final RectConstraint r, final Object left, final Object top, final Object right, final Object bottom)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				return toRect(r).grow(toDouble(left), toDouble(top), toDouble(right), toDouble(bottom));
			}
		};
	}
	
	
	public static RectConstraint _grow_up(final RectConstraint r, final Object grow)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				return toRect(r).grow(0, toDouble(grow), 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _grow_down(final RectConstraint r, final Object grow)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				return toRect(r).grow(0, 0, 0, toDouble(grow));
			}
		};
	}
	
	
	public static RectConstraint _grow_left(final RectConstraint r, final Object grow)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				return toRect(r).grow(toDouble(grow), 0, 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _grow_right(final RectConstraint r, final Object grow)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				return toRect(r).grow(0, 0, toDouble(grow), 0);
			}
		};
	}
	
	
	/* ================= Box creation ================= */
	
	public static RectConstraint _box(final Object side)
	{
		return _box(side, side);
	}
	
	
	public static RectConstraint _box(final VecConstraint origin, final Object width, final Object height)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				return new FixedRect(vec(origin), toDouble(width), toDouble(height));
			}
		};
	}
	
	
	public static RectConstraint _box(final Object width, final Object height)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				return new FixedRect(0, 0, toDouble(width), toDouble(height));
			}
		};
	}
	
	
	public static RectConstraint _box(final RectConstraint r, final Object width, final Object height)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				final Vec origin = toRect(r).getOrigin();
				
				return new FixedRect(origin.x(), origin.y(), toDouble(width), toDouble(height));
			}
		};
	}
	
	
	public static RectConstraint _box(final RectConstraint r, final Object x, final Object y, final Object width, final Object height)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				final Vec origin = toRect(r).getOrigin();
				
				return new FixedRect(origin.x() + toDouble(x), origin.y() + toDouble(y), toDouble(width), toDouble(height));
			}
		};
	}
	
	
	public static RectConstraint _box_abs(final RectConstraint r, final Object left, final Object top, final Object right, final Object bottom)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				final VecView origin = toRect(r).getOrigin();
				
				return new FixedRect(origin.add(toDouble(left), toDouble(top)), origin.add(toDouble(right), toDouble(bottom)));
			}
		};
	}
	
	
	public static RectConstraint _align(final RectConstraint r, final RectConstraint centerTo)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				final VecView size = toRect(r).getSize();
				final VecView center = centerTo.getRect().getCenter();
				
				return new FixedRect(center.sub(size.half()), size);
			}
		};
	}
	
	
	public static RectConstraint _align(final RectConstraint r, final VecConstraint centerTo)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				final VecView size = toRect(r).getSize();
				
				return new FixedRect(vec(centerTo).sub(size.half()), size);
			}
		};
	}
	
	
	public static RectConstraint _align(final RectConstraint r, final Vec centerTo)
	{
		return _align(r, new VecWrapper(centerTo));
	}
	
	
	public static RectConstraint _align(final RectConstraint r, final Object x, final Object y)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				final VecView size = toRect(r).getSize();
				final VecView v = new FixedCoord(toDouble(x), toDouble(y));
				
				return new FixedRect(v.sub(size.half()), size);
			}
		};
	}
	
	
	public static RectConstraint _move(final RectConstraint r, final VecConstraint move)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				final Vec v = move.getVec();
				
				return toRect(r).move(v);
			}
		};
	}
	
	
	public static RectConstraint _move(final RectConstraint r, final Object x, final Object y)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				return toRect(r).move(toDouble(x), toDouble(y));
			}
		};
	}
	
	
	/* ================= RectView bounds ================= */
	
	public static RectConstraint _left_edge(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				return toRect(r).shrink(0, 0, toRect(r).getWidth(), 0);
			}
		};
	}
	
	
	public static RectConstraint _top_edge(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				return toRect(r).shrink(0, 0, 0, toRect(r).getHeight());
			}
		};
	}
	
	
	public static RectConstraint _right_edge(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				return toRect(r).shrink(toRect(r).getWidth(), 0, 0, 0);
			}
		};
	}
	
	
	public static RectConstraint _bottom_edge(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				return toRect(r).shrink(0, toRect(r).getHeight(), 0, 0);
			}
		};
	}
	
	
	public static NumberConstraint _x(final VecConstraint c)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return vec(c).x();
			}
		};
	}
	
	
	public static NumberConstraint _y(final VecConstraint c)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return vec(c).y();
			}
		};
	}
	
	
	public static NumberConstraint _z(final VecConstraint c)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return vec(c).z();
			}
		};
	}
	
	
	public static VecConstraint _neg(final VecConstraint c)
	{
		return _mul(c, -1);
	}
	
	
	public static VecConstraint _half(final VecConstraint c)
	{
		return _mul(c, 0.5);
	}
	
	
	// --- add ---
	
	public static VecConstraint _add(final VecConstraint c1, final VecConstraint c2)
	{
		return new VecWrapper(new SynthCoord3D() {
			
			@Override
			public double x()
			{
				return vec(c1).x() + vec(c2).x();
			}
			
			
			@Override
			public double y()
			{
				return vec(c1).y() + vec(c2).y();
			}
			
			
			@Override
			public double z()
			{
				return vec(c1).z() + vec(c2).z();
			}
			
		});
	}
	
	
	public static VecConstraint _add(final VecConstraint c, final Object x, final Object y)
	{
		return _add(c, x, y, 0);
	}
	
	
	public static VecConstraint _add(final VecConstraint c, final Object x, final Object y, final Object z)
	{
		return new VecWrapper(new SynthCoord3D() {
			
			@Override
			public double x()
			{
				return vec(c).x() + toDouble(x);
			}
			
			
			@Override
			public double y()
			{
				return vec(c).y() + toDouble(y);
			}
			
			
			@Override
			public double z()
			{
				return vec(c).z() + toDouble(z);
			}
			
		});
	}
	
	
	// --- sub ---
	
	public static VecConstraint _sub(final VecConstraint c1, final VecConstraint c2)
	{
		return new VecWrapper(new SynthCoord3D() {
			
			@Override
			public double x()
			{
				return vec(c1).x() - vec(c2).x();
			}
			
			
			@Override
			public double y()
			{
				return vec(c1).y() - vec(c2).y();
			}
			
			
			@Override
			public double z()
			{
				return vec(c1).z() - vec(c2).z();
			}
			
		});
	}
	
	
	public static VecConstraint _sub(final VecConstraint c, final Object x, final Object y)
	{
		return _add(c, x, y, 0);
	}
	
	
	public static VecConstraint _sub(final VecConstraint c, final Object x, final Object y, final Object z)
	{
		return new VecWrapper(new SynthCoord3D() {
			
			@Override
			public double x()
			{
				return vec(c).x() - toDouble(x);
			}
			
			
			@Override
			public double y()
			{
				return vec(c).y() - toDouble(y);
			}
			
			
			@Override
			public double z()
			{
				return vec(c).z() - toDouble(z);
			}
			
		});
	}
	
	
	// --- mul ---
	
	public static VecConstraint _mul(final VecConstraint c, final Object mul)
	{
		return new VecWrapper(new SynthCoord3D() {
			
			@Override
			public double x()
			{
				return vec(c).x() * toDouble(mul);
			}
			
			
			@Override
			public double y()
			{
				return vec(c).y() * toDouble(mul);
			}
			
			
			@Override
			public double z()
			{
				return vec(c).z() * toDouble(mul);
			}
			
		});
	}
	
	
	// --- rects ---
	
	public static VecConstraint _origin(final RectConstraint r)
	{
		return new VecConstraint() {
			
			@Override
			public VecView getVec()
			{
				return toRect(r).getOrigin();
			}
		};
	}
	
	
	public static VecConstraint _size(final RectConstraint r)
	{
		return new VecConstraint() {
			
			@Override
			public VecView getVec()
			{
				return toRect(r).getSize();
			}
		};
	}
	
	
	public static NumberConstraint _width(final RectConstraint r)
	{
		return _x(_size(r));
	}
	
	
	public static NumberConstraint _height(final RectConstraint r)
	{
		return _y(_size(r));
	}
	
	
	public static VecConstraint _center(final RectConstraint r)
	{
		return _add(_origin(r), _half(_size(r)));
	}
	
	
	public static VecConstraint _top_left(final RectConstraint r)
	{
		return _origin(r);
	}
	
	
	public static VecConstraint _top_right(final RectConstraint r)
	{
		return _add(_origin(r), _width(r), 0);
	}
	
	
	public static VecConstraint _bottom_left(final RectConstraint r)
	{
		return _add(_origin(r), 0, _height(r));
	}
	
	
	public static VecConstraint _bottom_right(final RectConstraint r)
	{
		return _add(_origin(r), _size(r));
	}
	
	
	public static VecConstraint _center_top(final RectConstraint r)
	{
		return _add(_origin(r), _half(_width(r)), 0);
	}
	
	
	public static VecConstraint _center_bottom(final RectConstraint r)
	{
		return _add(_origin(r), _half(_width(r)), _height(r));
	}
	
	
	public static VecConstraint _center_left(final RectConstraint r)
	{
		return _add(_origin(r), 0, _half(_height(r)));
	}
	
	
	public static VecConstraint _center_right(final RectConstraint r)
	{
		return _add(_origin(r), _width(r), _half(_height(r)));
	}
	
	
	/**
	 * Zero-sized RectView at given coord
	 * 
	 * @param c coord
	 * @return rect
	 */
	public static RectConstraint _rect(final VecConstraint c)
	{
		return new RectConstraint() {
			
			@Override
			public RectView getRect()
			{
				final Vec v = vec(c);
				
				return new FixedRect(v.x(), v.y(), 0, 0);
			}
		};
	}
	
	
	/**
	 * Get proxy for {@link VecConstraint}
	 * 
	 * @param v vec coord
	 * @return proxy vec
	 */
	public static VecView _vec(final VecConstraint v)
	{
		return new SynthCoord3D() {
			
			@Override
			public double x()
			{
				return vec(v).x();
			}
			
			
			@Override
			public double y()
			{
				return vec(v).y();
			}
			
			
			@Override
			public double z()
			{
				return vec(v).z();
			}
		};
	}
	
	/* ================= Variables ================= */
	
	public static final NumberConstraint _mouseX = new NumberConstraint() {
		
		@Override
		public double getValue()
		{
			return InputSystem.getMousePos().x();
		}
	};
	
	public static final NumberConstraint _mouseY = new NumberConstraint() {
		
		@Override
		public double getValue()
		{
			return InputSystem.getMousePos().y();
		}
	};
	public static final NumberConstraint _mousePos = new NumberConstraint() {
		
		@Override
		public double getValue()
		{
			return InputSystem.getMousePos().y();
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
	
	/* ================= Layout utilities ================= */
	
	/* ================= Coords ================= */
	
	/* ================= Coords ================= */
	
	public static NumberConstraint _x(final Vec c)
	{
		return c.xc();
	}
	
	
	public static NumberConstraint _y(final Vec c)
	{
		return c.yc();
	}
	
	
	public static NumberConstraint _z(final Vec c)
	{
		return c.zc();
	}
	
	
	public static VecView _neg(final Vec c)
	{
		return _mul(c, -1);
	}
	
	
	public static VecView _half(final Vec c)
	{
		return _mul(c, 0.5);
	}
	
	
	// --- add ---
	
	public static VecView _add(final Vec c1, final Vec c2)
	{
		return new SynthCoord3D() {
			
			@Override
			public double x()
			{
				return c1.x() + c2.x();
			}
			
			
			@Override
			public double y()
			{
				return c1.y() + c2.y();
			}
			
			
			@Override
			public double z()
			{
				return c1.z() + c2.z();
			}
			
		};
	}
	
	
	public static VecView _add(final Vec c, final Object x, final Object y)
	{
		return _add(c, x, y, 0);
	}
	
	
	public static VecView _add(final Vec c, final Object x, final Object y, final Object z)
	{
		return new SynthCoord3D() {
			
			@Override
			public double x()
			{
				return c.x() + toDouble(x);
			}
			
			
			@Override
			public double y()
			{
				return c.y() + toDouble(y);
			}
			
			
			@Override
			public double z()
			{
				return c.z() + toDouble(z);
			}
			
		};
	}
	
	
	// --- sub ---
	
	public static VecView _sub(final Vec c1, final Vec c2)
	{
		return new SynthCoord3D() {
			
			@Override
			public double x()
			{
				return c1.x() - c2.x();
			}
			
			
			@Override
			public double y()
			{
				return c1.y() - c2.y();
			}
			
			
			@Override
			public double z()
			{
				return c1.z() - c2.z();
			}
			
		};
	}
	
	
	public static VecView _sub(final Vec c, final Object x, final Object y)
	{
		return _add(c, x, y, 0);
	}
	
	
	public static VecView _sub(final Vec c, final Object x, final Object y, final Object z)
	{
		return new SynthCoord3D() {
			
			@Override
			public double x()
			{
				return c.x() - toDouble(x);
			}
			
			
			@Override
			public double y()
			{
				return c.y() - toDouble(y);
			}
			
			
			@Override
			public double z()
			{
				return c.z() - toDouble(z);
			}
			
		};
	}
	
	
	// --- mul ---
	
	public static VecView _mul(final Vec c, final Object mul)
	{
		return new SynthCoord3D() {
			
			@Override
			public double x()
			{
				return c.x() * toDouble(mul);
			}
			
			
			@Override
			public double y()
			{
				return c.y() * toDouble(mul);
			}
			
			
			@Override
			public double z()
			{
				return c.z() * toDouble(mul);
			}
			
		};
	}
	
	
	public static VecView _origin(final Rect r)
	{
		return r.getOrigin();
	}
	
	
	public static VecView _size(final Rect r)
	{
		return r.getSize();
	}
	
	
	public static NumberConstraint _width(final Rect r)
	{
		return _x(_size(r));
	}
	
	
	public static NumberConstraint _height(final Rect r)
	{
		return _y(_size(r));
	}
	
	
	public static VecView _center(final Rect r)
	{
		return _add(_origin(r), _half(_size(r)));
	}
	
	
	public static VecView _top_left(final Rect r)
	{
		return _origin(r);
	}
	
	
	public static VecView _top_right(final Rect r)
	{
		return _add(_origin(r), _width(r), 0);
	}
	
	
	public static VecView _bottom_left(final Rect r)
	{
		return _add(_origin(r), 0, _height(r));
	}
	
	
	public static VecView _bottom_right(final Rect r)
	{
		return _add(_origin(r), _size(r));
	}
	
	
	public static VecView _center_top(final Rect r)
	{
		return _add(_origin(r), _half(_width(r)), 0);
	}
	
	
	public static VecView _center_bottom(final Rect r)
	{
		return _add(_origin(r), _half(_width(r)), _height(r));
	}
	
	
	public static VecView _center_left(final Rect r)
	{
		return _add(_origin(r), 0, _half(_height(r)));
	}
	
	
	public static VecView _center_right(final Rect r)
	{
		return _add(_origin(r), _width(r), _half(_height(r)));
	}
}
