package mightypork.utils.math.rect;


import mightypork.utils.math.vect.VectView;


/**
 * Immutable rect accessor
 * 
 * @author MightyPork
 */
class RectProxy extends RectView {
	
	private final Rect observed;
	
	
	public RectProxy(Rect observed) {
		
		assert (!(observed instanceof RectView));
		
		this.observed = observed;
	}
	
	
	@Override
	public VectView origin()
	{
		return observed.origin().view();
	}
	
	
	@Override
	public VectView size()
	{
		return observed.size().view();
	}
}
