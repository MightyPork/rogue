package mightypork.gamecore.util.math.constraints.num.mutable;


import mightypork.gamecore.util.math.constraints.num.Num;


/**
 * Mutable numeric variable
 * 
 * @author Ondřej Hruška
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
		setTo(value.value());
	}
	
	
	/**
	 * Set to zero
	 */
	public void reset()
	{
		setTo(0);
	}
	
}
