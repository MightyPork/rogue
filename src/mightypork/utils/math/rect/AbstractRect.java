package mightypork.utils.math.rect;


/**
 * Abstract {@link Rect}, implementing all but the data getters
 * 
 * @author MightyPork
 */
abstract class AbstractRect implements Rect {
	
	private RectProxy proxy;
	
	
	@Override
	public RectView getRect()
	{
		return this.view();
	}
	
	
	@Override
	public RectView view()
	{
		// must NOT call RectView.make, it'd cause infinite recursion.		
		if (proxy == null) proxy = new RectProxy(this);
		
		return proxy;
	}
	
	
	@Override
	public RectVal copy()
	{
		// must NOT call RectVal.make, it'd cause infinite recursion.
		return new RectVal(this);
	}
	
	
	@Override
	public String toString()
	{
		return String.format("Rect { %s - %s }", origin(), origin().copy().add(size()));
	}
	
}
