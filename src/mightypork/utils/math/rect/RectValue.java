package mightypork.utils.math.rect;


import mightypork.utils.math.coord.Vec;
import mightypork.utils.math.coord.VecView;


/**
 * Immutable rect
 * 
 * @author MightyPork
 */
public abstract class RectValue extends RectMath<RectValue> {
	
	/**
	 * Create at 0,0 with zero size
	 * 
	 * @return new mutable rect
	 */
	public static RectValue zero()
	{
		return make(0, 0, 0, 0);
	}
	
	
	/**
	 * Create at 1,1 with zero size
	 * 
	 * @return new mutable rect
	 */
	public static RectValue one()
	{
		return make(0, 0, 1, 1);
	}
	
	
	/**
	 * Create at 0,0 with given size
	 * 
	 * @param width
	 * @param height
	 * @return new mutable rect
	 */
	public static RectValue make(double width, double height)
	{
		return make(0, 0, width, height);
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param origin
	 * @param width
	 * @param height
	 * @return new mutable rect
	 */
	public static RectValue make(Vec origin, double width, double height)
	{
		return make(origin, VecView.make(width, height));
	}
	
	
	/**
	 * Create at 0,0 with given size.
	 * 
	 * @param size
	 * @return new mutable rect
	 */
	public static RectValue make(Vec size)
	{
		return make(VecView.zero(), size);
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return new mutable rect
	 */
	public static RectValue make(double x, double y, double width, double height)
	{
		return new ConstRect(x, y, width, height);
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param origin
	 * @param size
	 * @return new mutable rect
	 */
	public static RectValue make(Vec origin, Vec size)
	{
		return make(origin.x(), origin.y(), size.x(), size.y());
	}
	
	
	@Override
	public RectValue move(double x, double y)
	{
		return RectValue.make(getOrigin().add(x, y), getSize());
	}
	
	
	@Override
	public RectValue shrink(double left, double right, double top, double bottom)
	{
		return RectValue.make(getOrigin().add(left, top), getSize().sub(left + right, top + bottom));
	}
	
	
	@Override
	public RectValue grow(double left, double right, double top, double bottom)
	{
		return RectValue.make(getOrigin().sub(left, top), getSize().add(left + right, top + bottom));
	}
	
	
	@Override
	public RectValue round()
	{
		return RectValue.make(getOrigin().round(), getSize().round());
	}
	
	
	@Override
	public RectValue view()
	{
		return this;
	}
	
}
