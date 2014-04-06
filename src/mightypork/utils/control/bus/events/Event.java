package mightypork.utils.control.bus.events;


/**
 * Something that can be handled by HANDLER.
 * 
 * @author MightyPork
 * @param <HANDLER> handler type
 */
public interface Event<HANDLER> {
	
	/**
	 * Ask handler to handle this message.
	 * 
	 * @param handler handler instance
	 */
	public void handleBy(HANDLER handler);
}
