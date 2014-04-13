package mightypork.utils.math.vect;


import mightypork.utils.math.constraints.NumBound;


/**
 * Coord view composed of given {@link NumBound}s, using their current values.
 * 
 * @author MightyPork
 */
class NumConstrVect extends VectView {
	
	private final NumBound constrX;
	private final NumBound constrY;
	private final NumBound constrZ;
	
	
	public NumConstrVect(NumBound x, NumBound y, NumBound z) {
		this.constrX = x;
		this.constrY = y;
		this.constrZ = z;
	}
	
	
	public NumConstrVect(NumBound x, NumBound y) {
		this.constrX = x;
		this.constrY = y;
		this.constrZ = NumBound.ZERO;
	}
	
	
	@Override
	public double x()
	{
		return constrX.getValue();
	}
	
	
	@Override
	public double y()
	{
		return constrY.getValue();
	}
	
	
	@Override
	public double z()
	{
		return constrZ.getValue();
	}
	
}
