package mightypork.utils.math.rect.xx;


//package mightypork.utils.math.rect;
//
//import mightypork.utils.math.constraints.NumberConstraint;
//import mightypork.utils.math.coord.VecValue;
//import mightypork.utils.math.coord.VecView;
//import mightypork.utils.math.coord.SynthCoord3D;
//
//
//public class RectCalc {
//    /* ================= Coords ================= */
//	
//	public static NumberConstraint _x(final VecValue c)
//	{
//		return c.xc();
//	}
//	
//	
//	public static NumberConstraint _y(final VecValue c)
//	{
//		return c.yc();
//	}
//	
//	
//	public static NumberConstraint _z(final VecValue c)
//	{
//		return c.zc();
//	}
//	
//	
//	public static VecView _neg(final VecValue c)
//	{
//		return _mul(c, -1);
//	}
//	
//	
//	public static VecView _half(final VecValue c)
//	{
//		return _mul(c, 0.5);
//	}
//	
//	
//	// --- add ---
//	
//	public static VecView _add(final VecValue c1, final VecValue c2)
//	{
//		return new SynthCoord3D() {
//			
//			@Override
//			public double x()
//			{
//				return c1.x() + c2.x();
//			}
//			
//			
//			@Override
//			public double y()
//			{
//				return c1.y() + c2.y();
//			}
//			
//			
//			@Override
//			public double z()
//			{
//				return c1.z() + c2.z();
//			}
//			
//		};
//	}
//	
//	
//	public static VecView _add(final VecValue c, final Object x, final Object y)
//	{
//		return _add(c, x, y, 0);
//	}
//	
//	
//	public static VecView _add(final VecValue c, final Object x, final Object y, final Object z)
//	{
//		return new SynthCoord3D() {
//			
//			@Override
//			public double x()
//			{
//				return c.x() + num(x);
//			}
//			
//			
//			@Override
//			public double y()
//			{
//				return c.y() + num(y);
//			}
//			
//			
//			@Override
//			public double z()
//			{
//				return c.z() + num(z);
//			}
//			
//		};
//	}
//	
//	
//	// --- sub ---
//	
//	public static VecView _sub(final VecValue c1, final VecValue c2)
//	{
//		return new SynthCoord3D() {
//			
//			@Override
//			public double x()
//			{
//				return c1.x() - c2.x();
//			}
//			
//			
//			@Override
//			public double y()
//			{
//				return c1.y() - c2.y();
//			}
//			
//			
//			@Override
//			public double z()
//			{
//				return c1.z() - c2.z();
//			}
//			
//		};
//	}
//	
//	
//	public static VecView _sub(final VecValue c, final Object x, final Object y)
//	{
//		return _add(c, x, y, 0);
//	}
//	
//	
//	public static VecView _sub(final VecValue c, final Object x, final Object y, final Object z)
//	{
//		return new SynthCoord3D() {
//			
//			@Override
//			public double x()
//			{
//				return c.x() - num(x);
//			}
//			
//			
//			@Override
//			public double y()
//			{
//				return c.y() - num(y);
//			}
//			
//			
//			@Override
//			public double z()
//			{
//				return c.z() - num(z);
//			}
//			
//		};
//	}
//	
//	
//	// --- mul ---
//	
//	public static VecView _mul(final VecValue c, final Object mul)
//	{
//		return new SynthCoord3D() {
//			
//			@Override
//			public double x()
//			{
//				return c.x() * num(mul);
//			}
//			
//			
//			@Override
//			public double y()
//			{
//				return c.y() * num(mul);
//			}
//			
//			
//			@Override
//			public double z()
//			{
//				return c.z() * num(mul);
//			}
//			
//		};
//	}
//
//	public static VecView _origin(final Rect r)
//	{
//		return r.getOrigin().view();
//	}
//	
//	
//	public static VecView _size(final Rect r)
//	{
//		return r.getSize().view();
//	}
//	
//	
//	public static NumberConstraint _width(final Rect r)
//	{
//		return _x(_size(r));
//	}
//	
//	
//	public static NumberConstraint _height(final Rect r)
//	{
//		return _y(_size(r));
//	}
//	
//	
//	public static VecView _center(final Rect r)
//	{
//		return _add(_origin(r), _half(_size(r)));
//	}
//	
//	
//	public static VecView _top_left(final Rect r)
//	{
//		return _origin(r);
//	}
//	
//	
//	public static VecView _top_right(final Rect r)
//	{
//		return _add(_origin(r), _width(r), 0);
//	}
//	
//	
//	public static VecView _bottom_left(final Rect r)
//	{
//		return _add(_origin(r), 0, _height(r));
//	}
//	
//	
//	public static VecView _bottom_right(final Rect r)
//	{
//		return _add(_origin(r), _size(r));
//	}
//	
//	
//	public static VecView _center_top(final Rect r)
//	{
//		return _add(_origin(r), _half(_width(r)), 0);
//	}
//	
//	
//	public static VecView _center_bottom(final Rect r)
//	{
//		return _add(_origin(r), _half(_width(r)), _height(r));
//	}
//	
//	
//	public static VecView _center_left(final Rect r)
//	{
//		return _add(_origin(r), 0, _half(_height(r)));
//	}
//	
//	
//	public static VecView _center_right(final Rect r)
//	{
//		return _add(_origin(r), _width(r), _half(_height(r)));
//	}
//	
//}
