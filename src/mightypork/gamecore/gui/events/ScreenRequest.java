package mightypork.gamecore.gui.events;


import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.events.flags.SingleReceiverEvent;


/**
 * Request to change screen
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@SingleReceiverEvent
public class ScreenRequest extends BusEvent<ScreenRequestListener> {
	
	private final String scrName;
	
	
	/**
	 * @param screenKey screen name
	 */
	public ScreenRequest(String screenKey) {
		scrName = screenKey;
	}
	
	
	@Override
	public void handleBy(ScreenRequestListener handler)
	{
		handler.showScreen(scrName);
	}
	
}
