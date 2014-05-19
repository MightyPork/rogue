package mightypork.gamecore.core.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.EventBus;


/**
 * User quit request. This event is triggered when user clicks the "close"
 * titlebar button, and if no client consumes it, the application will be shut
 * down.
 * 
 * @author MightyPork
 */
public class UserQuitRequest extends BusEvent<UserQuitRequestListener> {
	
	@Override
	protected void handleBy(UserQuitRequestListener handler)
	{
		handler.onQuitRequest(this);
	}
	
	
	@Override
	public void onDispatchComplete(EventBus bus)
	{
		if (!isConsumed()) {
			bus.send(new ShudownRequest());
		}
	}
	
}
