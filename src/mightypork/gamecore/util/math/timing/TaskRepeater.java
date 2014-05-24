package mightypork.gamecore.util.math.timing;


import mightypork.gamecore.gui.Enableable;
import mightypork.gamecore.util.math.timing.animation.AnimatorRewind;
import mightypork.gamecore.util.math.timing.animation.NumAnimated;


public abstract class TaskRepeater extends AnimatorRewind implements Runnable, Enableable {
	
	private boolean enabled = true;
	
	
	public TaskRepeater(double period)
	{
		super(period);
	}
	
	
	@Override
	protected void nextCycle(NumAnimated anim)
	{
		run();
		super.nextCycle(anim);
	}
	
	
	@Override
	public void setEnabled(boolean yes)
	{
		this.enabled = yes;
	}
	
	
	@Override
	public boolean isEnabled()
	{
		return enabled;
	}
	
	
	@Override
	public void update(double delta)
	{
		if (!enabled) return;
		
		super.update(delta);
	}
	
}
