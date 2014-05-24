package mightypork.gamecore.input.events;


/**
 * {@link MouseMotionEvent} listener
 * 
 * @author Ondřej Hruška
 */
public interface MouseMotionHandler {
	
	/**
	 * Handle an event
	 * 
	 * @param event event
	 */
	void receive(MouseMotionEvent event);
}
