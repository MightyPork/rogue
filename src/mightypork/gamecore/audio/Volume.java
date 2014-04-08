package mightypork.gamecore.audio;


import mightypork.utils.math.Calc;
import mightypork.utils.objects.Mutable;


public class Volume extends Mutable<Double> {
	
	public Volume(Double d) {
		super(d);
	}
	
	
	@Override
	public void set(Double d)
	{
		super.set(Calc.clampd(d, 0, 1));
	}
	
}
