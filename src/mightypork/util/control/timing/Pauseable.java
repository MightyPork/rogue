package mightypork.util.control.timing;


/**
 * Can be paused & resumed
 * 
 * @author MightyPork
 */
public interface Pauseable {
	
	/**
	 * Pause operation
	 */
	public void pause();
	
	
	/**
	 * Resume operation
	 */
	public void resume();
	
	
	/**
	 * @return paused state
	 */
	public boolean isPaused();
	
}
