package mightypork.util.constraints.vect.caching;


import mightypork.util.constraints.vect.Vect;


public class VectDigest {
	
	public final double x;
	public final double y;
	public final double z;
	
	
	public VectDigest(Vect vect) {
		this.x = vect.x();
		this.y = vect.y();
		this.z = vect.z();
	}
	
	
	@Override
	public String toString()
	{
		return String.format("Vect(%.1f, %.1f, %.1f)", x, y, z);
	}
}
