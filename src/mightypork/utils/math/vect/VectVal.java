package mightypork.utils.math.vect;


import mightypork.utils.annotations.FactoryMethod;


/**
 * Coordinate with immutable numeric values.<br>
 * This coordinate is guaranteed to never change, as opposed to view.
 * 
 * @author MightyPork
 */
public final class VectVal extends VectMathStatic<VectVal> {
	
	@SuppressWarnings("hiding")
	public static final VectVal ZERO = new VectVal(0, 0, 0);
	@SuppressWarnings("hiding")
	public static final VectVal ONE = new VectVal(1, 1, 1);
	
	
	/**
	 * Make a constant vector
	 * 
	 * @param value source vector
	 * @return new constant vec
	 */
	@FactoryMethod
	public static VectVal make(Vect value)
	{
		return value.copy(); // let the vect handle it
	}
	
	
	/**
	 * Make a constant vector
	 * 
	 * @param x X value
	 * @param y Y value
	 * @return new constant vec
	 */
	@FactoryMethod
	public static VectVal make(double x, double y)
	{
		return make(x, y, 0);
	}
	
	
	/**
	 * Make a constant vector
	 * 
	 * @param x X value
	 * @param y Y value
	 * @param z Z value
	 * @return new constant vector
	 */
	@FactoryMethod
	public static VectVal make(double x, double y, double z)
	{
		return new VectVal(x, y, z);
	}
	
	private final double x, y, z;
	
	
	public VectVal(Vect other) {
		this(other.x(), other.y(), other.z());
	}
	
	
	public VectVal(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	@Override
	public double x()
	{
		return x;
	}
	
	
	@Override
	public double y()
	{
		return y;
	}
	
	
	@Override
	public double z()
	{
		return z;
	}
	
	
	/**
	 * @deprecated it's useless to copy a constant
	 */
	@Override
	@Deprecated
	public VectVal copy()
	{
		return this; // it's constant already
	}
	
	
	@Override
	public VectVal result(double x, double y, double z)
	{
		return new VectVal(x, y, z);
	}
	
}
