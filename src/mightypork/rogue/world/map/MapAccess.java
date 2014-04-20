package mightypork.rogue.world.map;


import mightypork.rogue.world.tile.Tile;


/**
 * Access interface for a level map.
 * 
 * @author MightyPork
 */
public interface MapAccess {
	
	/**
	 * Ge tile at X,Y
	 * 
	 * @param x
	 * @param y
	 * @return tile
	 */
	Tile getTile(int x, int y);
	
	
	/**
	 * @return map width in tiles
	 */
	int getWidth();
	
	
	/**
	 * @return map height in tiles
	 */
	int getHeight();
	
	
	/**
	 * @return map seed
	 */
	long getSeed();
}
