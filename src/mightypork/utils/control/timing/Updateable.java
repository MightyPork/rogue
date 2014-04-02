package mightypork.utils.control.timing;


/**
 * Uses delta timing
 * 
 * @author MightyPork
 */
public interface Updateable {
	
	/**
	 * Update item state based on elapsed time
	 * 
	 * @param delta time elapsed since last update, in seconds
	 */
	public void update(double delta);
}
