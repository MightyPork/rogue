package mightypork.utils.math.rect;


import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectVal;


/**
 * Abstract {@link Rect}, implementing all but the data getters
 * 
 * @author MightyPork
 */
public abstract class AbstractRect implements Rect {
	
	private RectProxy proxy;
	
	
	@Override
	public final RectView getRect()
	{
		return this.getView();
	}
	
	
	@Override
	public final VectVal topLeft()
	{
		return origin();
	}
	
	
	@Override
	public final VectVal topCenter()
	{
		return origin().add(size().x() / 2, 0);
	}
	
	
	@Override
	public final VectVal topRight()
	{
		return origin().add(size().x(), 0);
	}
	
	
	@Override
	public final VectVal centerLeft()
	{
		return origin().add(0, size().y() / 2);
	}
	
	
	@Override
	public final VectVal center()
	{
		return origin().add(size().half());
	}
	
	
	@Override
	public final VectVal centerRight()
	{
		return origin().add(size().x(), size().y() / 2);
	}
	
	
	@Override
	public final VectVal bottomLeft()
	{
		return origin().add(0, size().y());
	}
	
	
	@Override
	public final VectVal bottomCenter()
	{
		return origin().add(size().x() / 2, size().y());
	}
	
	
	@Override
	public final VectVal bottomRight()
	{
		return origin().add(size().x(), size().y());
	}
	
	
	@Override
	public final double width()
	{
		return size().x();
	}
	
	
	@Override
	public final double height()
	{
		return size().y();
	}
	
	
	@Override
	public final double getLeft()
	{
		return origin().x();
	}
	
	
	@Override
	public final double right()
	{
		return origin().x() + size().x();
	}
	
	
	@Override
	public final double top()
	{
		return origin().y();
	}
	
	
	@Override
	public final double bottom()
	{
		return origin().y() + size().y();
	}
	
	
	@Override
	public RectProxy getView()
	{
		if (proxy == null) proxy = new RectProxy(this);
		
		return proxy;
	}
	
	
	@Override
	public RectVal getValue()
	{
		return RectVal.make(origin(), size());
	}
	
	
	@Override
	public final boolean contains(Vect point)
	{
		final double x = point.x();
		final double y = point.y();
		
		final double x1 = origin().x();
		final double y1 = origin().y();
		final double x2 = x1 + size().x();
		final double y2 = y1 + size().y();
		
		return x >= x1 && y >= y1 && x <= x2 && y <= y2;
	}
	
	
	@Override
	public String toString()
	{
		return String.format("Rect { %s - %s }", topLeft(), bottomRight());
	}
	
}
