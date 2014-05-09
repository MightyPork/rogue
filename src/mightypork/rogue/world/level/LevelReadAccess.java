package mightypork.rogue.world.level;


import java.util.Collection;

import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.noise.NoiseGen;
import mightypork.rogue.world.World;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityType;
import mightypork.rogue.world.tile.Tile;


public interface LevelReadAccess {
	
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
	
	
	/**
	 * Check if entity is in the level
	 * 
	 * @param entity entity
	 * @return is present
	 */
	boolean isEntityPresent(Entity entity);
	
	
	/**
	 * Check if entity is in the level
	 * 
	 * @param eid entity ID
	 * @return true if present
	 */
	boolean isEntityPresent(int eid);
	
	
	/**
	 * Get entity of type closest to coord
	 * 
	 * @param self the querying entity - to provide position, and to be excluded
	 *            from the search.
	 * @param type wanted entity type
	 * @param radius search radius; -1 for unlimited.
	 * @return
	 */
	Entity getClosestEntity(Entity self, EntityType type, double radius);
	
	
	/**
	 * Get the level's world
	 * 
	 * @return world
	 */
	World getWorld();
	
	
	/**
	 * Get location where the player enters the level
	 * 
	 * @return pos
	 */
	Coord getEnterPoint();
	
	
	/**
	 * Check entity on tile
	 * 
	 * @param pos tile coord
	 * @return true if some entity is standing there
	 */
	boolean isOccupied(Coord pos);
	
	
	/**
	 * Check tile walkability
	 * 
	 * @param pos tile coord
	 * @return true if the tile is walkable by entity
	 */
	boolean isWalkable(Coord pos);
	
	
	/**
	 * Get entity by ID
	 * 
	 * @param eid entity ID
	 * @return the entity, or null
	 */
	Entity getEntity(int eid);
	
	
	/**
	 * @return all entities
	 */
	Collection<Entity> getEntities();
	
}
