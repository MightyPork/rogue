package mightypork.utils.math.rect;


import mightypork.utils.math.num.NumConst;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectConst;


/**
 * Rectangle with constant bounds, that can never change.
 * 
 * @author MightyPork
 */
public class RectConst extends Rect {
	
	private final VectConst pos;
	private final VectConst size;
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	RectConst(double x, double y, double width, double height) {
		this.pos = Vect.make(x, y);
		this.size = Vect.make(width, height);
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param origin
	 * @param size
	 */
	RectConst(Vect origin, Vect size) {
		this.pos = origin.freeze();
		this.size = size.freeze();
	}
	
	
	/**
	 * Create at given origin, with given size.
	 * 
	 * @param another other coord
	 */
	RectConst(Rect another) {
		this.pos = another.origin().freeze();
		this.size = another.size().freeze();
	}
	
	
	/**
	 * @deprecated it's useless to copy a constant
	 */
	@Override
	@Deprecated
	public RectConst freeze()
	{
		return this; // already constant
	}
	
	
	@Override
	public VectConst origin()
	{
		return pos;
	}
	
	
	@Override
	public VectConst size()
	{
		return size;
	}
	
	
	@Override
	public RectConst move(Vect move)
	{
		return move(move.x(), move.y());
	}
	
	
	@Override
	public RectConst move(double x, double y)
	{
		return Rect.make(origin().add(x, y), size()).freeze();
	}
	
	
	@Override
	public RectConst shrink(double left, double right, double top, double bottom)
	{
		return Rect.make(origin().add(left, top), size().sub(left + right, top + bottom)).freeze();
		
	}
	
	
	@Override
	public RectConst grow(double left, double right, double top, double bottom)
	{
		return Rect.make(origin().sub(left, top), size().add(left + right, top + bottom)).freeze();
	}
	
	
	@Override
	public RectConst round()
	{
		final VectConst s = size();
		final VectConst o = origin();
		
		return Rect.make(o.round(), s.round()).freeze();
	}
	
	
	@Override
	public NumConst x()
	{
		return origin().xn();
	}
	
	
	@Override
	public NumConst y()
	{
		return origin().yn();
	}
	
	
	@Override
	public NumConst width()
	{
		return size().xn();
	}
	
	
	@Override
	public NumConst height()
	{
		return size().yn();
	}
	
	
	@Override
	public NumConst left()
	{
		return origin().xn();
	}
	
	
	@Override
	public NumConst right()
	{
		return origin().xn().add(size().xn());
	}
	
	
	@Override
	public NumConst top()
	{
		return origin().yn();
	}
	
	
	@Override
	public NumConst bottom()
	{
		return origin().yn().add(size().yn());
	}
	
	
	@Override
	public VectConst topLeft()
	{
		return origin();
	}
	
	
	@Override
	public VectConst topCenter()
	{
		return origin().add(size().x() / 2, 0);
	}
	
	
	@Override
	public VectConst topRight()
	{
		return origin().add(size().x(), 0);
	}
	
	
	@Override
	public VectConst centerLeft()
	{
		return origin().add(0, size().y() / 2);
	}
	
	
	@Override
	public VectConst center()
	{
		return origin().add(size().half()).freeze();
	}
	
	
	@Override
	public VectConst centerRight()
	{
		return origin().add(size().x(), size().y() / 2);
	}
	
	
	@Override
	public VectConst bottomLeft()
	{
		return origin().add(0, size().y());
	}
	
	
	@Override
	public VectConst bottomCenter()
	{
		return origin().add(size().x() / 2, size().y());
	}
	
	
	@Override
	public VectConst bottomRight()
	{
		return origin().add(size()).freeze();
	}
	
}
