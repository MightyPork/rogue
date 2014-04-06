package mightypork.rogue.bus.events;


import mightypork.utils.control.bus.Event;
import mightypork.utils.control.bus.SingularEvent;


/**
 * Request to change screen
 * 
 * @author MightyPork
 */
@SingularEvent
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
