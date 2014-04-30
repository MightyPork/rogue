package mightypork.gamecore.input.events;


/**
 * {@link KeyEvent} listener
 * 
 * @author MightyPork
 */
public interface KeyListener {
	
	/**
	 * Handle an event
	 * 
	 * @param event event
	 */
	void receive(KeyEvent event);
}
