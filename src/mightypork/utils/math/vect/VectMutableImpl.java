package mightypork.utils.math.vect;


/**
 * Mutable coordinate.<br>
 * All Vec methods (except copy) alter data values and return this instance.
 * 
 * @author MightyPork
 */
class VectMutableImpl extends VectMutable {
	
	private double x, y, z;
	
	
	/**
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param z Z coordinate
	 */
	public VectMutableImpl(double x, double y, double z) {
		super();
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
	public VectMutable result(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		
		return this;
	}
	
	
	@Override
	public VectMutable setX(double x)
	{
		this.x = x;
		return this;
	}
	
	
	@Override
	public VectMutable setY(double y)
	{
		this.y = y;
		return this;
	}
	
	
	@Override
	public VectMutable setZ(double z)
	{
		this.z = z;
		return this;
	}
}
