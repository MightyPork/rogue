package mightypork.utils.math.constraints;


/**
 * Constant number {@link NumberConstraint}
 * 
 * @author MightyPork
 */
public class FixedNumberConstraint implements NumberConstraint {
	
	private final double value;
	
	
	public FixedNumberConstraint(double value) {
		this.value = value;
	}
	
	
	@Override
	public double getValue()
	{
		return value;
	}
	
}
