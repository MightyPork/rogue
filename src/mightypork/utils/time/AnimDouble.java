package mightypork.utils.time;


import mightypork.utils.math.Calc;


/**
 * Double which supports delta timing
 * 
 * @author MightyPork
 */
public class AnimDouble implements Updateable, Pauseable {

	/** target double */
	protected double endValue = 0;

	/** last tick double */
	protected double startValue = 0;

	/** how long the transition should last */
	protected double duration = 0;

	/** current anim time */
	protected double elapsedTime = 0;

	/** True if this animator is paused */
	protected boolean paused = false;


	/**
	 * @param value value
	 */
	public AnimDouble(double value) {
		setTo(value);
	}


	public AnimDouble(AnimDouble other) {
		setTo(other);
	}


	/**
	 * Get start value
	 * 
	 * @return number
	 */
	public double getStartValue()
	{
		return startValue;
	}


	/**
	 * Get value at delta time
	 * 
	 * @return the value
	 */
	public double getCurrentValue()
	{
		if (duration == 0) return endValue;
		return Calc.interpolate(startValue, endValue, elapsedTime / duration);
	}


	/**
	 * Get end value
	 * 
	 * @return number
	 */
	public double getEndValue()
	{
		return endValue;
	}


	/**
	 * Get how much of the animation is already finished
	 * 
	 * @return completion ratio (0 to 1)
	 */
	public double getProgress()
	{
		if (duration == 0) return 1;
		return elapsedTime / duration;
	}


	@Override
	public void update(double delta)
	{
		elapsedTime = Calc.clampd(elapsedTime + delta, 0, duration);
		if (isFinished()) {
			duration = 0;
			elapsedTime = 0;
			startValue = endValue;
		}
	}


	/**
	 * Get if animation is finished
	 * 
	 * @return is finished
	 */
	public boolean isFinished()
	{
		return duration == 0 || elapsedTime >= duration;
	}


	/**
	 * Set to a value (without animation)
	 * 
	 * @param value
	 */
	public void setTo(double value)
	{
		startValue = endValue = value;
		elapsedTime = 0;
		duration = 0;
	}


	/**
	 * Copy other
	 * 
	 * @param other
	 */
	public void setTo(AnimDouble other)
	{
		this.startValue = other.startValue;
		this.endValue = other.endValue;
		this.duration = other.duration;
		this.elapsedTime = other.elapsedTime;
		this.paused = other.paused;
	}


	/**
	 * Animate between two states, discard current state
	 * 
	 * @param from initial state
	 * @param to target state
	 * @param time animation time (secs)
	 */
	public void animate(double from, double to, double time)
	{
		startValue = from;
		endValue = to;
		duration = time;
		elapsedTime = 0;
	}


	/**
	 * Animate between two states, start from current value (if it's in between)
	 * 
	 * @param from start value
	 * @param to target state
	 * @param time animation time (secs)
	 */
	public void fadeTo(double from, double to, double time)
	{
		double current = getCurrentValue();

		startValue = from;
		endValue = to;
		duration = time;
		elapsedTime = 0;

		// if in between, pick up from where it is
		if (current >= from && current <= to) { // up
			elapsedTime = ((current - from) / (to - from)) * time;
		} else if (current >= to && current <= from) { // down
			elapsedTime = ((from - current) / (from - to)) * time;
		}
	}


	/**
	 * Animate 0 to 1
	 * 
	 * @param time animation time (secs)
	 */
	public void fadeIn(double time)
	{
		fadeTo(0, 1, time);
	}


	/**
	 * Animate 1 to 0
	 * 
	 * @param time animation time (secs)
	 */
	public void fadeOut(double time)
	{
		fadeTo(1, 0, time);
	}


	/**
	 * Make a copy
	 * 
	 * @return copy
	 */
	public Pauseable getCopy()
	{
		return new AnimDouble(this);
	}


	@Override
	public String toString()
	{
		return "Animation(" + startValue + " -> " + endValue + ", t=" + duration + "s, elapsed=" + elapsedTime + "s)";
	}


	/**
	 * Set to zero and stop animation
	 */
	public void clear()
	{
		startValue = endValue = 0;
		elapsedTime = 0;
		duration = 0;
		paused = false;
	}


	/**
	 * Stop animation, keep current value
	 */
	public void stop()
	{
		startValue = endValue = getCurrentValue();
		elapsedTime = 0;
		duration = 0;
	}


	@Override
	public void pause()
	{
		paused = true;
	}


	@Override
	public void resume()
	{
		paused = false;
	}


	@Override
	public boolean isPaused()
	{
		return paused;
	}
}
