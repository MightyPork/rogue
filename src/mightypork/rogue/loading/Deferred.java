package mightypork.rogue.loading;


/**
 * Deferred resource
 * 
 * @author MightyPork
 */
public interface Deferred {
	
	/**
	 * Load the actual resource, if not loaded yet.
	 */
	public void load();
	
	
	/**
	 * Check if resource was successfully loaded.
	 * 
	 * @return true if already loaded
	 */
	public boolean isLoaded();
}
