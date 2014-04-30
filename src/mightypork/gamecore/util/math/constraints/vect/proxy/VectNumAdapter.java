package mightypork.gamecore.util.math.constraints.vect.proxy;


import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.num.proxy.NumBound;
import mightypork.gamecore.util.math.constraints.vect.Vect;


/**
 * Coord view composed of given {@link NumBound}s, using their current values.
 * 
 * @author MightyPork
 */
public class VectNumAdapter extends Vect {
	
	private final Num constrX;
	private final Num constrY;
	private final Num constrZ;
	
	
	public VectNumAdapter(Num x, Num y, Num z)
	{
		this.constrX = x;
		this.constrY = y;
		this.constrZ = z;
	}
	
	
	public VectNumAdapter(Num x, Num y)
	{
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
