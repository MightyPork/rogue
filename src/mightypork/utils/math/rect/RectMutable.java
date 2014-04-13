package mightypork.utils.math.rect;


import mightypork.utils.annotations.FactoryMethod;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectVal;


/**
 * Mutable rectangle; operations change it's state.
 * 
 * @author MightyPork
 */
public abstract class RectMutable extends RectView {
	
	/**
	 * Create at 0,0 with zero size
	 * 
	 * @return new mutable rect
	 */
	@FactoryMethod
	public static RectMutable zero()
	{
		return make(0, 0, 0, 0);
	}
	
	
	/**
	 * Create at 1,1 with zero size
	 * 
	 * @return new mutable rect
	 */
	@FactoryMethod
	public static RectMutable one()
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
	public static RectMutable make(double width, double height)
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
	@FactoryMethod
	public static RectMutable make(Vect origin, double width, double height)
	{
		return make(origin, VectVal.make(width, height));
	}
	
	
	/**
	 * Create at 0,0 with given size.
	 * 
	 * @param size
	 * @return new mutable rect
	 */
	@FactoryMethod
	public static RectMutable make(Vect size)
	{
		return make(Vect.ZERO, size);
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
	@FactoryMethod
	public static RectMutable make(double x, double y, double width, double height)
	{
		return make(x, y, width, height);
	}
	
	
	/**
	 * Create as copy of another
	 * 
	 * @param other copied
	 * @return new mutable rect
	 */
	@FactoryMethod
	public static RectMutable make(Rect other)
	{
		return make(other.origin(), other.size());
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param origin
	 * @param size
	 * @return new mutable rect
	 */
	@FactoryMethod
	public static RectMutable make(Vect origin, Vect size)
	{
		return make(origin.x(), origin.y(), size.x(), size.y());
	}
	
	
	/**
	 * Set to other rect's coordinates
	 * 
	 * @param rect other rect
	 */
	public void setTo(Rect rect)
	{
		setTo(rect.origin(), rect.size());
	}
	
	
	/**
	 * Set to given size and position
	 * 
	 * @param origin new origin
	 * @param width new width
	 * @param height new height
	 */
	public void setTo(Vect origin, double width, double height)
	{
		setTo(origin, VectVal.make(width, height));
	}
	
	
	/**
	 * Set to given size and position
	 * 
	 * @param x origin.x
	 * @param y origin.y
	 * @param width new width
	 * @param height new height
	 */
	public void setTo(double x, double y, double width, double height)
	{
		setTo(VectVal.make(x, y), VectVal.make(width, height));
	}
	
	
	/**
	 * Set to given size and position
	 * 
	 * @param origin new origin
	 * @param size new size
	 */
	public void setTo(Vect origin, Vect size)
	{
		setOrigin(origin);
		setSize(size);
	}
	
	
	/**
	 * Set to zero
	 */
	public void reset()
	{
		setTo(Vect.ZERO, Vect.ZERO);
	}
	
	/**
	 * Set new origin
	 * 
	 * @param origin new origin
	 */
	public abstract void setOrigin(Vect origin);
	
	
	/**
	 * Set new size
	 * 
	 * @param size new size
	 */
	public abstract void setSize(Vect size);
	
}
