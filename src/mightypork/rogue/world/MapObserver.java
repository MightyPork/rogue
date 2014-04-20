package mightypork.rogue.world;


/**
 * Player observing a map represented by an observer.
 * 
 * @author MightyPork
 */
public interface MapObserver {
	
	/**
	 * @return observer's position
	 */
	public WorldPos getPosition();
	
	
	/**
	 * @return observed range (in tiles)
	 */
	public int getViewRange();
	
}
