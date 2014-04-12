package mightypork.utils.math.coord;


/**
 * View of another coordinate, immutable.<br>
 * GetVec()
 * 
 * @author MightyPork
 */
class VecProxy extends AbstractVecProxy {
	
	final Vec observed;
	
	
	/**
	 * Protected, in order to enforce the use of view() method on Vec, which
	 * uses caching.
	 * 
	 * @param observed
	 */
	public VecProxy(Vec observed) {
		this.observed = observed;
	}
	
	
	@Override
	protected Vec getSource()
	{
		return observed;
	}
	
}
