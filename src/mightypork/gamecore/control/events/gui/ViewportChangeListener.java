package mightypork.gamecore.control.events.gui;

/**
 * {@link ViewportChangeEvent} listener
 * 
 * @author MightyPork
 */
public interface ViewportChangeListener {
	
	/**
	 * Handle event
	 * 
	 * @param event
	 */
	void onViewportChanged(ViewportChangeEvent event);
}