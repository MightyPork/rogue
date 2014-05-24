package mightypork.gamecore.eventbus.clients;


/**
 * Client that can toggle receiving messages.
 * 
 * @author Ondřej Hruška
 */
public interface ToggleableClient {
	
	/**
	 * @return true if the client wants to receive messages
	 */
	public boolean isListening();
	
}
