package mightypork.gamecore.util.math.timing;


import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.math.Calc;
import mightypork.gamecore.util.math.Easing;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.num.mutable.NumAnimated;


public abstract class Animator extends Num implements Updateable, Pauseable {
	
	private final NumAnimated numAnim;
	private final Num num;
	private final double highValue;
	private final double lowValue;
	
	
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
		numAnim = new NumAnimated(0, easing);
		numAnim.setDefaultDuration(period);
		
		this.lowValue = start;
		this.highValue = end;
		
		this.num = numAnim.mul(end - start).add(start);
	}
	
	
	@Override
	public void pause()
	{
		numAnim.pause();
	}
	
	
	public void start()
	{
		resume();
	}
	
	
	@Override
	public void resume()
	{
		numAnim.resume();
	}
	
	
	@Override
	public boolean isPaused()
	{
		return numAnim.isPaused();
	}
	
	
	public void reset()
	{
		numAnim.reset();
	}
	
	
	public void restart()
	{
		reset();
		nextCycle(numAnim);
	}
	
	
	public void setDuration(double secs)
	{
		numAnim.setDefaultDuration(secs);
	}
	
	
	public double getDuration()
	{
		return numAnim.getDefaultDuration();
	}
	
	
	@Override
	public void update(double delta)
	{
		numAnim.update(delta);
		if (numAnim.isFinished()) nextCycle(numAnim);
	}
	
	
	@DefaultImpl
	protected abstract void nextCycle(NumAnimated anim);
	
	
	@Override
	public double value()
	{
		return num.value();
	}
	
	
	public void setProgress(double value)
	{
		final double target = numAnim.getEnd();
		numAnim.setTo(Calc.clamp(value, lowValue, highValue));
		numAnim.animate(target, numAnim.getDefaultDuration());
	}
	
	
	public double getProgress()
	{
		return numAnim.value();
	}
}
