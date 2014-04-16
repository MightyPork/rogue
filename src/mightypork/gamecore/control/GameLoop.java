package mightypork.gamecore.control;


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import mightypork.gamecore.control.events.MainLoopTaskRequest;
import mightypork.gamecore.control.events.UpdateEvent;
import mightypork.gamecore.gui.components.Renderable;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.util.annotations.DefaultImpl;
import mightypork.util.control.timing.TimerDelta;


/**
 * Delta-timed game loop with task queue etc.
 * 
 * @author MightyPork
 */
public abstract class GameLoop extends AppModule implements MainLoopTaskRequest.Listener {
	
	private final Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();
	private TimerDelta timer;
	private Renderable rootRenderable;
	private volatile boolean running = true;
	
	
	/**
	 * @param app {@link AppAccess} instance
	 */
	public GameLoop(AppAccess app) {
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
			
			getEventBus().send(new UpdateEvent(timer.getDelta()));
			
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
	
	
	@Override
	public synchronized void queueTask(Runnable request)
	{
		taskQueue.add(request);
	}
	
}
