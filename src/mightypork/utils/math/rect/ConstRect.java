package mightypork.utils.math.rect;


import mightypork.utils.math.coord.VecView;


class ConstRect extends RectValue {
	
	private final VecView pos;
	private final VecView size;
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public ConstRect(double x, double y, double width, double height) {
		pos = VecView.make(x, y);
		size = VecView.make(Math.abs(width), Math.abs(height));
	}
	
	
	@Override
	public ConstRect value()
	{
		return this; // nothing can change.
	}
	
	
	@Override
	public VecView getOrigin()
	{
		return pos;
	}
	
	
	@Override
	public VecView getSize()
	{
		return size;
	}
	
}
