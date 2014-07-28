package mightypork.rogue.world.entity;


import mightypork.utils.ion.IonBundled;
import mightypork.utils.ion.IonDataBundle;
import mightypork.utils.math.timing.TaskRepeater;


public abstract class AiTimer extends TaskRepeater implements IonBundled {

	public AiTimer(double duration)
	{
		super(duration);
	}


	@Override
	public abstract void run();


	@Override
	public void load(IonDataBundle bundle)
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
	public void save(IonDataBundle bundle)
	{
		bundle.put("paused", isPaused());
		bundle.put("progress", getProgress());
		bundle.put("duration", getDuration());
	}

}
