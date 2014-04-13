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
	public RectView getRect()
	{
		return this.view();
	}
	
	
	@Override
	public VectVal topLeft()
	{
		return origin();
	}
	
	
	@Override
	public VectVal topCenter()
	{
		return origin().add(size().x() / 2, 0);
	}
	
	
	@Override
	public VectVal topRight()
	{
		return origin().add(size().x(), 0);
	}
	
	
	@Override
	public VectVal centerLeft()
	{
		return origin().add(0, size().y() / 2);
	}
	
	
	@Override
	public VectVal center()
	{
		return origin().add(size().half());
	}
	
	
	@Override
	public VectVal centerRight()
	{
		return origin().add(size().x(), size().y() / 2);
	}
	
	
	@Override
	public VectVal bottomLeft()
	{
		return origin().add(0, size().y());
	}
	
	
	@Override
	public VectVal bottomCenter()
	{
		return origin().add(size().x() / 2, size().y());
	}
	
	
	@Override
	public VectVal bottomRight()
	{
		return origin().add(size().x(), size().y());
	}
	
	
	@Override
	public double x()
	{
		return origin().x();
	}
	
	
	@Override
	public double y()
	{
		return origin().y();
	}
	
	
	@Override
	public double width()
	{
		return size().x();
	}
	
	
	@Override
	public double height()
	{
		return size().y();
	}
	
	
	@Override
	public double left()
	{
		return origin().x();
	}
	
	
	@Override
	public double right()
	{
		return origin().x() + size().x();
	}
	
	
	@Override
	public double top()
	{
		return origin().y();
	}
	
	
	@Override
	public double bottom()
	{
		return origin().y() + size().y();
	}
	
	
	@Override
	public RectView view()
	{
		// must NOT call VectView.make, it'd cause infinite recursion.
		
		if (proxy == null) proxy = new RectProxy(this);
		
		return proxy;
	}
	
	
	@Override
	public RectVal copy()
	{
		// must NOT call RectVal.make, it'd cause infinite recursion.
		return new RectVal(x(), y(), width(), height());
	}
	
	
	@Override
	public boolean contains(Vect point)
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
