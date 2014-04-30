package mightypork.rogue.world.entity;


import java.io.IOException;

import mightypork.gamecore.util.ion.IonBundle;
import mightypork.gamecore.util.ion.IonObjBundled;
import mightypork.gamecore.util.math.timing.TaskRepeater;


public abstract class AiTimer extends TaskRepeater implements IonObjBundled {
	
	public AiTimer(double duration)
	{
		super(duration);
	}
	
	
	@Override
	public abstract void run();
	
	
	@Override
	public void load(IonBundle bundle) throws IOException
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
	public void save(IonBundle bundle) throws IOException
	{
		bundle.put("paused", isPaused());
		bundle.put("progress", getProgress());
		bundle.put("duration", getDuration());
	}
	
}
