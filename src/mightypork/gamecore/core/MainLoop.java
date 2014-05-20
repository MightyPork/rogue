package mightypork.gamecore.core;


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import mightypork.gamecore.eventbus.events.UpdateEvent;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.gamecore.render.Renderable;
import mightypork.gamecore.render.TaskTakeScreenshot;
import mightypork.gamecore.render.events.ScreenshotRequestListener;
import mightypork.gamecore.util.Utils;
import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.math.timing.TimerDelta;


/**
 * Delta-timed game loop with task queue etc.
 * 
 * @author MightyPork
 */
public class MainLoop extends AppModule implements ScreenshotRequestListener {
	
	private final Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();
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
			
			getEventBus().sendDirect(new UpdateEvent(timer.getDelta()));
			
			Runnable r;
			while ((r = taskQueue.poll()) != null) {
				r.run();
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
	 */
	public synchronized void queueTask(Runnable request)
	{
		taskQueue.add(request);
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
		});
	}
	
}
