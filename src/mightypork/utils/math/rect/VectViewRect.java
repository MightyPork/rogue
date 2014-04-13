package mightypork.utils.math.rect;


import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectView;


/**
 * Rect made of two {@link VectView}s
 * 
 * @author MightyPork
 */
class VectViewRect extends RectView {
	
	private final VectView origin;
	private final VectView size;
	
	
	public VectViewRect(Vect origin, Vect size) {
		this.origin = origin.view();
		this.size = size.view();
	}
	
	
	@Override
	public VectView origin()
	{
		return origin;
	}
	
	
	@Override
	public VectView size()
	{
		return size;
	}
	
}
