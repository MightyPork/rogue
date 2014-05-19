package mightypork.gamecore.core.events;


/**
 * Quit request listener; implementing client can abort shutdown.
 * 
 * @author MightyPork
 */
public interface UserQuitRequestListener {

	/**
	 * Intercept quit request.<br>
	 * Consume the event to abort shutdown (ie. ask user to save)
	 * 
	 * @param event quit request event.
	 */
	void onQuitRequest(UserQuitRequest event);
}
