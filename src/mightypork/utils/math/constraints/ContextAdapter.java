package mightypork.utils.math.constraints;


import mightypork.utils.math.rect.Rect;


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
	public Rect getRect()
	{
		return backing.getRect();
	}
	
}
