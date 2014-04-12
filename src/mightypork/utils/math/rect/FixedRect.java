package mightypork.utils.math.rect;


import mightypork.utils.math.coord.FixedCoord;
import mightypork.utils.math.coord.Vec;
import mightypork.utils.math.coord.VecView;


public class FixedRect extends RectView {
	
	private final VecView pos;
	private final VecView size;
	
	
	/**
	 * Create at 0,0 with zero size
	 */
	public FixedRect() {
		pos = Vec.ZERO;
		size = Vec.ZERO;
	}
	
	
	/**
	 * Create at 0,0 with given size
	 * 
	 * @param width
	 * @param height
	 */
	public FixedRect(double width, double height) {
		this(0, 0, width, height);
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param origin
	 * @param width
	 * @param height
	 */
	public FixedRect(Vec origin, double width, double height) {
		this(origin.x(), origin.y(), width, height);
	}
	
	
	/**
	 * Create at 0,0 with given size.
	 * 
	 * @param size
	 */
	public FixedRect(Vec size) {
		this(0, 0, size.x(), size.y());
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param origin
	 * @param size
	 */
	public FixedRect(Vec origin, Vec size) {
		this(origin.x(), origin.y(), size.x(), size.y());
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public FixedRect(double x, double y, double width, double height) {
		pos = new FixedCoord(x, y);
		size = new FixedCoord(Math.abs(width), Math.abs(height));
	}
	
	
	/**
	 * Create as copy of another
	 * 
	 * @param other copied
	 */
	public FixedRect(Rect other) {
		this(other.getOrigin(), other.getSize());
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
