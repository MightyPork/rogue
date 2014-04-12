package mightypork.utils.math.rect;


import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectVal;


/**
 * Mutable rectangle; operations change it's state.
 * 
 * @author MightyPork
 */
public abstract class RectMutable extends RectMath<RectMutable> {
	
	/**
	 * Create at 0,0 with zero size
	 * 
	 * @return new mutable rect
	 */
	public static RectMutable zero()
	{
		return make(0, 0, 0, 0);
	}
	
	
	/**
	 * Create at 1,1 with zero size
	 * 
	 * @return new mutable rect
	 */
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
	public static RectMutable make(Rect other)
	{
		return make(other.getOrigin(), other.getSize());
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param origin
	 * @param size
	 * @return new mutable rect
	 */
	public static RectMutable make(Vect origin, Vect size)
	{
		return make(origin.x(), origin.y(), size.x(), size.y());
	}
	
	
	/**
	 * Set to other rect's coordinates
	 * 
	 * @param rect other rect
	 * @return this
	 */
	public RectMutable setTo(Rect rect)
	{
		return setTo(rect.getOrigin(), rect.getSize());
	}
	
	
	/**
	 * Set to given size and position
	 * 
	 * @param origin new origin
	 * @param width new width
	 * @param height new height
	 * @return this
	 */
	public RectMutable setTo(Vect origin, double width, double height)
	{
		return setTo(origin, VectVal.make(width, height));
	}
	
	
	/**
	 * Set to given size and position
	 * 
	 * @param x origin.x
	 * @param y origin.y
	 * @param width new width
	 * @param height new height
	 * @return this
	 */
	public RectMutable setTo(double x, double y, double width, double height)
	{
		return setTo(VectVal.make(x, y), VectVal.make(width, height));
	}
	
	
	/**
	 * Set to given size and position
	 * 
	 * @param origin new origin
	 * @param size new size
	 * @return this
	 */
	public RectMutable setTo(Vect origin, Vect size)
	{
		setOrigin(origin);
		setSize(size);
		return this;
	}
	
	
	/**
	 * Set new origin
	 * 
	 * @param origin new origin
	 * @return this
	 */
	public abstract RectMutable setOrigin(Vect origin);
	
	
	/**
	 * Set new size
	 * 
	 * @param size new size
	 * @return this
	 */
	public abstract RectMutable setSize(Vect size);
	
}
