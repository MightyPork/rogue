package mightypork.rogue.display.constraints;


import mightypork.utils.math.coord.Coord;


public abstract class Constraint implements ConstraintContext {

	protected ConstraintContext context;


	public Constraint(ConstraintContext context) {
		this.context = context;
	}


	public void setContext(ConstraintContext context)
	{
		this.context = context;
	}


	public ConstraintContext getContext()
	{
		return context;
	}


	protected Coord origin()
	{
		return context.getRect().getOrigin();
	}


	protected Coord size()
	{
		return context.getRect().getSize();
	}

}
