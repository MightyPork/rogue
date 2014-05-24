package mightypork.gamecore.util.math.timing.animation;


import mightypork.gamecore.util.math.Calc;
import mightypork.gamecore.util.math.Calc.Deg;
import mightypork.gamecore.util.math.Easing;


/**
 * Degree animator
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class NumAnimatedDeg extends NumAnimated {
	
	public NumAnimatedDeg(NumAnimated other)
	{
		super(other);
	}
	
	
	public NumAnimatedDeg(double value)
	{
		super(value);
	}
	
	
	public NumAnimatedDeg(double value, Easing easing)
	{
		super(value, easing);
	}
	
	
	@Override
	public double value()
	{
		if (duration == 0) return Deg.norm(to);
		return Calc.interpolateDeg(from, to, (elapsedTime / duration), easingCurrent);
	}
	
	
	@Override
	protected double getProgressFromValue(double value)
	{
		final double whole = Deg.diff(from, to);
		if (Deg.diff(value, from) < whole && Deg.diff(value, to) < whole) {
			final double partial = Deg.diff(from, value);
			return partial / whole;
		}
		
		return 0;
	}
}
