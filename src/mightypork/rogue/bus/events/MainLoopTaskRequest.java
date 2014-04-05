package mightypork.rogue.bus.events;


import mightypork.utils.control.bus.Event;
import mightypork.utils.control.bus.SingularEvent;


/**
 * Request to execute given {@link Runnable} in main loop.
 * 
 * @author MightyPork
 */
public class MainLoopTaskRequest implements Event<MainLoopTaskRequest.Listener>, SingularEvent {
	
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
		public void queueTask(Runnable request);
	}
}
