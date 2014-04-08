package mightypork.gamecore.control;


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import mightypork.gamecore.control.bus.events.MainLoopTaskRequest;
import mightypork.gamecore.control.bus.events.UpdateEvent;
import mightypork.gamecore.control.interf.NoImpl;
import mightypork.gamecore.control.timing.TimerDelta;
import mightypork.gamecore.gui.renderers.Renderable;
import mightypork.gamecore.gui.screens.ScreenRegistry;


/**
 * Delta-timed game loop with task queue etc.
 * 
 * @author MightyPork
 */
public abstract class GameLoop extends AppModule implements MainLoopTaskRequest.Listener {
	
	private final Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();
	private TimerDelta timer;
	private Renderable rootRenderable;
	private boolean running = true;
	
	
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
			
			if (rootRenderable != null) rootRenderable.render();
			
			afterRender();
			
			getDisplay().endFrame();
		}
	}
	
	
	@NoImpl
	protected void beforeRender()
	{
		//
	}
	
	
	@NoImpl
	protected void afterRender()
	{
		//
	}
	
	
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
