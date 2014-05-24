package mightypork.gamecore.render.events;


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
