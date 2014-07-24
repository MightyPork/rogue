package mightypork.gamecore.resources;


/**
 * Deferred resource
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public interface DeferredResource {
	
	/**
	 * Load the actual resource, if not loaded yet.
	 */
	void load();
	
	
	/**
	 * Check if resource was successfully loaded.
	 * 
	 * @return true if already loaded
	 */
	boolean isLoaded();
}
