package mightypork.rogue;


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import mightypork.rogue.bus.Subsystem;
import mightypork.rogue.bus.events.ActionRequest;
import mightypork.rogue.bus.events.ActionRequest.RequestType;
import mightypork.rogue.bus.events.MainLoopTaskRequest;
import mightypork.rogue.bus.events.UpdateEvent;
import mightypork.rogue.render.Renderable;
import mightypork.rogue.tasks.TaskTakeScreenshot;
import mightypork.rogue.util.Utils;
import mightypork.utils.control.timing.TimerDelta;


public class MainLoop extends Subsystem implements ActionRequest.Listener, MainLoopTaskRequest.Listener {
	
	private final Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<Runnable>();
	private final Renderable renderable;
	
	
	public MainLoop(App app, Renderable masterRenderable) {
		super(app);
		
		if (masterRenderable == null) {
			throw new NullPointerException("Master renderable must not be null.");
		}
		
		this.renderable = masterRenderable;
	}
	
	/** timer */
	private TimerDelta timer;
	private boolean running = true;
	
	
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
			
			renderable.render();
			
			disp().endFrame();
		}
	}
	
	
	@Override
	protected void deinit()
	{
		running = false;
	}
	
	
	@Override
	public void requestAction(RequestType request)
	{
		switch (request) {
			case FULLSCREEN:
				queueTask(taskFullscreen);
				break;
			
			case SCREENSHOT:
				queueTask(taskScreenshot);
				break;
			
			case SHUTDOWN:
				queueTask(taskShutdown);
		}
	}
	
	/** Take a screenshot */
	private final Runnable taskScreenshot = new Runnable() {
		
		@Override
		public void run()
		{
			Res.getEffect("gui.shutter").play(1);
			Utils.runAsThread(new TaskTakeScreenshot(disp()));
		}
	};
	
	/** Shutdown the application */
	private final Runnable taskShutdown = new Runnable() {
		
		@Override
		public void run()
		{
			shutdown();
		}
	};
	
	/** Toggle fullscreen */
	private final Runnable taskFullscreen = new Runnable() {
		
		@Override
		public void run()
		{
			disp().switchFullscreen();
		}
	};
	
	
	@Override
	public synchronized void queueTask(Runnable request)
	{
		taskQueue.add(request);
	}
}
