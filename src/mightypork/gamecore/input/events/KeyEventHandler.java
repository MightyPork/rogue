package mightypork.gamecore.input.events;


/**
 * {@link KeyEvent} listener
 * 
 * @author Ondřej Hruška
 */
public interface KeyEventHandler {
	
	/**
	 * Handle an event
	 * 
	 * @param event event
	 */
	void receive(KeyEvent event);
}
