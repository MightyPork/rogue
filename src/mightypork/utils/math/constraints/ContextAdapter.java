package mightypork.utils.math.constraints;


import mightypork.utils.math.rect.RectValue;


/**
 * Basic pluggable context implementation
 * 
 * @author MightyPork
 */
public abstract class ContextAdapter implements PluggableRect {
	
	private RectConstraint backing = null;
	
	
	@Override
	public void setContext(RectConstraint rect)
	{
		this.backing = rect;
	}
	
	
	@Override
	public RectValue getRect()
	{
		return backing.getRect();
	}
	
}
