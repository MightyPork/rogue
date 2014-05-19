package mightypork.gamecore.resources;


import mightypork.gamecore.eventbus.BusAccess;


/**
 * {@link ResourceLoadRequest} listener
 * 
 * @author MightyPork
 */
public interface ResourceLoader {
	
	/**
	 * Load a resource
	 * 
	 * @param resource
	 */
	void loadResource(DeferredResource resource);
	
	
	/**
	 * Initialize the loader (Join the bus, start a stread etc)
	 * 
	 * @param app app the loader works for. The event bus must already be
	 *            initialized.
	 */
	void init(BusAccess app);
}
