package mightypork.utils.math.vect;


/**
 * Coordinate with immutable numeric values.<br>
 * This coordinate is guaranteed to never change, as opposed to view.
 * 
 * @author MightyPork
 */
public final class VectVal extends VectView {
	
	/**
	 * Make a constant vector
	 * 
	 * @param value source vector
	 * @return new constant vec
	 */
	public static VectVal make(Vect value)
	{
		return value.copy();
	}
	
	
	/**
	 * Make a constant vector
	 * 
	 * @param x X value
	 * @param y Y value
	 * @return new constant vec
	 */
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
	
	
	@Override
	public VectVal copy()
	{
		return this; // it's constant already
	}
	
}
