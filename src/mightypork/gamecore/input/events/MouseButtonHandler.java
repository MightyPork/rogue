package mightypork.gamecore.input.events;


/**
 * {@link MouseButtonEvent} listener
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public interface MouseButtonHandler {
	
	/**
	 * Handle an event
	 * 
	 * @param event event
	 */
	void receive(MouseButtonEvent event);
}
