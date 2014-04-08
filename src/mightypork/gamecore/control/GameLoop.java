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
	private final Renderable mainRenderable;
	private boolean running = true;
	
	
	/**
	 * @param app {@link AppAccess} instance
	 * @param rootRenderable main {@link Renderable}, typically a
	 *            {@link ScreenRegistry}
	 */
	public GameLoop(AppAccess app, Renderable rootRenderable) {
		super(app);
		
		if (rootRenderable == null) {
			throw new NullPointerException("Master renderable must not be null.");
		}
		
		mainRenderable = rootRenderable;
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
			
			mainRenderable.render();
			
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
