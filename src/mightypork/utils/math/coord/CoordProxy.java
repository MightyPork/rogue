package mightypork.utils.math.coord;

import mightypork.utils.math.constraints.VecConstraint;


/**
 * <p><b>[ Use Vec.view() method to make a proxy! ]</b></p>
 * <p>View of another coordinate, immutable.<br>
 * Operations yield a new {@link MutableCoord} with the result.</p>
 * 
 * @author MightyPork
 */
public class CoordProxy extends VecView {
	
	private final Vec observed;
	
	
	/**
	 * Protected, in order to enforce the use of view() method on Vec, which
	 * uses caching.
	 * 
	 * @param observed
	 */
	public CoordProxy(Vec observed) {
		this.observed = observed;
	}
	
	@Override
	public CoordProxy view()
	{
		return this; // no need to make another
	}
	
	
	@Override
	public double x()
	{
		return observed.x();
	}
	
	
	@Override
	public double y()
	{
		return observed.y();
	}
	
	
	@Override
	public double z()
	{
		return observed.z();
	}
	
}
