package mightypork.gamecore.resources.loading;


/**
 * Deferred resource
 * 
 * @author MightyPork
 */
public interface Deferred {
	
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
