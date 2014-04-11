package mightypork.utils.math.animation;


import mightypork.gamecore.control.timing.Pauseable;
import mightypork.gamecore.control.timing.Updateable;
import mightypork.utils.math.Calc;
import mightypork.utils.math.constraints.NumberConstraint;


/**
 * Double which supports delta timing
 * 
 * @author MightyPork
 */
public class AnimDouble implements Updateable, Pauseable, NumberConstraint {
	
	/** target double */
	protected double to = 0;
	
	/** last tick double */
	protected double from = 0;
	
	/** how long the transition should last */
	protected double duration = 0;
	
	/** current anim time */
	protected double elapsedTime = 0;
	
	/** True if this animator is paused */
	protected boolean paused = false;
	
	/** Easing fn */
	protected Easing easing = Easing.LINEAR;
	
	
	/**
	 * Create linear animator
	 * 
	 * @param value initial value
	 */
	public AnimDouble(double value) {
		setTo(value);
	}
	
	
	/**
	 * Create animator with easing
	 * 
	 * @param value initial value
	 * @param easing easing function
	 */
	public AnimDouble(double value, Easing easing) {
		setTo(value);
		setEasing(easing);
	}
	
	
	/**
	 * Create as copy of another
	 * 
	 * @param other other animator
	 */
	public AnimDouble(AnimDouble other) {
		setTo(other);
	}
	
	
	/**
	 * @return easing function
	 */
	public Easing getEasing()
	{
		return easing;
	}
	
	
	/**
	 * @param easing easing function
	 */
	public void setEasing(Easing easing)
	{
		this.easing = easing;
	}
	
	
	/**
	 * Get start value
	 * 
	 * @return number
	 */
	public double getGetStart()
	{
		return from;
	}
	
	
	/**
	 * Get end value
	 * 
	 * @return number
	 */
	public double getEnd()
	{
		return to;
	}
	
	
	/**
	 * Get value at delta time
	 * 
	 * @return the value
	 */
	public double now()
	{
		if (duration == 0) return to;
		return Calc.interpolate(from, to, (elapsedTime / duration), easing);
	}
	
	
	@Override
	public double getValue()
	{
		return now();
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
		if (paused) return;
		
		elapsedTime = Calc.clampd(elapsedTime + delta, 0, duration);
		if (isFinished()) {
			duration = 0;
			elapsedTime = 0;
			from = to;
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
		from = to = value;
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
		this.from = other.from;
		this.to = other.to;
		this.duration = other.duration;
		this.elapsedTime = other.elapsedTime;
		this.paused = other.paused;
		this.easing = other.easing;
	}
	
	
	/**
	 * Animate between two states, start from current value (if it's in between)
	 * 
	 * @param from start value
	 * @param to target state
	 * @param time animation time (secs)
	 */
	public void animate(double from, double to, double time)
	{
		final double current = now();
		
		this.from = from;
		this.to = to;
		
		final double progress = getProgressFromValue(current);
		
		this.from = (progress > 0 ? current : from);
		
		this.duration = time * (1 - progress);
		this.elapsedTime = 0;
	}
	
	
	/**
	 * Get progress already elapsed based on current value.<br>
	 * Used to resume animation from current point in fading etc.
	 * 
	 * @param value current value
	 * @return progress ratio 0-1
	 */
	protected double getProgressFromValue(double value)
	{
		double p = 0;
		
		if (from == to) return 0;
		
		if (value >= from && value <= to) { // up
			p = ((value - from) / (to - from));
		} else if (value >= to && value <= from) { // down
			p = ((from - value) / (from - to));
		}
		
		return p;
	}
	
	
	/**
	 * Animate to a value from current value
	 * 
	 * @param to target state
	 * @param duration animation duration (speeds)
	 */
	public void animate(double to, double duration)
	{
		this.from = now();
		this.to = to;
		this.duration = duration;
		this.elapsedTime = 0;
	}
	
	
	/**
	 * Animate 0 to 1
	 * 
	 * @param time animation time (secs)
	 */
	public void fadeIn(double time)
	{
		animate(0, 1, time);
	}
	
	
	/**
	 * Animate 1 to 0
	 * 
	 * @param time animation time (secs)
	 */
	public void fadeOut(double time)
	{
		animate(1, 0, time);
	}
	
	
	/**
	 * Make a copy
	 * 
	 * @return copy
	 */
	public AnimDouble copy()
	{
		return new AnimDouble(this);
	}
	
	
	@Override
	public String toString()
	{
		return "Animation(" + from + " -> " + to + ", t=" + duration + "s, elapsed=" + elapsedTime + "s)";
	}
	
	
	/**
	 * Set to zero and stop animation
	 */
	public void clear()
	{
		from = to = 0;
		elapsedTime = 0;
		duration = 0;
		paused = false;
	}
	
	
	/**
	 * Stop animation, keep current value
	 */
	public void stop()
	{
		from = to = now();
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
