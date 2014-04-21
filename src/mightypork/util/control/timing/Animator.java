package mightypork.util.control.timing;


import mightypork.util.annotations.DefaultImpl;
import mightypork.util.constraints.num.Num;
import mightypork.util.constraints.num.mutable.NumAnimated;
import mightypork.util.math.Easing;


public abstract class Animator extends Num implements Updateable, Pauseable {
	
	private final NumAnimated animator;
	private final Num num;
	
	
	public Animator(double period)
	{
		this(0, 1, period, Easing.LINEAR);
	}
	
	
	public Animator(double start, double end, double period)
	{
		this(start, end, period, Easing.LINEAR);
	}
	
	
	public Animator(double period, Easing easing)
	{
		this(0, 1, period, easing);
	}
	
	
	public Animator(double start, double end, double period, Easing easing)
	{
		animator = new NumAnimated(0, easing);
		animator.setDefaultDuration(period);
		
		this.num = animator.mul(end - start).add(start);
	}
	
	
	@Override
	public final void pause()
	{
		animator.pause();
	}
	
	
	@Override
	public final void resume()
	{
		animator.resume();
	}
	
	
	@Override
	public final boolean isPaused()
	{
		return animator.isPaused();
	}
	
	
	public final void reset()
	{
		animator.reset();
	}
	
	
	@Override
	public final void update(double delta)
	{
		animator.update(delta);
		if (animator.isFinished()) nextCycle(animator);
	}
	
	
	@DefaultImpl
	protected abstract void nextCycle(NumAnimated anim);
	
	
	@Override
	public final double value()
	{
		return num.value();
	}
}
