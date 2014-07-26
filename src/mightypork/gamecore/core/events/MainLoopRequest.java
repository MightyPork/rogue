package mightypork.gamecore.core.events;


import mightypork.gamecore.core.MainLoop;
import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.events.flags.SingleReceiverEvent;


/**
 * Request to execute given {@link Runnable} in main loop.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@SingleReceiverEvent
public class MainLoopRequest extends BusEvent<MainLoop> {
	
	private final Runnable task;
	private final boolean priority;
	
	
	/**
	 * @param task task to run on main thread in rendering context
	 */
	public MainLoopRequest(Runnable task) {
		this(task, false);
	}
	
	
	/**
	 * @param task task to run on main thread in rendering context
	 * @param priority if true, skip other tasks in queue
	 */
	public MainLoopRequest(Runnable task, boolean priority) {
		this.task = task;
		this.priority = priority;
	}
	
	
	@Override
	public void handleBy(MainLoop handler)
	{
		handler.queueTask(task, priority);
	}
}
