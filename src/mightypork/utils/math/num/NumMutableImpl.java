package mightypork.utils.math.num;


/**
 * Mutable numeric variable.
 * 
 * @author MightyPork
 */
class NumMutableImpl extends NumMutable {
	
	private double value;
	
	
	public NumMutableImpl(Num value) {
		this.value = eval(value);
	}
	
	
	public NumMutableImpl(double value) {
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
