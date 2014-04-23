package mightypork.gamecore.control.events.requests;


import mightypork.util.control.eventbus.BusEvent;
import mightypork.util.control.eventbus.events.flags.SingleReceiverEvent;


/**
 * Request to execute given {@link Runnable} in main loop.
 * 
 * @author MightyPork
 */
@SingleReceiverEvent
public class MainLoopRequest extends BusEvent<MainLoopRequestListener> {
	
	private final Runnable task;
	
	
	/**
	 * @param task task to run on main thread in rendering context
	 */
	public MainLoopRequest(Runnable task) {
		this.task = task;
	}
	
	
	@Override
	public void handleBy(MainLoopRequestListener handler)
	{
		handler.queueTask(task);
	}
}
