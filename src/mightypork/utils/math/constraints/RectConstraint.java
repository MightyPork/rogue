package mightypork.utils.math.constraints;


import mightypork.utils.math.coord.Rect;


/**
 * Constraint that provides a rect ({@link ConstraintContext})
 * 
 * @author MightyPork
 */
public abstract class RectConstraint extends BaseConstraint implements ConstraintContext {
	
	public RectConstraint(ConstraintContext context) {
		super(context);
	}
	
	
	@Override
	public abstract Rect getRect();
	
}
