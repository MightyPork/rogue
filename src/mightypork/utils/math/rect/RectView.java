package mightypork.utils.math.rect;


import mightypork.utils.annotations.FactoryMethod;
import mightypork.utils.math.num.Num;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectView;


/**
 * Immutable rect
 * 
 * @author MightyPork
 */
public abstract class RectView extends RectMathDynamic {
	
	/**
	 * Get a proxy at given rect
	 * 
	 * @param observed observed rect
	 * @return view
	 */
	@FactoryMethod
	public static RectView make(Rect observed)
	{
		return observed.view(); // let the rect handle it
	}
	
	
	/**
	 * Get a rect made of numeric constraints
	 * 
	 * @param width width
	 * @param height height
	 * @return view rect
	 */
	@FactoryMethod
	public static RectView make(Num width, Num height)
	{
		final Vect origin = Vect.ZERO;
		final Vect size = VectView.make(width, height);
		
		return new VectViewRect(origin, size);
	}
	
	
	/**
	 * Get a rect made of numeric constraints
	 * 
	 * @param x x coord
	 * @param y y coord
	 * @param width width
	 * @param height height
	 * @return view rect
	 */
	@FactoryMethod
	public static RectView make(Num x, Num y, Num width, Num height)
	{
		final Vect origin = VectView.make(x, y);
		final Vect size = VectView.make(width, height);
		
		return new VectViewRect(origin, size);
	}
	
	
	/**
	 * Get a rect made of two vect views
	 * 
	 * @param origin origin view
	 * @param size size view
	 * @return view rect
	 */
	@FactoryMethod
	public static RectView make(final Vect origin, final Vect size)
	{
		return new VectViewRect(origin, size);
	}
	
	
	/**
	 * @deprecated No point in taking view of a view
	 */
	@Override
	@Deprecated
	public RectView view()
	{
		return this; // wont change
	}
	
}
