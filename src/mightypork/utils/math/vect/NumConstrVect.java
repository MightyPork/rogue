package mightypork.utils.math.vect;


import mightypork.utils.math.constraints.NumBound;
import mightypork.utils.math.num.Num;


/**
 * Coord view composed of given {@link NumBound}s, using their current values.
 * 
 * @author MightyPork
 */
class NumConstrVect extends VectView {
	
	private final Num constrX;
	private final Num constrY;
	private final Num constrZ;
	
	
	public NumConstrVect(Num x, Num y, Num z) {
		this.constrX = x;
		this.constrY = y;
		this.constrZ = z;
	}
	
	
	public NumConstrVect(Num x, Num y) {
		this.constrX = x;
		this.constrY = y;
		this.constrZ = Num.ZERO;
	}
	
	
	@Override
	public double x()
	{
		return constrX.value();
	}
	
	
	@Override
	public double y()
	{
		return constrY.value();
	}
	
	
	@Override
	public double z()
	{
		return constrZ.value();
	}
	
}
