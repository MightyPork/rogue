package mightypork.utils.math.num;


import mightypork.utils.annotations.FactoryMethod;
import mightypork.utils.math.constraints.NumBound;


/**
 * Mutable numeric variable
 * 
 * @author MightyPork
 */
public abstract class NumMutable extends NumMathStatic<NumMutable> {
	
	/**
	 * Make a new mutable number initialized as zero (0)
	 * 
	 * @return new mutable number
	 */
	@FactoryMethod
	public static NumMutable zero()
	{
		return make(0);
	}
	
	
	/**
	 * Make a new mutable number initialized as one (1)
	 * 
	 * @return new mutable number
	 */
	@FactoryMethod
	public static NumMutable one()
	{
		return make(1);
	}
	
	
	
	/**
	 * Make as copy of another
	 * 
	 * @param value copied number
	 * @return new mutable number with the same value
	 */
	@FactoryMethod
	public static NumMutable make(double value)
	{
		return new NumMutableImpl(value);
	}
	
	/**
	 * Make as copy of another
	 * 
	 * @param copied copied number
	 * @return new mutable number with the same value
	 */
	@FactoryMethod
	public static NumMutable make(Num copied)
	{
		return new NumMutableImpl(eval(copied));
	}
	
	/**
	 * Make as copy of another
	 * 
	 * @param copied copied number
	 * @return new mutable number with the same value
	 */
	@FactoryMethod
	public static NumMutable make(NumBound copied)
	{
		return new NumMutableImpl(eval(copied));
	}
	
	
	@Override
	protected NumMutable result(double a)
	{
		return setTo(a);
	}
	
	
	/**
	 * Assign a value
	 * 
	 * @param value new value
	 * @return this
	 */
	public NumMutable set(Num value)
	{
		return setTo(eval(value));
	}
	
	
	/**
	 * Assign a value
	 * 
	 * @param value new value
	 * @return this
	 */
	public abstract NumMutable setTo(double value);
	
}
