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
	
	// cached with lazy loading
	private NumConst v_b;
	private NumConst v_r;
	private VectConst v_br;
	private VectConst v_tc;
	private VectConst v_tr;
	private VectConst v_cl;
	private VectConst v_c;
	private VectConst v_cr;
	private VectConst v_bl;
	private VectConst v_bc;
	private RectConst v_round;
	
	
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
		return Rect.make(pos.add(x, y), size);
	}
	
	
	@Override
	public RectConst shrink(double left, double right, double top, double bottom)
	{
		return Rect.make(pos.add(left, top), size.sub(left + right, top + bottom)).freeze();
		
	}
	
	
	@Override
	public RectConst grow(double left, double right, double top, double bottom)
	{
		return Rect.make(pos.sub(left, top), size.add(left + right, top + bottom)).freeze();
	}
	
	
	@Override
	public RectConst round()
	{
		if (v_round == null) v_round = Rect.make(pos.round(), size.round());
		return v_round;
	}
	
	
	@Override
	public NumConst x()
	{
		return pos.xn();
	}
	
	
	@Override
	public NumConst y()
	{
		return pos.yn();
	}
	
	
	@Override
	public NumConst width()
	{
		return size.xn();
	}
	
	
	@Override
	public NumConst height()
	{
		return size.yn();
	}
	
	
	@Override
	public NumConst left()
	{
		return pos.xn();
	}
	
	
	@Override
	public NumConst right()
	{
		if (v_r == null) v_r = super.right().freeze();
		return v_r;
	}
	
	
	@Override
	public NumConst top()
	{
		return pos.yn();
	}
	
	
	@Override
	public NumConst bottom()
	{
		if (v_b == null) v_b = super.bottom().freeze();
		return v_b;
	}
	
	
	@Override
	public VectConst topLeft()
	{
		return pos;
	}
	
	
	@Override
	public VectConst topCenter()
	{
		if (v_tc == null) v_tc = super.topCenter().freeze();
		return v_tc;
	}
	
	
	@Override
	public VectConst topRight()
	{
		if (v_tr == null) v_tr = super.topRight().freeze();
		return v_tr;
	}
	
	
	@Override
	public VectConst centerLeft()
	{
		if (v_cl == null) v_cl = super.centerLeft().freeze();
		return v_cl;
	}
	
	
	@Override
	public VectConst center()
	{
		if (v_c == null) v_c = super.center().freeze();
		return v_c;
	}
	
	
	@Override
	public VectConst centerRight()
	{
		if (v_cr == null) v_cr = super.centerRight().freeze();
		return v_cr;
	}
	
	
	@Override
	public VectConst bottomLeft()
	{
		if (v_bl == null) v_bl = super.bottomLeft().freeze();
		return v_bl;
	}
	
	
	@Override
	public VectConst bottomCenter()
	{
		if (v_bc == null) v_bc = super.bottomCenter().freeze();
		return v_bc;
	}
	
	
	@Override
	public VectConst bottomRight()
	{
		if (v_br == null) v_br = super.bottomRight().freeze();
		return v_br;
	}
	
}
