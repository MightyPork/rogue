package mightypork.utils.math.rect;


import mightypork.utils.math.vect.VectVal;
import mightypork.utils.math.vect.VectView;


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
	public VectVal getOrigin()
	{
		return observed.getOrigin();
	}
	
	
	@Override
	public VectVal getSize()
	{
		return observed.getSize();
	}
	
}
