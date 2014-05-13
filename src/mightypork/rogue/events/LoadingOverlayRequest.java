package mightypork.rogue.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.event_flags.SingleReceiverEvent;
import mightypork.rogue.screens.LoadingOverlay;


/**
 * Request to execute a given task in a loading overlay
 * 
 * @author MightyPork
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
