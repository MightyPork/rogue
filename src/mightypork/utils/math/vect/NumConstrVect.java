package mightypork.utils.math.vect;


import mightypork.utils.math.constraints.NumberConstraint;


/**
 * Coord view composed of given {@link NumberConstraint}s, using their current
 * values.
 * 
 * @author MightyPork
 */
class NumConstrVect extends VectView {
	
	private final NumberConstraint constrX;
	private final NumberConstraint constrY;
	private final NumberConstraint constrZ;
	
	
	public NumConstrVect(NumberConstraint x, NumberConstraint y, NumberConstraint z) {
		this.constrX = x;
		this.constrY = y;
		this.constrZ = z;
	}
	
	
	public NumConstrVect(NumberConstraint x, NumberConstraint y) {
		this.constrX = x;
		this.constrY = y;
		this.constrZ = NumberConstraint.ZERO;
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
