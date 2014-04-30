package mightypork.gamecore.util.math.timing;


import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.util.math.constraints.num.mutable.NumAnimated;


/**
 * Delayed runnable controlled by delta timing.
 * 
 * @author MightyPork
 */
public abstract class TimedTask implements Runnable, Updateable {
	
	private final NumAnimated timer = new NumAnimated(0);
	private boolean running = false;
	
	
	@Override
	public void update(double delta)
	{
		if (running) {
			timer.update(delta);
			if (timer.isFinished()) {
				running = false;
				run();
			}
		}
	}
	
	
	public void start(double seconds)
	{
		timer.reset();
		timer.animate(1, seconds);
		running = true;
	}
	
	
	public void stop()
	{
		running = false;
		timer.reset();
	}
	
}
