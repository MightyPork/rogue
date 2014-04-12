package mightypork.utils.math.coord;


/**
 * Coordinate with immutable numeric values.<br>
 * Operations yield a new {@link MutableCoord} with the result.
 * 
 * @author MightyPork
 */
public class FixedCoord extends VecView {
	
	private final double x, y, z;
	
	
	public FixedCoord(Vec other) {
		this(other.x(), other.y(), other.z());
	}
	
	
	public FixedCoord(double x, double y) {
		this(x, y, 0);
	}
	
	
	public FixedCoord(double x, double y, double z) {
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
