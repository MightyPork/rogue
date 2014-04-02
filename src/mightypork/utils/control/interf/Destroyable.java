package mightypork.utils.control.interf;


/**
 * Object that can be destroyed (free resources etc)
 * 
 * @author MightyPork
 */
public interface Destroyable {
	
	/**
	 * Destroy this object
	 */
	public void destroy();
}
