package mightypork.utils.math.rect;


import mightypork.utils.math.coord.Vec;
import mightypork.utils.math.coord.VecMutable;
import mightypork.utils.math.coord.VecView;


class RectMutableImpl extends RectMutable {
	
	final VecMutable pos = VecMutable.zero();
	final VecMutable size = VecMutable.zero();
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param origin
	 * @param size
	 */
	public RectMutableImpl(Vec origin, Vec size) {
		this.pos.setTo(origin);
		this.size.setTo(size).abs();
	}
	
	
	/**
	 * Add X and Y to origin
	 * 
	 * @param x x to add
	 * @param y y to add
	 * @return result
	 */
	@Override
	public RectMutableImpl move(double x, double y)
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
	public RectMutableImpl shrink(double left, double right, double top, double bottom)
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
	public RectMutableImpl grow(double left, double right, double top, double bottom)
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
	public RectMutableImpl round()
	{
		pos.round();
		size.round();
		return this;
	}
	
	
	@Override
	public VecView getOrigin()
	{
		return pos.view();
	}
	
	
	@Override
	public VecView getSize()
	{
		return size.view();
	}
	
	
	@Override
	public RectMutable setOrigin(Vec origin)
	{
		this.pos.setTo(origin);
		return this;
	}
	
	
	@Override
	public RectMutable setSize(Vec size)
	{
		this.size.setTo(size).abs();
		return this;
	}
}
