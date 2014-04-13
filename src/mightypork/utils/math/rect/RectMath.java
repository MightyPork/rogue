package mightypork.utils.math.rect;


import mightypork.utils.math.num.Num;
import mightypork.utils.math.num.NumView;
import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectAdapter;
import mightypork.utils.math.vect.VectView;


abstract class RectMath<R extends Rect> extends AbstractRect {
	
	protected NumView p_x;
	protected NumView p_y;
	protected NumView p_width;
	protected NumView p_height;
	
	protected NumView p_left;
	protected NumView p_top;
	protected NumView p_right;
	protected NumView p_bottom;
	
	protected VectView p_tl;
	protected VectView p_tc;
	protected VectView p_tr;
	
	protected VectView p_cl;
	protected VectView p_cc;
	protected VectView p_cr;
	
	protected VectView p_bl;
	protected VectView p_bc;
	protected VectView p_br;
	
	protected VectView p_origin;
	protected VectView p_size;
	
	
	public RectMath() {
		
		p_origin = new VectAdapter() {
			
			@Override
			protected Vect getSource()
			{
				return RectMath.this.origin();
			}
		};
		
		p_size = new VectAdapter() {
			
			@Override
			protected Vect getSource()
			{
				return RectMath.this.size();
			}
		};
		
		// setup proxies
		
		// origin, size disassembled
		p_width = p_size.xn();
		p_height = p_size.yn();
		p_x = p_origin.xn();
		p_y = p_origin.yn();
		
		// coordinates
		p_top = p_y;
		p_left = p_x;
		p_right = p_left.add(p_width);
		p_bottom = p_top.add(p_height);
		
		// corners
		// -- top line --
		Num width_half = p_width.half();
		Num ya = Num.ZERO;
		p_tl = p_origin;
		p_tc = p_origin.add(width_half, ya);
		p_tr = p_origin.add(p_width, ya);
		
		// --center line--
		ya = p_height.half();
		p_cl = p_origin.add(Num.ZERO, ya);
		p_cc = p_origin.add(width_half, ya);
		p_cr = p_origin.add(p_width, ya);
		
		// -- bottom line --
		ya = p_height;
		p_bl = p_origin.add(Num.ZERO, ya);
		p_bc = p_origin.add(width_half, ya);
		p_br = p_origin.add(p_width, ya);
	}
	
	
	/**
	 * Add vector to origin
	 * 
	 * @param move offset vector
	 * @return result
	 */
	public abstract R move(Vect move);
	
	
	/**
	 * Add X and Y to origin
	 * 
	 * @param x x to add
	 * @param y y to add
	 * @return result
	 */
	public abstract R move(double x, double y);
	
	
	/**
	 * Shrink to sides
	 * 
	 * @param shrink shrink size (horisontal and vertical)
	 * @return result
	 */
	
	public R shrink(Vect shrink)
	{
		return shrink(shrink.x(), shrink.y());
	}
	
	
	/**
	 * Shrink to sides at sides
	 * 
	 * @param x horizontal shrink
	 * @param y vertical shrink
	 * @return result
	 */
	public R shrink(double x, double y)
	{
		return shrink(x, x, y, y);
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
	public abstract R shrink(double left, double right, double top, double bottom);
	
	
	/**
	 * Grow to sides
	 * 
	 * @param grow grow size (added to each side)
	 * @return grown copy
	 */
	public final R grow(Vect grow)
	{
		return grow(grow.x(), grow.y());
	}
	
	
	/**
	 * Grow to sides
	 * 
	 * @param x horizontal grow
	 * @param y vertical grow
	 * @return result
	 */
	public final R grow(double x, double y)
	{
		return grow(x, x, y, y);
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
	public abstract R grow(double left, double right, double top, double bottom);
	
	
	/**
	 * Round coords
	 * 
	 * @return result
	 */
	public abstract R round();
	
	
	/**
	 * Center to given point
	 * 
	 * @param point new center
	 * @return centered
	 */
	public abstract R centerTo(final Vect point);
	
	
	/**
	 * Check if point is inside this rectangle
	 * 
	 * @param point point to test
	 * @return is inside
	 */
	public boolean contains(Vect point)
	{
		final double x = point.x();
		final double y = point.y();
		
		final double x1 = origin().x();
		final double y1 = origin().y();
		final double x2 = x1 + size().x();
		final double y2 = y1 + size().y();
		
		return x >= x1 && y >= y1 && x <= x2 && y <= y2;
	}
}
