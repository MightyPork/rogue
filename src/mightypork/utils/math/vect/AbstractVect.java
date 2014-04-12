package mightypork.utils.math.vect;


import mightypork.utils.math.constraints.NumberBound;


public abstract class AbstractVect implements Vect {
	
	private VectView proxy;
	private NumberBound xc;
	private NumberBound yc;
	private NumberBound zc;
	
	
	@Override
	public abstract double x();
	
	
	@Override
	public abstract double y();
	
	
	@Override
	public abstract double z();
	
	
	@Override
	public final int xi()
	{
		return (int) Math.round(x());
	}
	
	
	@Override
	public final int yi()
	{
		return (int) Math.round(y());
	}
	
	
	@Override
	public final int zi()
	{
		return (int) Math.round(z());
	}
	
	
	@Override
	public final NumberBound xc()
	{
		if (xc == null) xc = new NumberBound() {
			
			@Override
			public double getValue()
			{
				return x();
			}
		};
		
		return xc;
	}
	
	
	@Override
	public final NumberBound yc()
	{
		if (yc == null) yc = new NumberBound() {
			
			@Override
			public double getValue()
			{
				return y();
			}
		};
		
		return yc;
	}
	
	
	@Override
	public final NumberBound zc()
	{
		if (zc == null) zc = new NumberBound() {
			
			@Override
			public double getValue()
			{
				return z();
			}
		};
		
		return zc;
	}
	
	
	@Override
	public final double size()
	{
		final double x = x(), y = y(), z = z();
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	
	@Override
	public final boolean isZero()
	{
		return x() == 0 && y() == 0 && z() == 0;
	}
	
	
	@Override
	public VectVal getValue()
	{
		return new VectVal(this);
	}
	
	
	@Override
	public final double distTo(Vect point)
	{
		final double dx = x() - point.x();
		final double dy = y() - point.y();
		final double dz = z() - point.z();
		
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
	
	
	@Override
	public final VectVal midTo(Vect point)
	{
		final double dx = (point.x() - x()) * 0.5;
		final double dy = (point.y() - y()) * 0.5;
		final double dz = (point.z() - z()) * 0.5;
		
		return VectVal.make(dx, dy, dz);
	}
	
	
	@Override
	public final VectVal vecTo(Vect point)
	{
		return VectVal.make(point.x() - x(), point.y() - y(), point.z() - z());
	}
	
	
	@Override
	public final VectVal cross(Vect vec)
	{
		//@formatter:off
		return VectVal.make(
				y() * vec.z() - z() * vec.y(),
				z() * vec.x() - x() * vec.z(),
				x() * vec.y() - y() * vec.x());
		//@formatter:on
	}
	
	
	@Override
	public final double dot(Vect vec)
	{
		return x() * vec.x() + y() * vec.y() + z() * vec.z();
	}
	
	
	@Override
	public VectView getView()
	{
		if (proxy == null) proxy = new VectProxy(this);
		
		return proxy;
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
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
		if (!(obj instanceof Vect)) return false;
		final Vect other = (Vect) obj;
		
		return x() == other.x() && y() == other.y() && z() == other.z();
	}
	
	
	@Override
	public String toString()
	{
		return String.format("(%.1f %.1f %.1f)", x(), y(), z());
	}
}
