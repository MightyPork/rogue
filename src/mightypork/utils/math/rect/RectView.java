package mightypork.utils.math.rect;


import mightypork.utils.math.coord.VecView;


/**
 * Immutable rect
 * 
 * @author MightyPork
 */
public abstract class RectView extends RectImpl<RectView> {
	
	protected RectView result(VecView origin, VecView size)
	{
		return new FixedRect(origin, size);
	}
	
	
	@Override
	public RectView move(double x, double y)
	{
		return result(getOrigin().add(x, y), getSize());
	}
	
	
	@Override
	public RectView shrink(double left, double right, double top, double bottom)
	{
		return result(getOrigin().add(left, top), getSize().sub(left + right, top + bottom));
	}
	
	
	@Override
	public RectView grow(double left, double right, double top, double bottom)
	{
		return result(getOrigin().sub(left, top), getSize().add(left + right, top + bottom));
	}
	
	
	@Override
	public RectView round()
	{
		return result(getOrigin().round(), getSize().round());
	}
	
	
	@Override
	public MutableRect copy()
	{
		return new MutableRect(this);
	}
	
	
	@Override
	public RectView view()
	{
		return this;
	}
	
	
	@Override
	public abstract VecView getOrigin();
	
	
	@Override
	public abstract VecView getSize();
	
}
