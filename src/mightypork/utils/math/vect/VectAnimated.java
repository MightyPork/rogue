package mightypork.utils.math.vect;


import mightypork.gamecore.control.timing.Pauseable;
import mightypork.gamecore.control.timing.Updateable;
import mightypork.utils.annotations.FactoryMethod;
import mightypork.utils.math.animation.AnimDouble;
import mightypork.utils.math.animation.Easing;


/**
 * 3D coordinated with support for transitions, mutable.
 * 
 * @author MightyPork
 */
public class VectAnimated extends VectMutable implements Pauseable, Updateable {
	
	/**
	 * Create an animated vector; This way different easing / settings can be
	 * specified for each coordinate.
	 * 
	 * @param x x animator
	 * @param y y animator
	 * @param z z animator
	 * @return animated mutable vector
	 */
	@FactoryMethod
	public static VectAnimated make(AnimDouble x, AnimDouble y, AnimDouble z)
	{
		return new VectAnimated(x, y, z);
	}
	
	
	/**
	 * Create an animated vector
	 * 
	 * @param start initial positioon
	 * @param easing animation easing
	 * @return animated mutable vector
	 */
	@FactoryMethod
	public static VectAnimated make(Vect start, Easing easing)
	{
		return new VectAnimated(start, easing);
	}
	
	
	/**
	 * Create an animated vector, initialized at 0,0,0
	 * 
	 * @param easing animation easing
	 * @return animated mutable vector
	 */
	@FactoryMethod
	public static VectAnimated make(Easing easing)
	{
		return new VectAnimated(Vect.ZERO, easing);
	}
	
	private final AnimDouble x, y, z;
	private double defaultDuration = 0;
	
	
	/**
	 * Create an animated vector; This way different easing / settings can be
	 * specified for each coordinate.
	 * 
	 * @param x x animator
	 * @param y y animator
	 * @param z z animator
	 */
	public VectAnimated(AnimDouble x, AnimDouble y, AnimDouble z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	/**
	 * Create an animated vector
	 * 
	 * @param start initial positioon
	 * @param easing animation easing
	 */
	public VectAnimated(Vect start, Easing easing) {
		x = new AnimDouble(start.x(), easing);
		y = new AnimDouble(start.y(), easing);
		z = new AnimDouble(start.z(), easing);
	}
	
	
	@Override
	public double x()
	{
		return x.value();
	}
	
	
	@Override
	public double y()
	{
		return y.value();
	}
	
	
	@Override
	public double z()
	{
		return z.value();
	}
	
	
	@Override
	public void setTo(double x, double y, double z)
	{
		setX(x);
		setY(y);
		setZ(z);
	}
	
	
	@Override
	public void setX(double x)
	{
		this.x.animate(x, defaultDuration);
	}
	
	
	@Override
	public void setY(double y)
	{
		this.y.animate(y, defaultDuration);
	}
	
	
	@Override
	public void setZ(double z)
	{
		this.z.animate(z, defaultDuration);
	}
	
	
	/**
	 * Add offset with animation
	 * 
	 * @param offset added offset
	 * @param duration animation time (seconds)
	 */
	public void add(Vect offset, double duration)
	{
		animate(this.add(offset), duration);
	}
	
	
	/**
	 * Animate to given coordinates in given amount of time
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param duration animation time (seconds)
	 * @return this
	 */
	public VectAnimated animate(double x, double y, double z, double duration)
	{
		this.x.animate(x, duration);
		this.y.animate(y, duration);
		this.z.animate(z, duration);
		return this;
	}
	
	
	/**
	 * Animate to given vec in given amount of time.
	 * 
	 * @param target target (only it's current value will be used)
	 * @param duration animation time (seconds)
	 * @return this
	 */
	public VectAnimated animate(Vect target, double duration)
	{
		animate(target.x(), target.y(), target.z(), duration);
		return this;
	}
	
	
	/**
	 * @return the default duration (seconds)
	 */
	public double getDefaultDuration()
	{
		return defaultDuration;
	}
	
	
	/**
	 * Set default animation duration (when changed without using animate())
	 * 
	 * @param defaultDuration default duration (seconds)
	 */
	public void setDefaultDuration(double defaultDuration)
	{
		this.defaultDuration = defaultDuration;
	}
	
	
	@Override
	public void update(double delta)
	{
		x.update(delta);
		y.update(delta);
		z.update(delta);
	}
	
	
	@Override
	public void pause()
	{
		x.pause();
		y.pause();
		z.pause();
	}
	
	
	@Override
	public void resume()
	{
		x.resume();
		y.resume();
		z.resume();
	}
	
	
	@Override
	public boolean isPaused()
	{
		return x.isPaused(); // BÚNO
	}
	
	
	/**
	 * @return true if the animation is finished
	 */
	public boolean isFinished()
	{
		return x.isFinished(); // BÚNO
	}
	
	
	/**
	 * @return current animation duration
	 */
	public double getDuration()
	{
		return x.getDuration(); // BÚNO
	}
	
	
	/**
	 * @return elapsed time since the start of the animation
	 */
	public double getElapsed()
	{
		return x.getElapsed(); // BÚNO
	}
	
	
	/**
	 * @return animation progress (elapsed / duration)
	 */
	public double getProgress()
	{
		return x.getProgress(); // BÚNO
	}
	
	
	/**
	 * Set easing for all three coordinates
	 * 
	 * @param easing
	 */
	public void setEasing(Easing easing)
	{
		x.setEasing(easing);
		y.setEasing(easing);
		z.setEasing(easing);
	}
	
}
