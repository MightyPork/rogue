package mightypork.utils.math.num;


/**
 * Mutable numeric variable.
 * 
 * @author MightyPork
 */
public class NumVar extends NumMutable {
	
	private double value;
	
	
	public NumVar(Num value) {
		this(value.value());
	}
	
	
	public NumVar(double value) {
		this.value = value;
	}
	
	
	@Override
	public double value()
	{
		return value;
	}
	
	
	@Override
	public void setTo(double value)
	{
		this.value = value;
	}
	
}
