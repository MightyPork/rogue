package mightypork.utils.math.constraints;


import mightypork.utils.math.rect.RectView;


/**
 * Basic pluggable context implementation
 * 
 * @author MightyPork
 */
public abstract class ContextAdapter implements PluggableContext {
	
	private RectConstraint backing = null;
	
	
	@Override
	public void setContext(RectConstraint rect)
	{
		this.backing = rect;
	}
	
	
	@Override
	public RectView getRect()
	{
		return backing.getRect();
	}
	
}
