package mightypork.gamecore.gui.events;


/**
 * {@link ScreenRequest} listener
 * 
 * @author Ondřej Hruška
 */
public interface ScreenRequestListener {
	
	/**
	 * @param key screen to show
	 */
	void showScreen(String key);
}
