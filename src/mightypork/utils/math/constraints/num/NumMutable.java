package mightypork.utils.math.constraints.num;


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
	public void setTo(Num value)
	{
		setTo(value);
	}
	
	
	/**
	 * Set to zero
	 */
	public void reset()
	{
		setTo(0);
	}
	
}
