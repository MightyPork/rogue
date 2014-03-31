package mightypork.rogue.display.constraints;


import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.coord.Rect;


public abstract class Constraint implements Bounding {

	protected Bounding context;


	public Constraint(Bounding context) {
		this.context = context;
	}


	/**
	 * Assign a context
	 * 
	 * @param context
	 */
	public void setContext(Bounding context)
	{
		this.context = context;
	}


	/**
	 * @return context
	 */
	public Bounding getContext()
	{
		return context;
	}


	/**
	 * @return context rect origin
	 */
	protected Coord origin()
	{
		return context.getRect().getOrigin();
	}


	/**
	 * @return context rect size
	 */
	protected Coord size()
	{
		return context.getRect().getSize();
	}


	@Override
	public abstract Rect getRect();

}
