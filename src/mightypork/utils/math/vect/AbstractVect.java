package mightypork.utils.math.vect;




public abstract class AbstractVect implements Vect {
	
	private VectView proxy;
	
	
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
	public final VectView getVect()
	{
		return view();
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
