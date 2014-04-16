package mightypork.util.control.eventbus.clients;


/**
 * Client that can toggle receiving messages.
 * 
 * @author MightyPork
 */
public interface ToggleableClient {
	
	/**
	 * @return true if the client wants to receive messages
	 */
	public boolean isListening();
	
}
