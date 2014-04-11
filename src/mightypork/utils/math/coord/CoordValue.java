package mightypork.utils.math.coord;




/**
 * Coordinate with immutable numeric values.<br>
 * Operations yield a new {@link MutableCoord} with the result.
 * 
 * @author MightyPork
 */
public class CoordValue extends VecView {
	
	private final double x, y, z;
	
	
	public CoordValue(Vec other) {
		this(other.x(), other.y(), other.z());
	}
	
	
	public CoordValue(double x, double y) {
		this(x, y, 0);
	}
	
	
	public CoordValue(double x, double y, double z) {
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
	
}
