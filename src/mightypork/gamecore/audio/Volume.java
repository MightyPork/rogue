package mightypork.gamecore.audio;


import mightypork.util.math.Calc;
import mightypork.util.objects.Mutable;


/**
 * Mutable volume 0-1
 * 
 * @author MightyPork
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
		super.set(Calc.clampd(d, 0, 1));
	}
	
}
