package mightypork.gamecore.resources.audio;


import mightypork.gamecore.util.math.Calc;
import mightypork.gamecore.util.objects.Mutable;


/**
 * Mutable volume 0-1
 * 
 * @author Ondřej Hruška
 */
public class Volume extends Mutable<Double> {
	
	/**
	 * @param d initial value
	 */
	public Volume(Double d)
	{
		super(d);
	}
	
	
	@Override
	public void set(Double d)
	{
		super.set(Calc.clamp(d, 0, 1));
	}
	
}
