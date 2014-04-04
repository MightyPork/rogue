package mightypork.rogue;


import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import mightypork.rogue.bus.Subsystem;
import mightypork.rogue.bus.events.ActionRequest;
import mightypork.rogue.bus.events.RequestType;
import mightypork.rogue.bus.events.ScreenRequestEvent;
import mightypork.rogue.tasks.TaskTakeScreenshot;
import mightypork.rogue.util.Utils;
import mightypork.utils.control.bus.events.UpdateEvent;
import mightypork.utils.control.timing.TimerDelta;


public class MainLoop extends Subsystem implements ActionRequest.Listener {
	
	private Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<Runnable>();
	private List<Runnable> regularTasks;
	
	
	public MainLoop(App app, ArrayList<Runnable> loopTasks) {
		super(app);
		
		this.regularTasks = loopTasks;
	}
	
	/** timer */
	private TimerDelta timer;
	private boolean running = true;
	
	
	public void start()
	{
		bus().queue(new ScreenRequestEvent("test.texture"));
		
		timer = new TimerDelta();
		
		while (running) {
			disp().beginFrame();
			
			bus().send(new UpdateEvent(timer.getDelta()));
			
			for (Runnable r : regularTasks) {
				r.run();
			}
			
			Runnable r;
			while ((r = taskQueue.poll()) != null) {
				r.run();
			}
			
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
				taskQueue.add(taskFullscreen);
				break;
			
			case SCREENSHOT:
				taskQueue.add(taskScreenshot);
				break;
			
			case SHUTDOWN:
				taskQueue.add(taskShutdown);
		}
	}
	
	private final Runnable taskScreenshot = new Runnable() {
		
		@Override
		public void run()
		{
			Res.getEffect("gui.shutter").play(1);
			Utils.runAsThread(new TaskTakeScreenshot(disp()));
		}
	};
	
	private final Runnable taskShutdown = new Runnable() {
		
		@Override
		public void run()
		{
			shutdown();
		}
	};
	
	private final Runnable taskFullscreen = new Runnable() {
		
		@Override
		public void run()
		{
			disp().switchFullscreen();
		}
	};
}
