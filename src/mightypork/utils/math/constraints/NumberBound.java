package mightypork.utils.math.constraints;


/**
 * Numeric constraint
 * 
 * @author MightyPork
 */
public interface NumberBound {
	
	public static final NumberBound ZERO = new NumberConst(0);
	public static final NumberBound ONE = new NumberConst(1);
	
	
	/**
	 * @return current value
	 */
	double getValue();
	
}
