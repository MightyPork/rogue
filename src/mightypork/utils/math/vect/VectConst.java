package mightypork.utils.math.vect;


import mightypork.utils.math.num.Num;
import mightypork.utils.math.num.NumConst;


/**
 * Coordinate with immutable numeric values.<br>
 * This coordinate is guaranteed to never change, as opposed to view.
 * 
 * @author MightyPork
 */
public final class VectConst extends Vect {
	
	private final double x, y, z;
	// non-parametric operations are cached using lazy load.
	private NumConst v_size;
	private VectConst v_neg;
	private VectConst v_ceil;
	private VectConst v_floor;
	private VectConst v_round;
	private VectConst v_half;
	private VectConst v_abs;
	private NumConst v_xc;
	private NumConst v_yc;
	private NumConst v_zc;
	
	
	VectConst(Vect other) {
		this(other.x(), other.y(), other.z());
	}
	
	
	VectConst(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	@Override
	public double x()
	{
		return x;
	}
	
	
	@Override
	public double y()
	{
		return y;
	}
	
	
	@Override
	public double z()
	{
		return z;
	}
	
	
	/**
	 * @return X constraint
	 */
	@Override
	public final NumConst xn()
	{
		if (v_xc == null) v_xc = Num.make(this.x);
		
		return v_xc;
	}
	
	
	/**
	 * @return Y constraint
	 */
	@Override
	public final NumConst yn()
	{
		if (v_yc == null) v_yc = Num.make(this.y);
		
		return v_yc;
	}
	
	
	/**
	 * @return Z constraint
	 */
	@Override
	public final NumConst zn()
	{
		
		if (v_zc == null) v_zc = Num.make(this.z);
		
		return v_zc;
	}
	
	
	/**
	 * @deprecated it's useless to copy a constant
	 */
	@Override
	@Deprecated
	public VectConst freeze()
	{
		return this; // it's constant already
	}
	
	
	@Override
	public VectConst abs()
	{
		if (v_abs != null) return v_abs;
		return v_abs = Vect.make(Math.abs(x()), Math.abs(y()), Math.abs(z()));
	}
	
	
	@Override
	public VectConst add(double x, double y)
	{
		return add(x, y, 0);
	}
	
	
	@Override
	public VectConst add(double x, double y, double z)
	{
		return Vect.make(x() + x, y() + y, z() + z);
	}
	
	
	@Override
	public VectConst half()
	{
		if (v_half != null) return v_half;
		return v_half = mul(0.5);
	}
	
	
	@Override
	public VectConst mul(double d)
	{
		return mul(d, d, d);
	}
	
	
	@Override
	public VectConst mul(double x, double y)
	{
		return mul(x, y, 1);
	}
	
	
	@Override
	public VectConst mul(double x, double y, double z)
	{
		return Vect.make(x() * x, y() * y, z() * z);
	}
	
	
	@Override
	public VectConst round()
	{
		if (v_round != null) return v_round;
		return v_round = Vect.make(Math.round(x()), Math.round(y()), Math.round(z()));
	}
	
	
	@Override
	public VectConst floor()
	{
		if (v_floor != null) return v_floor;
		return v_floor = Vect.make(Math.floor(x()), Math.floor(y()), Math.floor(z()));
	}
	
	
	@Override
	public VectConst ceil()
	{
		if (v_ceil != null) return v_ceil;
		return v_ceil = Vect.make(Math.ceil(x()), Math.ceil(y()), Math.ceil(z()));
	}
	
	
	@Override
	public VectConst sub(double x, double y)
	{
		return sub(x, y, 0);
	}
	
	
	@Override
	public VectConst sub(double x, double y, double z)
	{
		return Vect.make(x() - x, y() - y, z() - z);
	}
	
	
	@Override
	public VectConst neg()
	{
		if (v_neg != null) return v_neg;
		return v_neg = Vect.make(-x(), -y(), -z());
	}
	
	
	@Override
	public VectConst norm(double size)
	{
		if (isZero()) return this; // can't norm zero vector
			
		final double k = size().mul(1 / size).value();
		
		return mul(k);
	}
	
	
	@Override
	public NumConst size()
	{
		if (v_size != null) return v_size;
		
		final double x = x(), y = y(), z = z();
		return v_size = Num.make(Math.sqrt(x * x + y * y + z * z));
	}
	
}
