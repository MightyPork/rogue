package mightypork.utils.math.coord;


import mightypork.utils.math.constraints.NumberConstraint;


/**
 * 3D immutable coord for anonymous implementations.<br>
 * Operations yield a new {@link MutableCoord} with the result.
 *  
 * @author MightyPork
 */
public abstract class SynthCoord3D extends VecView {
	
	@Override
	public abstract double x();
	
	
	@Override
	public abstract double y();
	
	
	@Override
	public abstract double z();
	
}
