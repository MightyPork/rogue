package mightypork.utils.math.constraints.rect;


import mightypork.utils.math.constraints.vect.Vect;


/**
 * Rect made of two {@link Vect}s
 * 
 * @author MightyPork
 */
class RectVectAdapter extends Rect {
	
	private final Vect origin;
	private final Vect size;
	
	
	public RectVectAdapter(Vect origin, Vect size) {
		this.origin = origin;
		this.size = size;
	}
	
	
	@Override
	public Vect origin()
	{
		return origin;
	}
	
	
	@Override
	public Vect size()
	{
		return size;
	}
	
}
