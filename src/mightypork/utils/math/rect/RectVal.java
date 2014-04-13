package mightypork.utils.math.rect;


import mightypork.utils.annotations.FactoryMethod;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectVal;


/**
 * Rectangle with constant bounds, that can never change.
 * 
 * @author MightyPork
 */
public class RectVal extends RectView {
	
	/**
	 * Get a proxy at given rect
	 * 
	 * @param observed observed rect
	 * @return view
	 */
	@FactoryMethod
	public static RectVal make(Rect observed)
	{
		return observed.copy(); // let the rect handle it
	}
	
	
	/**
	 * Create at 0,0 with given size
	 * 
	 * @param width
	 * @param height
	 * @return new mutable rect
	 */
	@FactoryMethod
	public static RectVal make(double width, double height)
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
	public static RectVal make(Vect origin, double width, double height)
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
	public static RectVal make(Vect size)
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
	public static RectVal make(double x, double y, double width, double height)
	{
		return new RectVal(x, y, width, height);
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param origin
	 * @param size
	 * @return new mutable rect
	 */
	@FactoryMethod
	public static RectVal make(Vect origin, Vect size)
	{
		return make(origin.x(), origin.y(), size.x(), size.y());
	}
	
	private final VectVal pos;
	private final VectVal size;
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public RectVal(double x, double y, double width, double height) {
		pos = VectVal.make(x, y);
		size = VectVal.make(width, height);
	}
	
	
	@Override
	public RectVal copy()
	{
		// must NOT call RectVal.make, it'd cause infinite recursion.
		return this; // nothing can change.
	}
	
	
	@Override
	public VectVal origin()
	{
		return pos;
	}
	
	
	@Override
	public VectVal size()
	{
		return size;
	}
	
}
