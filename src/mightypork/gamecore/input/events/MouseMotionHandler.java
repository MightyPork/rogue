package mightypork.gamecore.input.events;


/**
 * {@link MouseMotionEvent} listener
 * 
 * @author MightyPork
 */
public interface MouseMotionHandler {
	
	/**
	 * Handle an event
	 * 
	 * @param event event
	 */
	void receive(MouseMotionEvent event);
}
