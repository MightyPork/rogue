package mightypork.utils.math.coord;


/**
 * Coordinate with immutable numeric values.
 * 
 * @author MightyPork
 */
class ConstVec extends VecView {
	
	private final double x, y, z;
	
	
	public ConstVec(Vec other) {
		this(other.x(), other.y(), other.z());
	}
	
	
	public ConstVec(double x, double y) {
		this(x, y, 0);
	}
	
	
	public ConstVec(double x, double y, double z) {
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
	public VecView value()
	{
		return this; // it's constant already
	}
	
}
