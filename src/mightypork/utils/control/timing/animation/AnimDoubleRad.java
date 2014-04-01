package mightypork.utils.control.timing.animation;


import mightypork.utils.math.Calc;
import mightypork.utils.math.Calc.Rad;
import mightypork.utils.math.easing.Easing;


/**
 * Radians animator
 * 
 * @author MightyPork
 */
public class AnimDoubleRad extends AnimDouble {
	
	public AnimDoubleRad(AnimDouble other) {
		super(other);
	}
	
	
	public AnimDoubleRad(double value) {
		super(value);
	}
	
	
	public AnimDoubleRad(double value, Easing easing) {
		super(value, easing);
	}
	
	
	@Override
	public double getCurrentValue()
	{
		if (duration == 0) return Rad.norm(to);
		return Calc.interpolateRad(from, to, (elapsedTime / duration), easing);
	}
	
	
	@Override
	protected double getProgressFromValue(double value)
	{
		double whole = Rad.diff(from, to);
		if (Rad.diff(value, from) < whole && Rad.diff(value, to) < whole) {
			double partial = Rad.diff(from, value);
			return partial / whole;
		}
		
		return 0;
	}
}
