package mightypork.utils.math.constraints;


/**
 * Numeric constraint
 * 
 * @author MightyPork
 */
public interface NumberConstraint {
	
	public static final NumberConstraint ZERO = new NumberConstraint() {
		
		@Override
		public double getValue()
		{
			return 0;
		}
	};
	
	public static final NumberConstraint ONE = new NumberConstraint() {
		
		@Override
		public double getValue()
		{
			return 0;
		}
	};
	
	
	/**
	 * @return current value
	 */
	double getValue();
	
}
