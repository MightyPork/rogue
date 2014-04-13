package mightypork.utils.math.rect;

import mightypork.utils.math.vect.VectView;


/**
 * Immutable rect accessor
 * 
 * @author MightyPork
 */
public class RectProxy extends RectView {
	
	private final RectView observed;
	
	
	public RectProxy(Rect observed) {
		this.observed = observed.view();
	}
	
	
	@Override
	public VectView origin()
	{
		return observed.p_origin;
	}
	
	
	@Override
	public VectView size()
	{
		return observed.p_size;
	}
}
