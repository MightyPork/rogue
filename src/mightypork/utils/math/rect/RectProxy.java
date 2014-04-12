package mightypork.utils.math.rect;


import mightypork.utils.math.vect.VectVal;


/**
 * Immutable rect accessor
 * 
 * @author MightyPork
 */
public class RectProxy extends RectView {
	
	private final Rect observed;
	
	
	public RectProxy(Rect observed) {
		this.observed = observed;
	}
	
	
	@Override
	public VectVal origin()
	{
		return observed.origin();
	}
	
	
	@Override
	public VectVal size()
	{
		return observed.size();
	}
	
}
