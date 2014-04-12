package mightypork.utils.math.coord;


import mightypork.gamecore.control.timing.Pauseable;
import mightypork.gamecore.control.timing.Updateable;
import mightypork.utils.math.animation.AnimDouble;
import mightypork.utils.math.animation.Easing;


/**
 * 3D coordinated with support for transitions, mutable.
 * 
 * @author MightyPork
 */
public class AnimCoord extends VecMutableImpl implements Pauseable, Updateable {
	
	private final AnimDouble x, y, z;
	
	
	public AnimCoord(AnimDouble x, AnimDouble y, AnimDouble z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	public AnimCoord(Vec start, Easing easing) {
		x = new AnimDouble(start.x(), easing);
		y = new AnimDouble(start.y(), easing);
		z = new AnimDouble(start.z(), easing);
	}
	
	
	@Override
	public double x()
	{
		return x.now();
	}
	
	
	@Override
	public double y()
	{
		return y.now();
	}
	
	
	@Override
	public double z()
	{
		return z.now();
	}
	
	
	@Override
	public AnimCoord result(double x, double y, double z)
	{
		this.x.setTo(x);
		this.y.setTo(y);
		this.z.setTo(z);
		
		return this;
	}
	
	
	public AnimCoord add(Vec offset, double speed)
	{
		animate(offset.x() - x(), offset.y() - y(), offset.z() - z(), speed);
		return this;
	}
	
	
	public AnimCoord animate(double x, double y, double z, double duration)
	{
		this.x.animate(x, duration);
		this.y.animate(y, duration);
		this.z.animate(z, duration);
		return this;
	}
	
	
	public AnimCoord animate(Vec target, double duration)
	{
		x.animate(target.x(), duration);
		y.animate(target.y(), duration);
		z.animate(target.z(), duration);
		return this;
	}
	
	
	public void animateWithSpeed(Vec target, double unitsPerSecond)
	{
		final double dist = distTo(target);
		final double duration = dist / unitsPerSecond;
		animate(target, duration);
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
		return x.isPaused(); // BUNO
	}
	
	
	public boolean isFinished()
	{
		return x.isFinished(); // BUNO
	}
	
	
	public double getDuration()
	{
		return x.getDuration(); // BUNO
	}
	
	
	public double getElapsed()
	{
		return x.getElapsed(); // BUNO
	}
	
	
	public double getProgress()
	{
		return x.getProgress(); // BUNO
	}
}
