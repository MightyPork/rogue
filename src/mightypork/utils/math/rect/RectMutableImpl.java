package mightypork.utils.math.rect;


import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectMutable;
import mightypork.utils.math.vect.VectView;


class RectMutableImpl extends RectMutable {
	
	final VectMutable pos = VectMutable.zero();
	final VectMutable size = VectMutable.zero();
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public RectMutableImpl(double x, double y, double width, double height) {
		this.pos.setTo(x, y);
		this.size.setTo(width, height);
	}
	
	
	@Override
	public VectView origin()
	{
		return pos;
	}
	
	
	@Override
	public VectView size()
	{
		return size;
	}
	
	
	@Override
	public void setOrigin(Vect origin)
	{
		this.pos.setTo(origin);
	}
	
	
	@Override
	public void setSize(Vect size)
	{
		this.size.setTo(size);
	}
}
