package mightypork.utils.math.coord;


import mightypork.utils.math.constraints.NumberConstraint;


/**
 * Implementation of coordinate methods
 * 
 * @author MightyPork
 * @param <V> Return type of methods
 */
abstract class VecImpl<V extends VecArith> implements VecArith {
	
	private NumberConstraint constraintZ, constraintY, constraintX;
	
	private CoordProxy view = null;
	
	
	@Override
	public abstract double x();
	
	
	@Override
	public abstract double y();
	
	
	@Override
	public abstract double z();
	
	
	@Override
	public int xi()
	{
		return (int) Math.round(x());
	}
	
	
	@Override
	public int yi()
	{
		return (int) Math.round(y());
	}
	
	
	@Override
	public int zi()
	{
		return (int) Math.round(z());
	}
	
	
	@Override
	public float xf()
	{
		return (float) x();
	}
	
	
	@Override
	public float yf()
	{
		return (float) y();
	}
	
	
	@Override
	public float zf()
	{
		return (float) z();
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
	public MutableCoord copy()
	{
		return new MutableCoord(this);
	}
	
	
	@Override
	public CoordProxy view()
	{
		if (view == null) view = new CoordProxy(this);
		
		return view;
	}
	
	
	@Override
	public NumberConstraint xc()
	{
		if (constraintX == null) constraintX = new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return x();
			}
		};
		
		return constraintX;
	}
	
	
	@Override
	public NumberConstraint yc()
	{
		if (constraintY == null) constraintY = new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return y();
			}
		};
		
		return constraintY;
	}
	
	
	@Override
	public NumberConstraint zc()
	{
		if (constraintZ == null) constraintZ = new NumberConstraint() {
			
			@Override
			public double getValue()
			{
				return z();
			}
		};
		
		return constraintZ;
	}
	
	
	@Override
	public V setX(double x)
	{
		return result(x, y(), z());
	}
	
	
	@Override
	public V setY(double y)
	{
		return result(x(), y, z());
	}
	
	
	@Override
	public V setZ(double z)
	{
		return result(x(), y(), z);
	}
	
	
	@Override
	public double size()
	{
		double x = x(), y = y(), z = z();
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	
	@Override
	public boolean isZero()
	{
		return x() == 0 && y() == 0 && z() == 0;
	}
	
	
	@Override
	public V add(Vec vec)
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
		return result(x, y, z);
	}
	
	
	@Override
	public double distTo(Vec point)
	{
		double dx = x() - point.x();
		double dy = y() - point.y();
		double dz = z() - point.z();
		
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
	
	
	@Override
	public V midTo(Vec point)
	{
		double dx = (point.x() - x()) * 0.5;
		double dy = (point.y() - y()) * 0.5;
		double dz = (point.z() - z()) * 0.5;
		
		return result(dx, dy, dz);
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
	public V mul(Vec vec)
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
	public V sub(Vec vec)
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
	public V vecTo(Vec point)
	{
		return result(point.x() - x(), point.y() - y(), point.z() - z());
	}
	
	
	@Override
	public V cross(Vec vec)
	{
		//@formatter:off
		return result(
				y() * vec.z() - z() * vec.y(),
				z() * vec.x() - x() * vec.z(),
				x() * vec.y() - y() * vec.x());
		//@formatter:on
	}
	
	
	@Override
	public double dot(Vec vec)
	{
		return x() * vec.x() + y() * vec.y() + z() * vec.z();
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
			
		double k = size / size();
		
		return mul(k);
	}
	
	
	@Override
	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = prime * result + Double.valueOf(x()).hashCode();
		result = prime * result + Double.valueOf(y()).hashCode();
		result = prime * result + Double.valueOf(z()).hashCode();
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Vec)) return false;
		Vec other = (Vec) obj;
		
		return x() == other.x() && y() == other.y() && z() == other.z();
	}
	
	@Override
	public String toString()
	{
		return String.format("[ %.2f ; %.2f ; %.2f ]", x(), y(), z());
	}
}
