package mightypork.utils.math.constraints;


import mightypork.utils.math.coord.Rect;


public class ContextAdapter implements PluggableContext {
	
	private RectEvaluable backing = null;
	
	
	@Override
	public void setContext(RectEvaluable rect)
	{
		this.backing = rect;
	}
	
	
	@Override
	public Rect getRect()
	{
		return backing.getRect();
	}
	
}
