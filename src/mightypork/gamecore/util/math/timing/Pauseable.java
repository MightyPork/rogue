package mightypork.gamecore.util.math.timing;


/**
 * Can be paused & resumed
 * 
 * @author Ondřej Hruška (MightyPork)
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
