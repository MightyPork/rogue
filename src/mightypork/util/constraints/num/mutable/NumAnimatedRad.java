package mightypork.util.constraints.num.mutable;


import mightypork.util.math.Calc;
import mightypork.util.math.Calc.Rad;
import mightypork.util.math.Easing;


/**
 * Radians animator
 * 
 * @author MightyPork
 */
public class NumAnimatedRad extends NumAnimated {
	
	public NumAnimatedRad(NumAnimated other) {
		super(other);
	}
	
	
	public NumAnimatedRad(double value) {
		super(value);
	}
	
	
	public NumAnimatedRad(double value, Easing easing) {
		super(value, easing);
	}
	
	
	@Override
	public double value()
	{
		if (duration == 0) return Rad.norm(to);
		return Calc.interpolateRad(from, to, (elapsedTime / duration), easingCurrent);
	}
	
	
	@Override
	protected double getProgressFromValue(double value)
	{
		final double whole = Rad.diff(from, to);
		if (Rad.diff(value, from) < whole && Rad.diff(value, to) < whole) {
			final double partial = Rad.diff(from, value);
			return partial / whole;
		}
		
		return 0;
	}
}
