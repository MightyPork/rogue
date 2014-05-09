package mightypork.rogue.world.level;


import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.rogue.world.World;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;


/**
 * Level full access
 * 
 * @author MightyPork
 */
public interface LevelAccess extends LevelReadAccess, Updateable {
	
	
	/**
	 * Mark tile and surrounding area as explored
	 * 
	 * @param center center the explored tile
	 */
	public abstract void explore(Coord center);
	
	
	/**
	 * Assign a world
	 * 
	 * @param world new world
	 */
	public abstract void setWorld(World world);
	
	
	/**
	 * Set level entry point
	 * 
	 * @param pos pos where the player enters
	 */
	public abstract void setEnterPoint(Coord pos);
	
	
	/**
	 * Mark tile as free (entity left)
	 * 
	 * @param pos tile pos
	 */
	public abstract void freeTile(Coord pos);
	
	
	/**
	 * Mark tile as occupied (entity entered)
	 * 
	 * @param pos tile pos
	 */
	public abstract void occupyTile(Coord pos);
	
	
	/**
	 * Remove an entity from the level, if present
	 * 
	 * @param eid entity id
	 */
	public abstract void removeEntity(int eid);
	
	
	/**
	 * Remove an entity from the level, if present
	 * 
	 * @param entity entity
	 */
	public abstract void removeEntity(Entity entity);
	
	
	/**
	 * Set level seed (used for visuals; the seed used for generation)
	 * 
	 * @param seed seed
	 */
	public abstract void setSeed(long seed);
	
	
	/**
	 * Set tile at pos
	 * 
	 * @param pos tile pos
	 * @param tile the tile instance to set
	 */
	public abstract void setTile(Coord pos, Tile tile);
	
	
	/**
	 * Fill whole map with tile type
	 * 
	 * @param model tile model
	 */
	public abstract void fill(TileModel model);
	
	
	/**
	 * Try to add entity at given pos
	 * 
	 * @param entity the entity
	 * @param pos pos
	 * @return true if added (false if void, wall etc)
	 */
	public abstract boolean addEntity(Entity entity, Coord pos);
}
