package mightypork.gamecore.core.events;


import mightypork.gamecore.core.modules.MainLoop;
import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.event_flags.SingleReceiverEvent;


/**
 * Request to execute given {@link Runnable} in main loop.
 * 
 * @author MightyPork
 */
@SingleReceiverEvent
public class MainLoopRequest extends BusEvent<MainLoop> {
	
	private final Runnable task;
	
	
	/**
	 * @param task task to run on main thread in rendering context
	 */
	public MainLoopRequest(Runnable task)
	{
		this.task = task;
	}
	
	
	@Override
	public void handleBy(MainLoop handler)
	{
		handler.queueTask(task);
	}
}
