package mightypork.gamecore.control.events.requests;


import mightypork.util.control.eventbus.BusEvent;
import mightypork.util.control.eventbus.event_flags.SingleReceiverEvent;


/**
 * Request to change screen
 * 
 * @author MightyPork
 */
@SingleReceiverEvent
public class ScreenRequestEvent extends BusEvent<ScreenRequestListener> {
	
	private final String scrName;
	
	
	/**
	 * @param screenKey screen name
	 */
	public ScreenRequestEvent(String screenKey)
	{
		scrName = screenKey;
	}
	
	
	@Override
	public void handleBy(ScreenRequestListener handler)
	{
		handler.showScreen(scrName);
	}
	
}
