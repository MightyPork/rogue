package mightypork.utils.math.constraints;


/**
 * Numeric constraint
 * 
 * @author MightyPork
 */
public interface NumberConstraint {
	
	public static final NumberConstraint ZERO = new FixedNumberConstraint(0);
	public static final NumberConstraint ONE = new FixedNumberConstraint(1);
	
	
	/**
	 * @return current value
	 */
	double getValue();
	
}
