package mightypork.utils.math.rect;


import mightypork.utils.math.coord.VecView;


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
	public VecView getOrigin()
	{
		return observed.getOrigin();
	}
	
	
	@Override
	public VecView getSize()
	{
		return observed.getSize();
	}
	
}
