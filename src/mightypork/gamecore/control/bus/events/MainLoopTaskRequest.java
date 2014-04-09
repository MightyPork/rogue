package mightypork.gamecore.control.bus.events;


import mightypork.gamecore.control.bus.events.types.SingleReceiverEvent;


/**
 * Request to execute given {@link Runnable} in main loop.
 * 
 * @author MightyPork
 */
@SingleReceiverEvent
public class MainLoopTaskRequest implements Event<MainLoopTaskRequest.Listener> {
	
	private final Runnable task;
	
	
	/**
	 * @param task task to run on main thread in rendering context
	 */
	public MainLoopTaskRequest(Runnable task) {
		this.task = task;
	}
	
	
	@Override
	public void handleBy(Listener handler)
	{
		handler.queueTask(task);
	}
	
	/**
	 * {@link MainLoopTaskRequest} listener
	 * 
	 * @author MightyPork
	 */
	public interface Listener {
		
		/**
		 * Perform the requested action
		 * 
		 * @param request
		 */
		void queueTask(Runnable request);
	}
}
