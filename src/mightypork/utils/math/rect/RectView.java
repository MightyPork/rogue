package mightypork.utils.math.rect;


import mightypork.utils.annotations.FactoryMethod;


/**
 * Immutable rect
 * 
 * @author MightyPork
 */
public abstract class RectView extends RectMathDynamic {
	
	@SuppressWarnings("hiding")
	public static final RectView ZERO = Rect.ZERO.view();
	@SuppressWarnings("hiding")
	public static final RectView ONE = Rect.ONE.view();
	
	
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
	 * @deprecated No point in taking view of a view
	 */
	@Override
	@Deprecated
	public RectView view()
	{
		// must NOT call RectView.make, it'd cause infinite recursion.
		return this; // wont change
	}
	
}
