package mightypork.utils.patterns.subscription;

/**
 * Something that can be handled by HANDLER.
 * 
 * @author MightyPork
 * @param <HANDLER> handler type
 */
public interface Handleable<HANDLER> {
	
	/**
	 * Ask handler to handle this message.
	 * 
	 * @param handler handler instance
	 */
	public void handleBy(HANDLER handler);
}
