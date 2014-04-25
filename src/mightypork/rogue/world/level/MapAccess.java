package mightypork.rogue.world.level;


import mightypork.rogue.world.Coord;
import mightypork.rogue.world.tile.Tile;
import mightypork.util.math.noise.NoiseGen;


/**
 * Access interface for a level map.
 * 
 * @author MightyPork
 */
public interface MapAccess {
	
	/**
	 * Ge tile at X,Y
	 * 
	 * @param pos
	 * @return tile
	 */
	Tile getTile(Coord pos);
	
	
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
	
	
	/**
	 * @return level-specific noise generator
	 */
	NoiseGen getNoiseGen();
}
