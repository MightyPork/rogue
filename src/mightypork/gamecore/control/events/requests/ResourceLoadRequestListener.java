package mightypork.gamecore.control.events.requests;

import mightypork.gamecore.loading.Deferred;

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