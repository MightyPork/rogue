package mightypork.gamecore.audio;


import mightypork.utils.math.Calc;
import mightypork.utils.struct.Mutable;


/**
 * Mutable volume 0-1
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class Volume extends Mutable<Double> {
	
	/**
	 * @param d initial value
	 */
	public Volume(Double d) {
		super(d);
	}
	
	
	@Override
	public void set(Double d)
	{
		super.set(Calc.clamp(d, 0, 1));
	}
	
}
