package mightypork.gamecore.control.bus.events;


import mightypork.gamecore.control.bus.events.types.SingleReceiverEvent;


/**
 * Request to change screen
 * 
 * @author MightyPork
 */
@SingleReceiverEvent
public class ScreenRequestEvent implements Event<ScreenRequestEvent.Listener> {
	
	private final String scrName;
	
	
	/**
	 * @param screenKey screen name
	 */
	public ScreenRequestEvent(String screenKey) {
		scrName = screenKey;
	}
	
	
	@Override
	public void handleBy(Listener handler)
	{
		handler.showScreen(scrName);
	}
	
	/**
	 * {@link ScreenRequestEvent} listener
	 * 
	 * @author MightyPork
	 */
	public interface Listener {
		
		/**
		 * @param key screen to show
		 */
		void showScreen(String key);
	}
	
}
