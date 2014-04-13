package mightypork.utils.math.rect;


import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectMutable;
import mightypork.utils.math.vect.VectVal;


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
		this.size.setTo(width, height).abs();
	}
	
	
	@Override
	public VectVal origin()
	{
		return pos.copy();
	}
	
	
	@Override
	public VectVal size()
	{
		return size.copy();
	}
	
	
	@Override
	protected RectMutable result(Vect newOrigin, Vect newSize)
	{
		setOrigin(newOrigin);
		setSize(newSize);
		return this;
	}
	
	
	@Override
	public RectMutable setOrigin(Vect origin)
	{
		this.pos.setTo(origin);
		return this;
	}
	
	
	@Override
	public RectMutable setSize(Vect size)
	{
		this.size.setTo(size).abs();
		return this;
	}
}
