package mightypork.gamecore.resources.events;


import mightypork.gamecore.resources.loading.Deferred;


/**
 * {@link ResourceLoadRequest} listener
 * 
 * @author MightyPork
 */
public interface ResourceLoadRequestListener {
	
	/**
	 * Load a resource
	 * 
	 * @param resource
	 */
	void loadResource(Deferred resource);
}
