package mightypork.utils.math.constraints;


/**
 * Numeric constraint
 * 
 * @author MightyPork
 */
public interface NumBound {
	
	public static final NumBound ZERO = new NumberConst(0);
	public static final NumBound ONE = new NumberConst(1);
	
	
	/**
	 * @return current value
	 */
	double getValue();
	
}
