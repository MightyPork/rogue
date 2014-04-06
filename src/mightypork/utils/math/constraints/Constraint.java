package mightypork.utils.math.constraints;


import mightypork.utils.math.coord.Coord;


/**
 * A constraint based on a given {@link ConstraintContext}
 * 
 * @author MightyPork
 */
public abstract class Constraint implements SettableContext {
	
	private ConstraintContext context = null;
	
	
	public Constraint(ConstraintContext context) {
		this.context = context;
	}
	
	
	@Override
	public void setContext(ConstraintContext context)
	{
		this.context = context;
	}
	
	
	/**
	 * @return the context
	 */
	public ConstraintContext getContext()
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
		
		return context.getRect().size();
	}
}
