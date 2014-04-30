package mightypork.gamecore.util.math.constraints;


/**
 * Can be asked to update it's state
 * 
 * @author MightyPork
 */
public interface Pollable {
	
	/**
	 * Update internal state
	 */
	void poll();
}
