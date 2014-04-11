package mightypork.utils.math.coord;

import mightypork.utils.math.constraints.VecConstraint;



/**
 * Read-only coordinate, operations with it will yield a new {@link MutableCoord} with the result.
 * 
 * @author MightyPork
 */
public abstract class VecView extends VecImpl<CoordValue> {	
	
	@Override
	public CoordValue result(double x, double y, double z)
	{
		return new CoordValue(x,y,z);
	}
}
