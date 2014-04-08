package mightypork.gamecore.control;


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import mightypork.gamecore.control.bus.events.MainLoopTaskRequest;
import mightypork.gamecore.control.bus.events.UpdateEvent;
import mightypork.gamecore.control.timing.TimerDelta;


public abstract class GameLoop extends AppModule implements MainLoopTaskRequest.Listener {
	
	private final Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<Runnable>();
	/** timer */
	private TimerDelta timer;
	private boolean running = true;
	
	
	public GameLoop(AppAccess app) {
		super(app);
	}
	
	
	public void start()
	{
		timer = new TimerDelta();
		
		while (running) {
			disp().beginFrame();
			
			bus().send(new UpdateEvent(timer.getDelta()));
			
			Runnable r;
			while ((r = taskQueue.poll()) != null) {
				r.run();
			}
			
			tick();
			
			disp().endFrame();
		}
	}
	
	
	/**
	 * Called each frame, in rendering context.
	 */
	protected abstract void tick();
	
	
	@Override
	protected final void deinit()
	{
		running = false;
	}
	
	
	@Override
	public final synchronized void queueTask(Runnable request)
	{
		taskQueue.add(request);
	}
	
}
