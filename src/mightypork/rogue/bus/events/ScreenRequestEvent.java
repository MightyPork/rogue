package mightypork.rogue.bus.events;


import mightypork.utils.control.bus.events.Event;
import mightypork.utils.control.bus.events.types.QueuedEvent;
import mightypork.utils.control.bus.events.types.SingularEvent;


/**
 * Request to change screen
 * 
 * @author MightyPork
 */
@SingularEvent
@QueuedEvent
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
