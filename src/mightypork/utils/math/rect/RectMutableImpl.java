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
	
	
	/**
	 * Add X and Y to origin
	 * 
	 * @param x x to add
	 * @param y y to add
	 * @return result
	 */
	@Override
	public RectMutable move(double x, double y)
	{
		pos.add(x, y);
		return this;
	}
	
	
	/**
	 * Shrink the rect
	 * 
	 * @param left shrink
	 * @param right shrink
	 * @param top shrink
	 * @param bottom shrink
	 * @return result
	 */
	@Override
	public RectMutable shrink(double left, double right, double top, double bottom)
	{
		pos.add(left, top);
		size.sub(left + right, top + bottom).abs();
		return this;
	}
	
	
	/**
	 * Grow the rect
	 * 
	 * @param left growth
	 * @param right growth
	 * @param top growth
	 * @param bottom growth
	 * @return result
	 */
	@Override
	public RectMutable grow(double left, double right, double top, double bottom)
	{
		pos.sub(left, top);
		size.add(left + right, top + bottom).abs();
		return this;
	}
	
	
	/**
	 * Round coords
	 * 
	 * @return result
	 */
	@Override
	public RectMutable round()
	{
		pos.round();
		size.round();
		return this;
	}
	
	
	@Override
	public VectVal getOrigin()
	{
		return pos.value();
	}
	
	
	@Override
	public VectVal getSize()
	{
		return size.value();
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
