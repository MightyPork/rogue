package mightypork.rogue.bus.events;


import mightypork.utils.control.bus.events.Event;
import mightypork.utils.control.bus.events.types.QueuedEvent;
import mightypork.utils.control.bus.events.types.SingularEvent;


/**
 * Request to execute given {@link Runnable} in main loop.
 * 
 * @author MightyPork
 */
@SingularEvent
@QueuedEvent
public class MainLoopTaskRequest implements Event<MainLoopTaskRequest.Listener> {
	
	private final Runnable task;
	
	
	public MainLoopTaskRequest(Runnable task) {
		this.task = task;
	}
	
	
	@Override
	public void handleBy(Listener handler)
	{
		handler.queueTask(task);
	}
	
	public interface Listener {
		
		/**
		 * Perform the requested action
		 * 
		 * @param request
		 */
		void queueTask(Runnable request);
	}
}
