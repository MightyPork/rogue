package mightypork.utils.math.constraints;


/**
 * Constant number {@link NumberBound}
 * 
 * @author MightyPork
 */
public class NumberConst implements NumberBound {
	
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
