package mightypork.rogue.world.entity;


import mightypork.gamecore.util.math.timing.TaskRepeater;
import mightypork.ion.IonBundle;
import mightypork.ion.IonObjBundled;


public abstract class AiTimer extends TaskRepeater implements IonObjBundled {
	
	public AiTimer(double duration)
	{
		super(duration);
	}
	
	
	@Override
	public abstract void run();
	
	
	@Override
	public void load(IonBundle bundle)
	{
		final boolean wasPaused = bundle.get("paused", isPaused());
		if (wasPaused) {
			pause();
		} else {
			resume();
		}
		
		setProgress(bundle.get("progress", getProgress()));
		setDuration(bundle.get("duration", getDuration()));
	}
	
	
	@Override
	public void save(IonBundle bundle)
	{
		bundle.put("paused", isPaused());
		bundle.put("progress", getProgress());
		bundle.put("duration", getDuration());
	}
	
}
