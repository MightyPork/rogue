package mightypork.utils.math.rect;


import mightypork.utils.math.num.NumVal;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectVal;


public abstract class RectMathStatic<R extends RectMathStatic<R>> extends RectMath<R> {
	
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
	public R move(double x, double y) {
		return result(p_origin.add(x,y), p_size);
	}
	
	
	@Override
	public R shrink(double left, double right, double top, double bottom) 
	{
		return result(p_origin.add(left, top), p_size.sub(left + right, top + bottom));
	}
	
	
	@Override
	public R grow(double left, double right, double top, double bottom)
	{		
		return result(p_origin.sub(left, top), p_size.add(left + right, top + bottom));
	}
	
	
	@Override
	public R centerTo(final Vect point)
	{		
		return result(p_origin.sub(p_size.half()), p_size);
	}
	
	
	@Override
	public R round()
	{		
		return result(p_origin.round(), p_size.round());
	}
	
	
	protected abstract R result(Vect newOrigin, Vect newSize);
	
	
	@Override
	public NumVal x()
	{
		return p_x.copy();
	}
	
	
	@Override
	public NumVal y()
	{
		return p_y.copy();
	}
	
	
	@Override
	public NumVal width()
	{
		return p_width.copy();
	}
	
	
	@Override
	public NumVal height()
	{
		return p_height.copy();
	}
	
	
	@Override
	public NumVal left()
	{
		return p_left.copy();
	}
	
	
	@Override
	public NumVal right()
	{
		return p_right.copy();
	}
	
	
	@Override
	public NumVal top()
	{
		return p_top.copy();
	}
	
	
	@Override
	public NumVal bottom()
	{
		return p_bottom.copy();
	}
	
	
	@Override
	public VectVal topLeft()
	{
		return p_tl.copy();
	}
	
	
	@Override
	public VectVal topCenter()
	{
		return p_tc.copy();
	}
	
	
	@Override
	public VectVal topRight()
	{
		return p_tr.copy();
	}
	
	
	@Override
	public VectVal centerLeft()
	{
		return p_cl.copy();
	}
	
	
	@Override
	public VectVal center()
	{
		return p_cc.copy();
	}
	
	
	@Override
	public VectVal centerRight()
	{
		return p_cr.copy();
	}
	
	
	@Override
	public VectVal bottomLeft()
	{
		return p_bl.copy();
	}
	
	
	@Override
	public VectVal bottomCenter()
	{
		return p_bc.copy();
	}
	
	
	@Override
	public VectVal bottomRight()
	{
		return p_br.copy();
	}
}
