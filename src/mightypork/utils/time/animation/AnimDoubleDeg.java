package mightypork.utils.time.animation;


import mightypork.utils.math.Calc;
import mightypork.utils.math.Calc.Deg;
import mightypork.utils.math.easing.Easing;


/**
 * Degree animator
 * 
 * @author MightyPork
 */
public class AnimDoubleDeg extends AnimDouble {
	
	public AnimDoubleDeg(AnimDouble other) {
		super(other);
	}
	
	
	public AnimDoubleDeg(double value) {
		super(value);
	}
	
	
	public AnimDoubleDeg(double value, Easing easing) {
		super(value, easing);
	}
	
	
	@Override
	public double getCurrentValue()
	{
		if (duration == 0) return Deg.norm(to);
		return Calc.interpolateDeg(from, to, (elapsedTime / duration), easing);
	}
	
	
	@Override
	protected double getProgressFromValue(double value)
	{
		double whole = Deg.diff(from, to);
		if (Deg.diff(value, from) < whole && Deg.diff(value, to) < whole) {
			double partial = Deg.diff(from, value);
			return partial / whole;
		}
		
		return 0;
	}
}
