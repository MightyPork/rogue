package mightypork.gamecore.gui.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.event_flags.SingleReceiverEvent;


/**
 * Request to change screen
 * 
 * @author Ondřej Hruška
 */
@SingleReceiverEvent
public class ScreenRequest extends BusEvent<ScreenRequestListener> {
	
	private final String scrName;
	
	
	/**
	 * @param screenKey screen name
	 */
	public ScreenRequest(String screenKey)
	{
		scrName = screenKey;
	}
	
	
	@Override
	public void handleBy(ScreenRequestListener handler)
	{
		handler.showScreen(scrName);
	}
	
}
