package mightypork.utils.math.rect;


import static mightypork.utils.math.constraints.Constraints.*;
import mightypork.utils.math.constraints.VecConstraint;
import mightypork.utils.math.coord.Vec;
import mightypork.utils.math.coord.VecView;


public abstract class RectImpl<T extends Rect> implements RectMath<T> {
	
	private VecConstraint tl, tc, tr, cl, c, cr, bl, bc, br;
	
	
	@Override
	public RectView getRect()
	{
		return this.view();
	}
	
	
	@Override
	public abstract VecView getOrigin();
	
	
	@Override
	public abstract VecView getSize();
	
	
	@Override
	public VecView getTopLeft()
	{
		if (tl == null) tl = cTopLeft(this);
		return tl.getVec();
	}
	
	
	@Override
	public VecView getTopCenter()
	{
		if (tc == null) tc = cTopCenter(this);
		return tc.getVec();
	}
	
	
	@Override
	public VecView getTopRight()
	{
		if (tr == null) tr = cTopRight(this);
		return tr.getVec();
	}
	
	
	@Override
	public VecView getCenterLeft()
	{
		if (cl == null) cl = cCenterLeft(this);
		return cl.getVec();
	}
	
	
	@Override
	public final VecView getCenter()
	{
		if (c == null) c = cCenter(this);
		return c.getVec();
	}
	
	
	@Override
	public VecView getCenterRight()
	{
		if (cr == null) cr = cCenterRight(this);
		return cr.getVec();
	}
	
	
	@Override
	public VecView getBottomLeft()
	{
		if (bl == null) bl = cBottomLeft(this);
		return bl.getVec();
	}
	
	
	@Override
	public VecView getBottomCenter()
	{
		if (bc == null) bc = cBottomCenter(this);
		return bc.getVec();
	}
	
	
	@Override
	public VecView getBottomRight()
	{
		if (br == null) br = cBottomRight(this);
		return br.getVec();
	}
	
	
	@Override
	public final double getWidth()
	{
		return getSize().x();
	}
	
	
	@Override
	public final double getHeight()
	{
		return getSize().y();
	}
	
	
	@Override
	public final double xMin()
	{
		return getOrigin().x();
	}
	
	
	@Override
	public final double xMax()
	{
		return getOrigin().x() + getSize().x();
	}
	
	
	@Override
	public final double yMin()
	{
		return getOrigin().y();
	}
	
	
	@Override
	public final double yMax()
	{
		return getOrigin().y() + getSize().y();
	}
	
	
	@Override
	public final T move(Vec move)
	{
		return move(move.x(), move.y());
	}
	
	
	@Override
	public final T shrink(Vec shrink)
	{
		return shrink(shrink.x(), shrink.y());
	}
	
	
	@Override
	public final T shrink(double x, double y)
	{
		return shrink(x, x, y, y);
	}
	
	
	@Override
	public final T grow(Vec grow)
	{
		return grow(grow.x(), grow.y());
	}
	
	
	@Override
	public final T grow(double x, double y)
	{
		return grow(x, x, y, y);
	}
	
	
	@Override
	public RectView view()
	{
		return new RectProxy(this);
	}
	
	
	@Override
	public RectMutable copy()
	{
		return new MutableRect(this);
	}
	
	
	@Override
	public final boolean contains(Vec point)
	{
		final double x = point.x();
		final double y = point.y();
		
		final double x1 = getOrigin().x();
		final double y1 = getOrigin().y();
		final double x2 = x1 + getSize().x();
		final double y2 = y1 + getSize().y();
		
		return x >= x1 && y >= y1 && x <= x2 && y <= y2;
	}
	
	
	@Override
	public String toString()
	{
		return String.format("Rect { %s - %s }", getOrigin().toString(), getOrigin().add(getSize()));
	}
}
