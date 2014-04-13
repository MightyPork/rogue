package mightypork.utils.math.num;


import mightypork.utils.annotations.FactoryMethod;
import mightypork.utils.math.constraints.NumBound;


/**
 * Constant number {@link NumBound}
 * 
 * @author MightyPork
 */
public class NumVal extends NumMathStatic<NumVal> {
	
	@SuppressWarnings("hiding")
	public static final NumVal ZERO = NumVal.make(0);
	@SuppressWarnings("hiding")
	public static final NumVal ONE = NumVal.make(1);
	
	
	/**
	 * Make a new constant
	 * 
	 * @param value constant value
	 * @return new constant with the value
	 */
	@FactoryMethod
	public static NumVal make(double value)
	{
		return new NumVal(value);
	}
	
	
	/**
	 * Make a new constant
	 * 
	 * @param copied number whose value to use
	 * @return new constant with the value
	 */
	@FactoryMethod
	public static NumVal make(Num copied)
	{
		return (copied == null ? ZERO : copied.copy());
	}
	
	
	/**
	 * Make a new constant
	 * 
	 * @param copied number whose value to use
	 * @return new constant with the value
	 */
	@FactoryMethod
	public static NumVal make(NumBound copied)
	{
		return new NumVal(eval(copied));
	}
	
	private final double value;
	
	
	NumVal(Num copied) {
		this.value = copied.value();
	}
	
	
	NumVal(double value) {
		this.value = value;
	}
	
	
	@Override
	public double value()
	{
		return value;
	}
	
	
	@Override
	protected NumVal result(double a)
	{
		return new NumVal(a);
	}
	
	
	/**
	 * @deprecated it's useless to copy a constant
	 */
	@Override
	@Deprecated
	public NumVal copy()
	{
		return super.copy();
	}
	
}
