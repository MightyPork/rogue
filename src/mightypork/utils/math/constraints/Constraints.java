package mightypork.utils.math.constraints;


import mightypork.gamecore.control.timing.Poller;
import mightypork.utils.math.coord.AbstractVecProxy;
import mightypork.utils.math.coord.Vec;
import mightypork.utils.math.coord.VecView;
import mightypork.utils.math.rect.RectValue;


/**
 * Constraint factory.<br>
 * Import statically for best experience.
 * 
 * @author MightyPork
 */
public class Constraints {
	
	public static RectCache cCached(final Poller poller, final RectConstraint rc)
	{
		return new RectCache(poller, rc);
	}
	
	
	/**
	 * Convert {@link Number} to {@link NumberConstraint} if needed
	 * 
	 * @param o unknown numeric value
	 * @return converted
	 */
	private static NumberConstraint toConstraint(final Object o)
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
	private static double toDouble(final Object o)
	{
		return toConstraint(o).getValue();
	}
	
	
	// =================== Number constraints ====================
	
	public static NumberConstraint cMin(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.min(toDouble(a), toDouble(b));
			}
		};
	}
	
	
	public static NumberConstraint cMax(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.max(toDouble(a), toDouble(b));
			}
		};
	}
	
	
	public static NumberConstraint cAbs(final NumberConstraint a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.abs(a.getValue());
			}
		};
	}
	
	
	public static NumberConstraint cHalf(final NumberConstraint a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return a.getValue() / 2;
			}
		};
	}
	
	
	public static NumberConstraint cRound(final NumberConstraint a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.round(a.getValue());
			}
		};
	}
	
	
	public static RectConstraint cRound(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return r.getRect().round();
			}
		};
	}
	
	
	public static NumberConstraint cCeil(final NumberConstraint a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.ceil(a.getValue());
			}
		};
	}
	
	
	public static NumberConstraint cFloor(final NumberConstraint a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return Math.floor(a.getValue());
			}
		};
	}
	
	
	public static NumberConstraint cNeg(final NumberConstraint a)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return -a.getValue();
			}
		};
	}
	
	
	public static NumberConstraint cAdd(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return toDouble(a) + toDouble(b);
			}
		};
	}
	
	
	public static NumberConstraint cSub(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return toDouble(a) - toDouble(b);
			}
		};
	}
	
	
	public static NumberConstraint cMul(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return toDouble(a) * toDouble(b);
			}
		};
	}
	
	
	public static NumberConstraint cHalf(final Object a)
	{
		return cMul(a, 0.5);
	}
	
	
	public static NumberConstraint cDiv(final Object a, final Object b)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return toDouble(a) / toDouble(b);
			}
		};
	}
	
	
	public static NumberConstraint cPerc(final Object whole, final Object percent)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return toDouble(whole) * (toDouble(percent) / 100);
			}
		};
	}
	
	
	public static RectConstraint cRow(final RectConstraint r, final int rows, final int index)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				final double height = r.getRect().getHeight();
				final double perRow = height / rows;
				
				final Vec origin = r.getRect().getOrigin().add(0, perRow * index);
				final Vec size = r.getRect().getSize().setY(perRow);
				
				return RectValue.make(origin, size);
			}
		};
	}
	
	
	public static RectConstraint cColumn(final RectConstraint r, final int columns, final int index)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				final double width = r.getRect().getWidth();
				final double perCol = width / columns;
				
				final Vec origin = r.getRect().getOrigin().add(perCol * index, 0);
				final Vec size = r.getRect().getSize().setX(perCol);
				
				return RectValue.make(origin, size);
			}
		};
	}
	
	
	public static RectConstraint cTile(final RectConstraint r, final int rows, final int cols, final int left, final int top)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				final double height = r.getRect().getHeight();
				final double width = r.getRect().getHeight();
				final double perRow = height / rows;
				final double perCol = width / cols;
				
				final Vec origin = r.getRect().getOrigin().add(perCol * left, perRow * (rows - top - 1));
				
				return RectValue.make(origin, perCol, perRow);
			}
		};
	}
	
	
	/* ================= RectView manipulation ================= */
	
	public static RectConstraint cShrink(RectConstraint r, Object shrink)
	{
		final NumberConstraint n = toConstraint(shrink);
		return cShrink(r, n, n, n, n);
	}
	
	
	public static RectConstraint cShrink(RectConstraint context, Object horiz, Object vert)
	{
		return cShrink(context, horiz, vert, horiz, vert);
	}
	
	
	public static RectConstraint cShrink(final RectConstraint r, final Object left, final Object top, final Object right, final Object bottom)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return r.getRect().shrink(toDouble(left), toDouble(top), toDouble(right), toDouble(bottom));
			}
		};
	}
	
	
	public static RectConstraint cShrinkTop(final RectConstraint r, final Object shrink)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return r.getRect().shrink(0, toDouble(shrink), 0, 0);
			}
		};
	}
	
	
	public static RectConstraint cShrinkBottom(final RectConstraint r, final Object shrink)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return r.getRect().shrink(0, 0, 0, toDouble(shrink));
			}
		};
	}
	
	
	public static RectConstraint cShrinkLeft(final RectConstraint r, final Object shrink)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return r.getRect().shrink(toDouble(shrink), 0, 0, 0);
			}
		};
	}
	
	
	public static RectConstraint cShrinkRight(final RectConstraint r, final Object shrink)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return r.getRect().shrink(0, 0, toDouble(shrink), 0);
			}
		};
	}
	
	
	public static RectConstraint cGrow(RectConstraint r, Object grow)
	{
		final NumberConstraint n = toConstraint(grow);
		return cGrow(r, n, n, n, n);
	}
	
	
	public static RectConstraint cGrow(RectConstraint r, Object horiz, Object vert)
	{
		return cGrow(r, horiz, vert, horiz, vert);
	}
	
	
	public static RectConstraint cGrow(final RectConstraint r, final Object left, final Object right, final Object top, final Object bottom)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return r.getRect().grow(toDouble(left), toDouble(right), toDouble(top), toDouble(bottom));
			}
		};
	}
	
	
	public static RectConstraint cGrowUp(final RectConstraint r, final Object grow)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return r.getRect().grow(0, toDouble(grow), 0, 0);
			}
		};
	}
	
	
	public static RectConstraint cGrowDown(final RectConstraint r, final Object grow)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return r.getRect().grow(0, 0, 0, toDouble(grow));
			}
		};
	}
	
	
	public static RectConstraint cGrowLeft(final RectConstraint r, final Object grow)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return r.getRect().grow(toDouble(grow), 0, 0, 0);
			}
		};
	}
	
	
	public static RectConstraint cGrowRight(final RectConstraint r, final Object grow)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return r.getRect().grow(0, 0, toDouble(grow), 0);
			}
		};
	}
	
	
	/* ================= Box creation ================= */
	
	public static RectConstraint cBox(final Object side)
	{
		return cBox(side, side);
	}
	
	
	public static RectConstraint cBox(final VecConstraint origin, final Object width, final Object height)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return RectValue.make(origin.getVec(), toDouble(width), toDouble(height));
			}
		};
	}
	
	
	public static RectConstraint cBox(final Object width, final Object height)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return RectValue.make(0, 0, toDouble(width), toDouble(height));
			}
		};
	}
	
	
	public static RectConstraint cBox(final RectConstraint r, final Object width, final Object height)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				final Vec origin = r.getRect().getOrigin();
				
				return RectValue.make(origin.x(), origin.y(), toDouble(width), toDouble(height));
			}
		};
	}
	
	
	public static RectConstraint cBox(final RectConstraint r, final Object x, final Object y, final Object width, final Object height)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				final Vec origin = r.getRect().getOrigin();
				
				return RectValue.make(origin.x() + toDouble(x), origin.y() + toDouble(y), toDouble(width), toDouble(height));
			}
		};
	}
	
	
	public static RectConstraint cCenterTo(final RectConstraint r, final RectConstraint centerTo)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				final VecView size = r.getRect().getSize();
				final VecView center = centerTo.getRect().getCenter();
				
				return RectValue.make(center.sub(size.half()), size);
			}
		};
	}
	
	
	public static RectConstraint cCenterTo(final RectConstraint r, final VecConstraint centerTo)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				final VecView size = r.getRect().getSize();
				
				return RectValue.make(centerTo.getVec().sub(size.half()), size);
			}
		};
	}
	
	
	public static RectConstraint cCenterTo(final RectConstraint r, final Object x, final Object y)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				final VecView size = r.getRect().getSize();
				final VecView v = VecView.make(toDouble(x), toDouble(y));
				
				return RectValue.make(v.sub(size.half()), size);
			}
		};
	}
	
	
	public static RectConstraint cMove(final RectConstraint r, final VecConstraint move)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return r.getRect().move(move.getVec());
			}
		};
	}
	
	
	public static RectConstraint cMove(final RectConstraint r, final Object x, final Object y)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return r.getRect().move(toDouble(x), toDouble(y));
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
	public static RectConstraint cExpand(final VecConstraint c, final Object allSides)
	{
		return cExpand(c, allSides, allSides, allSides, allSides);
	}
	
	
	/**
	 * Make a rect around coord
	 * 
	 * @param c coord
	 * @param horizontal horisontal grow (left, right)
	 * @param vertical vertical grow (top, bottom)
	 * @return rect constraint
	 */
	public static RectConstraint cExpand(final VecConstraint c, final Object horizontal, final Object vertical)
	{
		return cExpand(c, horizontal, vertical, horizontal, vertical);
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
	public static RectConstraint cExpand(final VecConstraint c, final Object top, final Object right, final Object bottom, final Object left)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				final double t = toDouble(top);
				final double r = toDouble(right);
				final double b = toDouble(bottom);
				final double l = toDouble(left);
				
				final double x = c.getVec().x();
				final double y = c.getVec().y();
				
				return RectValue.make(x - l, y - t, l + r, t + b);
			}
		};
	}
	
	
	/* ================= RectView bounds ================= */
	
	public static RectConstraint cLeftEdge(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return r.getRect().shrink(0, 0, r.getRect().getWidth(), 0);
			}
		};
	}
	
	
	public static RectConstraint cTopEdge(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return r.getRect().shrink(0, 0, 0, r.getRect().getHeight());
			}
		};
	}
	
	
	public static RectConstraint cRightEdge(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return r.getRect().shrink(r.getRect().getWidth(), 0, 0, 0);
			}
		};
	}
	
	
	public static RectConstraint cBottomEdge(final RectConstraint r)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				return r.getRect().shrink(0, r.getRect().getHeight(), 0, 0);
			}
		};
	}
	
	
	public static NumberConstraint cX(final VecConstraint c)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return c.getVec().x();
			}
		};
	}
	
	
	public static NumberConstraint cY(final VecConstraint c)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return c.getVec().y();
			}
		};
	}
	
	
	public static NumberConstraint cZ(final VecConstraint c)
	{
		return new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return c.getVec().z();
			}
		};
	}
	
	
	public static VecConstraint cNeg(final VecConstraint c)
	{
		return cMul(c, -1);
	}
	
	
	public static VecConstraint cHalf(final VecConstraint c)
	{
		return cMul(c, 0.5);
	}
	
	
	// --- add ---
	
	public static VecConstraint cAdd(final VecConstraint c1, final VecConstraint c2)
	{
		return new AbstractVecProxy() {
			
			@Override
			public VecView getSource()
			{
				return c1.getVec().add(c2.getVec());
			}
		};
	}
	
	
	public static VecConstraint cAdd(final VecConstraint c, final Object x, final Object y)
	{
		return cAdd(c, x, y, 0);
	}
	
	
	public static VecConstraint cAdd(final VecConstraint c, final Object x, final Object y, final Object z)
	{
		return new AbstractVecProxy() {
			
			@Override
			public VecView getSource()
			{
				return c.getVec().add(toDouble(x), toDouble(y), toDouble(z));
			}
		};
	}
	
	
	// --- sub ---
	
	public static VecConstraint cSub(final VecConstraint c1, final VecConstraint c2)
	{
		return new AbstractVecProxy() {
			
			@Override
			public VecView getSource()
			{
				return c1.getVec().sub(c2.getVec());
			}
		};
	}
	
	
	public static VecConstraint cSub(final VecConstraint c, final Object x, final Object y)
	{
		return cSub(c, x, y, 0);
	}
	
	
	public static VecConstraint cSub(final VecConstraint c, final Object x, final Object y, final Object z)
	{
		return new AbstractVecProxy() {
			
			@Override
			public VecView getSource()
			{
				return c.getVec().sub(toDouble(x), toDouble(y), toDouble(z));
			}
			
		};
	}
	
	
	// --- mul ---
	
	public static VecConstraint cMul(final VecConstraint c, final Object mul)
	{
		return new AbstractVecProxy() {
			
			@Override
			public VecView getSource()
			{
				return c.getVec().mul(toDouble(mul));
			}
			
		};
	}
	
	
	// --- rects ---
	
	public static VecConstraint cOrigin(final RectConstraint r)
	{
		return new AbstractVecProxy() {
			
			@Override
			public VecView getSource()
			{
				return r.getRect().getOrigin();
			}
		};
	}
	
	
	public static VecConstraint cSize(final RectConstraint r)
	{
		return new AbstractVecProxy() {
			
			@Override
			public VecView getSource()
			{
				return r.getRect().getSize();
			}
		};
	}
	
	
	public static NumberConstraint cHeight(final RectConstraint r)
	{
		return cY(cSize(r));
	}
	
	
	public static NumberConstraint cWidth(final RectConstraint r)
	{
		return cX(cSize(r));
	}
	
	
	public static VecConstraint cCenter(final RectConstraint r)
	{
		return cAdd(cOrigin(r), cHalf(cSize(r)));
	}
	
	
	public static VecConstraint cTopLeft(final RectConstraint r)
	{
		return cOrigin(r);
	}
	
	
	public static VecConstraint cTopRight(final RectConstraint r)
	{
		return cAdd(cOrigin(r), cWidth(r), 0);
	}
	
	
	public static VecConstraint cBottomLeft(final RectConstraint r)
	{
		return cAdd(cOrigin(r), 0, cWidth(r));
	}
	
	
	public static VecConstraint cBottomRight(final RectConstraint r)
	{
		return cAdd(cOrigin(r), cSize(r));
	}
	
	
	public static VecConstraint cTopCenter(final RectConstraint r)
	{
		return cAdd(cOrigin(r), cHalf(cWidth(r)), 0);
	}
	
	
	public static VecConstraint cBottomCenter(final RectConstraint r)
	{
		return cAdd(cOrigin(r), cHalf(cWidth(r)), cWidth(r));
	}
	
	
	public static VecConstraint cCenterLeft(final RectConstraint r)
	{
		return cAdd(cOrigin(r), 0, cHalf(cWidth(r)));
	}
	
	
	public static VecConstraint cCenterRight(final RectConstraint r)
	{
		return cAdd(cOrigin(r), cWidth(r), cHalf(cWidth(r)));
	}
	
	
	/**
	 * Zero-sized RectView at given coord
	 * 
	 * @param c coord
	 * @return rect
	 */
	public static RectConstraint cZeroRect(final VecConstraint c)
	{
		return new RectConstraint() {
			
			@Override
			public RectValue getRect()
			{
				final Vec v = c.getVec();
				
				return RectValue.make(v.x(), v.y(), 0, 0);
			}
		};
	}
	
	
	/**
	 * Convert VecConstraint to VecView
	 * 
	 * @param c vec constraint
	 * @return CReverseProxy
	 */
	public static VecView cVec(final VecConstraint c)
	{
		return new CReverseProxy(c);
	}
	
}
