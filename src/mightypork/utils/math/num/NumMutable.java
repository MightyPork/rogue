package mightypork.utils.math.num;


/**
 * Mutable numeric variable
 * 
 * @author MightyPork
 */
public abstract class NumMutable extends Num {
	
	/**
	 * Assign a value
	 * 
	 * @param value new value
	 */
	public abstract void setTo(double value);
	
	
	/**
	 * Assign a value
	 * 
	 * @param value new value
	 */
	public void assign(Num value)
	{
		setTo(eval(value));
	}
	
	
	/**
	 * Set to zero
	 */
	public void reset()
	{
		setTo(0);
	}
	
}
