package mightypork.rogue.display.constraints;


/**
 * Constraint that provides size
 * 
 * @author MightyPork
 */
public abstract class NumConstraint extends BaseConstraint {
	
	public NumConstraint(RenderContext context) {
		super(context);
	}
	
	
	public abstract double getValue();
	
}
