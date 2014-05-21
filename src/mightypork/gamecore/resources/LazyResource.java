package mightypork.gamecore.resources;


/**
 * Deferred resource
 * 
 * @author MightyPork
 */
public interface LazyResource {
	
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
