package mightypork.utils.math.vect;


import mightypork.utils.math.num.Num;
import mightypork.utils.math.num.NumVal;


/**
 * Implementation of coordinate methods
 * 
 * @author MightyPork
 * @param <V> Return type of methods
 */
abstract class VectMathStatic<V extends VectMathStatic<V>> extends VectMath<V, NumVal> {
	
	@Override
	public NumVal xn()
	{
		return NumVal.make(x());
	}
	
	
	@Override
	public NumVal yn()
	{
		return NumVal.make(yn());
	}
	
	
	@Override
	public NumVal zn()
	{
		return NumVal.make(z());
	}
	
	
	/**
	 * <p>
	 * Some operation was performed and this result was obtained.
	 * </p>
	 * <p>
	 * It's now up to implementing class what to do - mutable ones can alter
	 * it's data values, immutable can return a new Vec.
	 * </p>
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return the result Vec
	 */
	public abstract V result(double x, double y, double z);
	
	
	@Override
	public V abs()
	{
		return result(Math.abs(x()), Math.abs(y()), Math.abs(z()));
	}
	
	
	@Override
	public V add(Vect vec)
	{
		return add(vec.x(), vec.y(), vec.z());
	}
	
	
	@Override
	public V add(double x, double y)
	{
		return add(x, y, 0);
	}
	
	
	@Override
	public V add(double x, double y, double z)
	{
		return result(x() + x, y() + y, z() + z);
	}
	
	
	@Override
	public V add(Num x, Num y)
	{
		return add(x, y, Num.ZERO);
	}
	
	
	@Override
	public V add(final Num x, final Num y, final Num z)
	{
		return add(x.value(), y.value(), z.value());
	}
	
	
	@Override
	public V half()
	{
		return mul(0.5);
	}
	
	
	@Override
	public V mul(double d)
	{
		return mul(d, d, d);
	}
	
	
	@Override
	public V mul(Vect vec)
	{
		return mul(vec.x(), vec.y(), vec.z());
	}
	
	
	@Override
	public V mul(double x, double y)
	{
		return mul(x, y, 1);
	}
	
	
	@Override
	public V mul(double x, double y, double z)
	{
		return result(x() * x, y() * y, z() * z);
	}
	
	
	@Override
	public V mul(final Num d)
	{
		return mul(d, d, d);
	}
	
	
	@Override
	public V mul(final Num x, final Num y)
	{
		return mul(x, y, Num.ONE);
	}
	
	
	@Override
	public V mul(final Num x, final Num y, final Num z)
	{
		return mul(x.value(), y.value(), z.value());
	}
	
	
	@Override
	public V round()
	{
		return result(Math.round(x()), Math.round(y()), Math.round(z()));
	}
	
	
	@Override
	public V floor()
	{
		return result(Math.floor(x()), Math.floor(y()), Math.floor(z()));
	}
	
	
	@Override
	public V ceil()
	{
		return result(Math.ceil(x()), Math.ceil(y()), Math.ceil(z()));
	}
	
	
	@Override
	public V sub(Vect vec)
	{
		return sub(vec.x(), vec.y(), vec.z());
	}
	
	
	@Override
	public V sub(double x, double y)
	{
		return sub(x, y, 0);
	}
	
	
	@Override
	public V sub(double x, double y, double z)
	{
		return result(x() - x, y() - y, z() - z);
	}
	
	
	@Override
	public V sub(Num x, Num y)
	{
		return sub(x, y, Num.ZERO);
	}
	
	
	@Override
	public V sub(final Num x, final Num y, final Num z)
	{
		return sub(x.value(), y.value(), z.value());
	}
	
	
	@Override
	public V neg()
	{
		return result(-x(), -y(), -z());
	}
	
	
	@Override
	public V norm(double size)
	{
		if (isZero()) return result(x(), y(), z()); // can't norm zero vector
			
		final NumVal k = size().mul(1 / size);
		
		return mul(k);
	}
	
	
	@Override
	public NumVal distTo(Vect point)
	{
		final double dx = x() - point.x();
		final double dy = y() - point.y();
		final double dz = z() - point.z();
		
		return NumVal.make(Math.sqrt(dx * dx + dy * dy + dz * dz));
	}
	
	
	@Override
	public final V midTo(Vect point)
	{
		final double dx = (point.x() - x()) * 0.5;
		final double dy = (point.y() - y()) * 0.5;
		final double dz = (point.z() - z()) * 0.5;
		
		return result(dx, dy, dz);
	}
	
	
	@Override
	public final V vectTo(Vect point)
	{
		return result(point.x() - x(), point.y() - y(), point.z() - z());
	}
	
	
	@Override
	public final V cross(Vect vec)
	{
		//@formatter:off
		return result(
				y() * vec.z() - z() * vec.y(),
				z() * vec.x() - x() * vec.z(),
				x() * vec.y() - y() * vec.x());
		//@formatter:on
	}
	
	
	@Override
	public NumVal dot(Vect vec)
	{
		return NumVal.make(x() * vec.x() + y() * vec.y() + z() * vec.z());
	}
	
	
	@Override
	public NumVal size()
	{
		final double x = x(), y = y(), z = z();
		return NumVal.make(Math.sqrt(x * x + y * y + z * z));
	}
}
