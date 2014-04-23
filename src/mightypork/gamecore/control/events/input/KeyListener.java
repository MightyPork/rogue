package mightypork.gamecore.control.events.input;


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
