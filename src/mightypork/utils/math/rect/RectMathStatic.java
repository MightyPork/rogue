package mightypork.utils.math.rect;


import mightypork.utils.math.num.NumVal;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectVal;
import mightypork.utils.math.vect.VectView;


abstract class RectMathStatic<R extends RectMathStatic<R>> extends RectMath<R> {
	
	@Override
	public abstract VectVal origin();
	
	
	@Override
	public abstract VectVal size();
	
	
	@Override
	public R move(Vect move)
	{
		return move(move.x(), move.y());
	}
	
	
	@Override
	public R move(double x, double y)
	{
		return result(origin().add(x, y), size());
	}
	
	
	@Override
	public R shrink(double left, double right, double top, double bottom)
	{
		return result(origin().add(left, top), size().sub(left + right, top + bottom));
		
	}
	
	
	@Override
	public R grow(double left, double right, double top, double bottom)
	{
		return result(origin().sub(left, top), size().add(left + right, top + bottom));
	}
	
	
	@Override
	public R centerTo(final Vect point)
	{
		final VectView s = size().view();
		final VectView o = origin().view();
		
		return result(o.sub(s.half()), s);
	}
	
	
	@Override
	public R round()
	{
		final VectView s = size().view();
		final VectView o = origin().view();
		
		return result(o.round(), s.round());
	}
	
	
	protected abstract R result(Vect newOrigin, Vect newSize);
	
	
	@Override
	public NumVal x()
	{
		return origin().xn();
	}
	
	
	@Override
	public NumVal y()
	{
		return origin().yn();
	}
	
	
	@Override
	public NumVal width()
	{
		return size().xn();
	}
	
	
	@Override
	public NumVal height()
	{
		return size().yn();
	}
	
	
	@Override
	public NumVal left()
	{
		return origin().xn();
	}
	
	
	@Override
	public NumVal right()
	{
		return origin().xn().add(size().xn());
	}
	
	
	@Override
	public NumVal top()
	{
		return origin().yn();
	}
	
	
	@Override
	public NumVal bottom()
	{
		return origin().yn().add(size().yn());
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
		return origin().add(size().view().half());
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
		return origin().add(size().view());
	}
}
