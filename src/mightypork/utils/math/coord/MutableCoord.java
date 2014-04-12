package mightypork.utils.math.coord;


/**
 * Mutable coordinate.<br>
 * All Vec methods (except copy) alter data values and return this instance.
 * 
 * @author MightyPork
 */
public class MutableCoord extends VecMutableImpl {
	
	private double x, y, z;
	
	
	/**
	 * Zero coord
	 */
	public MutableCoord() {
		this(0, 0, 0);
	}
	
	
	/**
	 * @param copied other coord to vopy
	 */
	public MutableCoord(Vec copied) {
		this(copied.x(), copied.y(), copied.z());
	}
	
	
	/**
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public MutableCoord(double x, double y) {
		super();
		this.x = x;
		this.y = y;
		this.z = 0;
	}
	
	
	/**
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param z Z coordinate
	 */
	public MutableCoord(double x, double y, double z) {
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
	public MutableCoord result(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		
		return this;
	}
}
