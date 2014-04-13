package mightypork.utils.math.rect;


import mightypork.utils.annotations.FactoryMethod;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectVal;


/**
 * Rectangle with constant bounds, that can never change.
 * 
 * @author MightyPork
 */
public class RectVal extends RectMathStatic<RectVal> {
	
	@SuppressWarnings("hiding")
	public static final RectVal ZERO = Rect.ZERO.copy();
	@SuppressWarnings("hiding")
	public static final RectVal ONE = Rect.ONE.copy();
	
	
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
		this.pos = VectVal.make(x, y);
		this.size = VectVal.make(width, height);
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param origin
	 * @param size
	 */
	public RectVal(Vect origin, Vect size) {
		this.pos = origin.copy();
		this.size = size.copy();
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param another other coord
	 */
	public RectVal(Rect another) {
		this.pos = another.origin().copy();
		this.size = another.size().copy();
	}
	
	
	/**
	 * @deprecated it's useless to copy a constant
	 */
	@Override
	@Deprecated
	public RectVal copy()
	{
		return this; // already constant
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
	
	
	@Override
	protected RectVal result(Vect newOrigin, Vect newSize)
	{
		return make(newOrigin, newSize);
	}
	
}
