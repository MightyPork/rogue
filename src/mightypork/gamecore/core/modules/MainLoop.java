package mightypork.gamecore.core.modules;


import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import mightypork.gamecore.eventbus.events.UpdateEvent;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.gamecore.logging.Log;
import mightypork.gamecore.render.Renderable;
import mightypork.gamecore.render.TaskTakeScreenshot;
import mightypork.gamecore.render.events.ScreenshotRequestListener;
import mightypork.gamecore.resources.Profiler;
import mightypork.gamecore.util.Utils;
import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.math.timing.TimerDelta;


/**
 * Delta-timed game loop with task queue etc.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class MainLoop extends AppModule implements ScreenshotRequestListener {
	
	private static final double MAX_TIME_TASKS = 1 / 30D; // (avoid queue from hogging timing)
	private static final double MAX_DELTA = 1 / 20D; // (skip huge gaps caused by loading resources etc)
	
	private final Deque<Runnable> tasks = new ConcurrentLinkedDeque<>();
	private TimerDelta timer;
	private Renderable rootRenderable;
	private volatile boolean running = true;
	
	
	/**
	 * @param app {@link AppAccess} instance
	 */
	public MainLoop(AppAccess app)
	{
		super(app);
	}
	
	
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
			getDisplay().beginFrame();
			
			double delta = timer.getDelta();
			if (delta > MAX_DELTA) {
				Log.f3("(timing) Cropping delta: was " + delta + " , limit " + MAX_DELTA);
				delta = MAX_DELTA;
			}
			
			getEventBus().sendDirect(new UpdateEvent(delta));
			
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
			
			getDisplay().endFrame();
		}
	}
	
	
	/**
	 * Called before render
	 */
	@DefaultImpl
	protected void beforeRender()
	{
		//
	}
	
	
	/**
	 * Called after render
	 */
	@DefaultImpl
	protected void afterRender()
	{
		//
	}
	
	
	@Override
	protected void deinit()
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
	
	
	@Override
	public void onScreenshotRequest()
	{
		// ensure it's started in main thread
		queueTask(new Runnable() {
			
			@Override
			public void run()
			{
				Utils.runAsThread(new TaskTakeScreenshot());
			}
		}, false);
	}
	
}
