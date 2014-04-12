package mightypork.utils.math.rect;


import static mightypork.utils.math.constraints.Constraints.*;
import mightypork.utils.math.constraints.VecConstraint;
import mightypork.utils.math.coord.Vec;
import mightypork.utils.math.coord.VecView;


/**
 * Abstract {@link Rect}, implementing all but the data getters
 * 
 * @author MightyPork
 */
public abstract class AbstractRect implements Rect {
	
	private VecConstraint tl;
	private VecConstraint tc;
	private VecConstraint tr;
	private VecConstraint cl;
	private VecConstraint c;
	private VecConstraint cr;
	private VecConstraint bl;
	private VecConstraint bc;
	private VecConstraint br;
	
	
	@Override
	public final RectValue getRect()
	{
		return this.view();
	}
	
	
	@Override
	public final VecView getTopLeft()
	{
		// lazy init
		if (tl == null) tl = cTopLeft(this);
		return tl.getVec();
	}
	
	
	@Override
	public final VecView getTopCenter()
	{
		// lazy init
		if (tc == null) tc = cTopCenter(this);
		return tc.getVec();
	}
	
	
	@Override
	public final VecView getTopRight()
	{
		// lazy init
		if (tr == null) tr = cTopRight(this);
		return tr.getVec();
	}
	
	
	@Override
	public final VecView getCenterLeft()
	{
		// lazy init
		if (cl == null) cl = cCenterLeft(this);
		return cl.getVec();
	}
	
	
	@Override
	public final VecView getCenter()
	{
		// lazy init
		if (c == null) c = cCenter(this);
		return c.getVec();
	}
	
	
	@Override
	public final VecView getCenterRight()
	{
		// lazy init
		if (cr == null) cr = cCenterRight(this);
		return cr.getVec();
	}
	
	
	@Override
	public final VecView getBottomLeft()
	{
		// lazy init
		if (bl == null) bl = cBottomLeft(this);
		return bl.getVec();
	}
	
	
	@Override
	public final VecView getBottomCenter()
	{
		// lazy init
		if (bc == null) bc = cBottomCenter(this);
		return bc.getVec();
	}
	
	
	@Override
	public final VecView getBottomRight()
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
	public RectValue view()
	{
		return new RectProxy(this);
	}
	
	
	@Override
	public final RectMutable mutable()
	{
		return RectMutable.make(this);
	}
	
	
	@Override
	public RectValue value()
	{
		return RectValue.make(getOrigin(), getSize());
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
