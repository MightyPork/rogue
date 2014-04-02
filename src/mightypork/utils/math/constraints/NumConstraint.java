package mightypork.utils.math.constraints;


/**
 * Constraint that provides size
 * 
 * @author MightyPork
 */
public abstract class NumConstraint extends Constraint {
	
	public NumConstraint(ConstraintContext context) {
		super(context);
	}
	
	
	public abstract double getValue();
	
}
