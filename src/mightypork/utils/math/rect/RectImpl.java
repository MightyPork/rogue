package mightypork.utils.math.rect;


import mightypork.utils.math.coord.Vec;
import mightypork.utils.math.coord.VecView;


public abstract class RectImpl<T extends Rect> implements RectMath<T> {
	
	@Override
	public final VecView getCenter()
	{
		return getOrigin().add(getSize().half());
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
		return shrink(x, y, x, y);
	}
	
	
	@Override
	public final T grow(Vec grow)
	{
		return grow(grow.x(), grow.y());
	}
	
	
	@Override
	public final T grow(double x, double y)
	{
		return grow(x, y, x, y);
	}
	
	
	@Override
	public RectView view()
	{
		return new RectProxy(this);
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
		return String.format("Rect[ %s - %s ]", getOrigin().toString(), getOrigin().add(getSize()));
	}
}
