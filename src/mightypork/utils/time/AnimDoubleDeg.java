package mightypork.utils.time;


import mightypork.utils.math.Calc;


/**
 * Double which supports delta timing
 * 
 * @author MightyPork
 */
public class AnimDoubleDeg extends AnimDouble {

	/**
	 * new AnimDoubleDeg
	 * 
	 * @param d value
	 */
	public AnimDoubleDeg(double d) {
		super(d);
	}


	/**
	 * Get value at delta time
	 * 
	 * @return the value
	 */
	@Override
	public double getCurrentValue()
	{
		return Calc.interpolateDeg(startValue, endValue, elapsedTime / duration);
	}


	@Override
	public void fadeTo(double from, double to, double time)
	{
		throw new UnsupportedOperationException("Cannot fadeTo in AnimDoubleDeg. Use animate() instead.");
	}
}
