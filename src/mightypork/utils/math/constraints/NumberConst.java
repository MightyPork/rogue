package mightypork.utils.math.constraints;


/**
 * Constant number {@link NumBound}
 * 
 * @author MightyPork
 */
public class NumberConst implements NumBound {
	
	private final double value;
	
	
	public NumberConst(double value) {
		this.value = value;
	}
	
	
	@Override
	public double getValue()
	{
		return value;
	}
	
}
