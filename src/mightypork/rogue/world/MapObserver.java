package mightypork.rogue.world;


/**
 * Player observing a map represented by an observer.
 * 
 * @author MightyPork
 */
public interface MapObserver extends WorldEntity {
	
	/**
	 * @return observed range (in tiles)
	 */
	public int getViewRange();
	
}
