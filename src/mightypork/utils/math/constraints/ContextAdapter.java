package mightypork.utils.math.constraints;


import mightypork.utils.math.rect.RectView;


/**
 * Basic pluggable context implementation
 * 
 * @author MightyPork
 */
public abstract class ContextAdapter implements PluggableRect {
	
	private RectBound backing = null;
	
	
	@Override
	public void setContext(RectBound rect)
	{
		this.backing = rect;
	}
	
	
	@Override
	public RectView getRect()
	{
		return backing.getRect();
	}
	
}
