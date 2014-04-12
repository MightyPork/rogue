package mightypork.utils.math.coord;


/**
 * 2D coord for anonymous implementations.<br>
 * Operations yield a new {@link MutableCoord} with the result.
 * 
 * @author MightyPork
 */
public abstract class SynthCoord2D extends VecView {
	
	@Override
	public abstract double x();
	
	
	@Override
	public abstract double y();
	
	
	@Override
	public double z()
	{
		return 0;
	}
}
