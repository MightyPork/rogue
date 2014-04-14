package mightypork.utils.math.rect;


import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectVar;


public class RectVar extends RectMutable {
	
	final VectVar pos = Vect.makeVar();
	final VectVar size = Vect.makeVar();
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public RectVar(double x, double y, double width, double height) {
		this.pos.setTo(x, y);
		this.size.setTo(width, height);
	}
	
	
	@Override
	public Vect origin()
	{
		return pos;
	}
	
	
	@Override
	public Vect size()
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