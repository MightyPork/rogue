package mightypork.rogue;


/**
 * Deferred resource
 * 
 * @author MightyPork
 */
public interface Deferred {
	
	/**
	 * Load the actual resource if not loaded yet
	 */
	public void load();
	
	
	/**
	 * @return true if already loaded
	 */
	public boolean isLoaded();
}
