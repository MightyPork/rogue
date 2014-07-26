package mightypork.gamecore.core;


import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import mightypork.gamecore.graphics.Renderable;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.utils.annotations.Stub;
import mightypork.utils.eventbus.clients.BusNode;
import mightypork.utils.eventbus.events.UpdateEvent;
import mightypork.utils.interfaces.Destroyable;
import mightypork.utils.logging.Log;
import mightypork.utils.math.timing.Profiler;
import mightypork.utils.math.timing.TimerDelta;


/**
 * Delta-timed game loop with task queue etc.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class MainLoop extends BusNode implements Destroyable {
	
	private static final double MAX_TIME_TASKS = 1 / 30D; // (avoid queue from hogging timing)
	private static final double MAX_DELTA = 1 / 20D; // (skip huge gaps caused by loading resources etc)
	
	private final Deque<Runnable> tasks = new ConcurrentLinkedDeque<>();
	private TimerDelta timer;
	private Renderable rootRenderable;
	private volatile boolean running = true;
	
	
	/**
	 * Set primary renderable
	 * 
	 * @param rootRenderable main {@link Renderable}, typically a
	 *            {@link ScreenRegistry}
	 */
	public void setRootRenderable(Renderable rootRenderable)
	{
		this.rootRenderable = rootRenderable;
	}
	
	
	/**
	 * Start the loop
	 */
	public void start()
	{
		timer = new TimerDelta();
		
		while (running) {
			App.gfx().beginFrame();
			
			double delta = timer.getDelta();
			if (delta > MAX_DELTA) {
				Log.f3("(timing) Cropping delta: was " + delta + " , limit " + MAX_DELTA);
				delta = MAX_DELTA;
			}
			
			App.bus().sendDirect(new UpdateEvent(delta));
			
			Runnable r;
			final long t = Profiler.begin();
			while ((r = tasks.poll()) != null) {
				Log.f3(" * Main loop task.");
				r.run();
				if (Profiler.end(t) > MAX_TIME_TASKS) {
					Log.f3("! Postponing main loop tasks to next cycle.");
					break;
				}
			}
			
			beforeRender();
			
			if (rootRenderable != null) {
				rootRenderable.render();
			}
			
			afterRender();
			
			App.gfx().endFrame();
		}
	}
	
	
	/**
	 * Called before render
	 */
	@Stub
	protected void beforeRender()
	{
		//
	}
	
	
	/**
	 * Called after render
	 */
	@Stub
	protected void afterRender()
	{
		//
	}
	
	
	@Override
	public void destroy()
	{
		running = false;
	}
	
	
	/**
	 * Add a task to queue to be executed in the main loop (OpenGL thread)
	 * 
	 * @param request task
	 * @param priority if true, skip other tasks
	 */
	public synchronized void queueTask(Runnable request, boolean priority)
	{
		if (priority) {
			tasks.addFirst(request);
		} else {
			tasks.addLast(request);
		}
	}
	
}
