package mightypork.utils.math.constraints.vect;


public class VectDigest {
	
	public final VectConst source;
	
	public final double x;
	public final double y;
	public final double z;
	
	
	public VectDigest(Vect vect) {
		this.source = vect.freeze();
		
		this.x = vect.x();
		this.y = vect.y();
		this.z = vect.z();
	}
}
