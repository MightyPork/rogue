package mightypork.utils.math.rect;


import static mightypork.utils.math.constraints.Constraints.*;
import mightypork.utils.math.constraints.VectConstraint;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectVal;
import mightypork.utils.math.vect.VectView;


/**
 * Abstract {@link Rect}, implementing all but the data getters
 * 
 * @author MightyPork
 */
public abstract class AbstractRect implements Rect {
	
	private RectProxy proxy;
	private VectConstraint tl;
	private VectConstraint tc;
	private VectConstraint tr;
	private VectConstraint cl;
	private VectConstraint c;
	private VectConstraint cr;
	private VectConstraint bl;
	private VectConstraint bc;
	private VectConstraint br;
	
	
	@Override
	public final RectView getRect()
	{
		return this.view();
	}
	
	
	@Override
	public final VectVal getTopLeft()
	{
		// lazy init
		if (tl == null) tl = cTopLeft(this);
		return tl.getVec();
	}
	
	
	@Override
	public final VectVal getTopCenter()
	{
		// lazy init
		if (tc == null) tc = cTopCenter(this);
		return tc.getVec();
	}
	
	
	@Override
	public final VectVal getTopRight()
	{
		// lazy init
		if (tr == null) tr = cTopRight(this);
		return tr.getVec();
	}
	
	
	@Override
	public final VectVal getCenterLeft()
	{
		// lazy init
		if (cl == null) cl = cCenterLeft(this);
		return cl.getVec();
	}
	
	
	@Override
	public final VectVal getCenter()
	{
		// lazy init
		if (c == null) c = cCenter(this);
		return c.getVec();
	}
	
	
	@Override
	public final VectVal getCenterRight()
	{
		// lazy init
		if (cr == null) cr = cCenterRight(this);
		return cr.getVec();
	}
	
	
	@Override
	public final VectVal getBottomLeft()
	{
		// lazy init
		if (bl == null) bl = cBottomLeft(this);
		return bl.getVec();
	}
	
	
	@Override
	public final VectVal getBottomCenter()
	{
		// lazy init
		if (bc == null) bc = cBottomCenter(this);
		return bc.getVec();
	}
	
	
	@Override
	public final VectVal getBottomRight()
	{
		// lazy init
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
	public RectProxy view()
	{
		if (proxy == null) proxy = new RectProxy(this);
		
		return proxy;
	}
	
	
	@Override
	public RectMutable mutable()
	{
		return RectMutable.make(this);
	}
	
	
	@Override
	public RectVal value()
	{
		return RectVal.make(getOrigin(), getSize());
	}
	
	
	@Override
	public final boolean contains(Vect point)
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
		return String.format("Rect { %s - %s }", getTopLeft(), getBottomRight());
	}
	
}
