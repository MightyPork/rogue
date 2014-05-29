package mightypork.rogue.events;


import mightypork.rogue.screens.LoadingOverlay;
import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.events.flags.SingleReceiverEvent;


/**
 * Request to execute a given task in a loading overlay
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@SingleReceiverEvent
public class LoadingOverlayRequest extends BusEvent<LoadingOverlay> {
	
	private final String msg;
	private final Runnable task;
	
	
	/**
	 * @param msg task description
	 * @param task task runnable
	 */
	public LoadingOverlayRequest(String msg, Runnable task)
	{
		this.task = task;
		this.msg = msg;
	}
	
	
	@Override
	protected void handleBy(LoadingOverlay handler)
	{
		handler.show(msg, task);
	}
	
}
