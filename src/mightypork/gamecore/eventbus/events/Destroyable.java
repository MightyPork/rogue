package mightypork.gamecore.eventbus.events;


/**
 * Object that can be destroyed (free resources etc)
 * 
 * @author Ondřej Hruška
 */
public interface Destroyable {
	
	/**
	 * Destroy this object
	 */
	public void destroy();
}
