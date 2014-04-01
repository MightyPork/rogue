package mightypork.rogue.display.constraints;


import mightypork.utils.math.coord.Rect;


/**
 * Constraint that provides a rect (RenderContext)
 * 
 * @author MightyPork
 */
public abstract class RectConstraint extends BaseConstraint implements RenderContext {
	
	public RectConstraint(RenderContext context) {
		super(context);
	}
	
	
	@Override
	public abstract Rect getRect();
	
}
