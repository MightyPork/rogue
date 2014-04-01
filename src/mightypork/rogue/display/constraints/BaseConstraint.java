package mightypork.rogue.display.constraints;


import mightypork.utils.math.coord.Coord;


/**
 * A constraint based on a given {@link RenderContext}
 * 
 * @author MightyPork
 */
public abstract class BaseConstraint implements WithContext {
	
	protected RenderContext context = null;
	
	
	public BaseConstraint(RenderContext context) {
		this.context = context;
	}
	
	
	@Override
	public void setContext(RenderContext context)
	{
		this.context = context;
	}
	
	
	/**
	 * @return context
	 */
	public RenderContext getContext()
	{
		return context;
	}
	
	
	/**
	 * @return context rect origin
	 */
	protected Coord getOrigin()
	{
		if (context == null) return Coord.zero();
		
		return context.getRect().getOrigin();
	}
	
	
	/**
	 * @return context rect size
	 */
	protected Coord getSize()
	{
		if (context == null) return Coord.zero();
		
		return context.getRect().getSize();
	}
}
