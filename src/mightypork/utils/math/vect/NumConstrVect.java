package mightypork.utils.math.vect;


import mightypork.utils.math.constraints.NumberBound;


/**
 * Coord view composed of given {@link NumberBound}s, using their current
 * values.
 * 
 * @author MightyPork
 */
class NumConstrVect extends VectView {
	
	private final NumberBound constrX;
	private final NumberBound constrY;
	private final NumberBound constrZ;
	
	
	public NumConstrVect(NumberBound x, NumberBound y, NumberBound z) {
		this.constrX = x;
		this.constrY = y;
		this.constrZ = z;
	}
	
	
	public NumConstrVect(NumberBound x, NumberBound y) {
		this.constrX = x;
		this.constrY = y;
		this.constrZ = NumberBound.ZERO;
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
