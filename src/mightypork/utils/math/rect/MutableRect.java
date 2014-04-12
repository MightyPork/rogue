package mightypork.utils.math.rect;


import mightypork.utils.math.coord.MutableCoord;
import mightypork.utils.math.coord.Vec;
import mightypork.utils.math.coord.VecMutable;
import mightypork.utils.math.coord.VecView;


public class MutableRect extends RectImpl<RectMutable> implements RectMutable {
	
	private final VecMutable pos = new MutableCoord();
	private final VecMutable size = new MutableCoord();
	
	
	/**
	 * Create at 0,0 with zero size
	 */
	public MutableRect() {
		// keep default zeros
	}
	
	
	/**
	 * Create at 0,0 with given size
	 * 
	 * @param width
	 * @param height
	 */
	public MutableRect(double width, double height) {
		this.pos.setTo(0, 0);
		this.size.setTo(width, height).abs();
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param origin
	 * @param width
	 * @param height
	 */
	public MutableRect(Vec origin, double width, double height) {
		this.pos.setTo(origin);
		this.size.setTo(width, height).abs();
	}
	
	
	/**
	 * Create at 0,0 with given size.
	 * 
	 * @param size
	 */
	public MutableRect(Vec size) {
		this(0, 0, size.x(), size.y());
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param origin
	 * @param size
	 */
	public MutableRect(Vec origin, Vec size) {
		this.pos.setTo(origin);
		this.size.setTo(size).abs();
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public MutableRect(double x, double y, double width, double height) {
		pos.setTo(x, y);
		size.setTo(width, height).abs();
	}
	
	
	/**
	 * Create as copy of another
	 * 
	 * @param other copied
	 */
	public MutableRect(Rect other) {
		this(other.getOrigin(), other.getSize());
	}
	
	
	/**
	 * Set to other rect's coordinates
	 * 
	 * @param rect other rect
	 */
	@Override
	public void setTo(Rect rect)
	{
		setTo(rect.getOrigin(), rect.getSize());
	}
	
	
	@Override
	public void setTo(Vec origin, Vec size)
	{
		this.pos.setTo(origin);
		this.size.setTo(size).abs();
	}
	
	
	@Override
	public void setTo(Vec origin, double width, double height)
	{
		this.pos.setTo(origin);
		this.size.setTo(width, height).abs();
	}
	
	
	@Override
	public void setOrigin(Vec origin)
	{
		this.pos.setTo(origin);
	}
	
	
	@Override
	public void setSize(Vec size)
	{
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
	public RectMutable move(double x, double y)
	{
		pos.add(x, y);
		return this;
	}
	
	
	/**
	 * Get a copy
	 * 
	 * @return copy
	 */
	@Override
	public RectMutable copy()
	{
		return new MutableRect(this);
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
	public RectView view()
	{
		return new FixedRect(this);
	}
}
