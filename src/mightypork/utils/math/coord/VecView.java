package mightypork.utils.math.coord;


/**
 * Read-only coordinate, operations with it will yield a new
 * {@link MutableCoord} with the result.
 * 
 * @author MightyPork
 */
public abstract class VecView extends VecMathImpl<VecView> {
	
	@Override
	public VecView result(double x, double y, double z)
	{
		return new FixedCoord(x, y, z);
	}
	
	
	@Override
	public VecView view()
	{
		return this; // already not mutable
	}
}
