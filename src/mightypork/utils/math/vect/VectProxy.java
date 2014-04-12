package mightypork.utils.math.vect;


/**
 * View of another coordinate, immutable.<br>
 * GetVec()
 * 
 * @author MightyPork
 */
class VectProxy extends VectAdapter {
	
	final Vect observed;
	
	
	/**
	 * Protected, in order to enforce the use of view() method on Vec, which
	 * uses caching.
	 * 
	 * @param observed
	 */
	public VectProxy(Vect observed) {
		this.observed = observed;
	}
	
	
	@Override
	protected Vect getSource()
	{
		return observed;
	}
	
}
