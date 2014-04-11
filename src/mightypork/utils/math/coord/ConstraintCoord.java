package mightypork.utils.math.coord;


import mightypork.utils.math.constraints.NumberConstraint;


/**
 * Coord view composed of given {@link NumberConstraint}s, using their current
 * values.<br>
 * Operations yield a new {@link MutableCoord} with the result.
 * 
 * @author MightyPork
 */
public class ConstraintCoord extends VecView {
	
	private final NumberConstraint constrX;
	private final NumberConstraint constrY;
	private final NumberConstraint constrZ;
	
	
	public ConstraintCoord(NumberConstraint x, NumberConstraint y, NumberConstraint z) {
		this.constrX = x;
		this.constrY = y;
		this.constrZ = z;
	}
	
	
	public ConstraintCoord(NumberConstraint x, NumberConstraint y) {
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
