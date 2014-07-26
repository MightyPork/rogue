package mightypork.gamecore.gui.events;


/**
 * {@link ViewportChangeEvent} listener
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public interface ViewportChangeListener {
	
	/**
	 * Handle event
	 * 
	 * @param event
	 */
	void onViewportChanged(ViewportChangeEvent event);
}
