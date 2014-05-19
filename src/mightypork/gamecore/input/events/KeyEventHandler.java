package mightypork.gamecore.input.events;


/**
 * {@link KeyEvent} listener
 * 
 * @author MightyPork
 */
public interface KeyEventHandler {
	
	/**
	 * Handle an event
	 * 
	 * @param event event
	 */
	void receive(KeyEvent event);
}
