package mightypork.utils.math.vect;


import mightypork.utils.math.constraints.NumBound;


public abstract class AbstractVect implements Vect {
	
	private VectView proxy;
	private NumBound xc;
	private NumBound yc;
	private NumBound zc;
	
	
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
	public final NumBound xc()
	{
		if (xc == null) xc = new NumBound() {
			
			@Override
			public double getValue()
			{
				return x();
			}
		};
		
		return xc;
	}
	
	
	@Override
	public final NumBound yc()
	{
		if (yc == null) yc = new NumBound() {
			
			@Override
			public double getValue()
			{
				return y();
			}
		};
		
		return yc;
	}
	
	
	@Override
	public final NumBound zc()
	{
		if (zc == null) zc = new NumBound() {
			
			@Override
			public double getValue()
			{
				return z();
			}
		};
		
		return zc;
	}
	
	
	@Override
	public final Vect getVect()
	{
		return this;
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
	public VectVal copy()
	{
		// must NOT call VectVal.make, it'd cause infinite recursion.
		return new VectVal(this);
	}
	
	
	@Override
	public VectView view()
	{
		// must NOT call VectView.make, it'd cause infinite recursion.
		
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
		return String.format("(%.1f|%.1f|%.1f)", x(), y(), z());
	}
}
