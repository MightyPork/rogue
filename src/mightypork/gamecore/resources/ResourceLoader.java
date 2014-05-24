package mightypork.gamecore.resources;


import mightypork.gamecore.eventbus.BusAccess;


/**
 * {@link ResourceLoadRequest} listener
 * 
 * @author Ondřej Hruška
 */
public interface ResourceLoader {
	
	/**
	 * Load a resource
	 * 
	 * @param resource
	 */
	void loadResource(LazyResource resource);
	
	
	/**
	 * Initialize the loader (Join the bus, start a stread etc)
	 * 
	 * @param app app the loader works for. The event bus must already be
	 *            initialized.
	 */
	void init(BusAccess app);
}
