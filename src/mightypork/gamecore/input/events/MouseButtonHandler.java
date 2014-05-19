package mightypork.gamecore.input.events;


/**
 * {@link MouseButtonEvent} listener
 * 
 * @author MightyPork
 */
public interface MouseButtonHandler {
	
	/**
	 * Handle an event
	 * 
	 * @param event event
	 */
	void receive(MouseButtonEvent event);
}
