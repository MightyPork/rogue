package mightypork.rogue.bus.events;


import mightypork.utils.control.bus.events.Event;
import mightypork.utils.control.bus.events.types.SingleReceiverEvent;


/**
 * Request to change screen
 * 
 * @author MightyPork
 */
@SingleReceiverEvent
public class ScreenRequestEvent implements Event<ScreenRequestEvent.Listener> {
	
	private final String scrName;
	
	
	public ScreenRequestEvent(String screenKey) {
		scrName = screenKey;
	}
	
	
	@Override
	public void handleBy(Listener handler)
	{
		handler.showScreen(scrName);
	}
	
	public interface Listener {
		
		void showScreen(String key);
	}
	
}
