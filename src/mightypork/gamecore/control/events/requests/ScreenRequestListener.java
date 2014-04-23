package mightypork.gamecore.control.events.requests;

/**
 * {@link ScreenRequestEvent} listener
 * 
 * @author MightyPork
 */
public interface ScreenRequestListener {
	
	/**
	 * @param key screen to show
	 */
	void showScreen(String key);
}