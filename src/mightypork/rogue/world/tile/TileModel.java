package mightypork.rogue.world.tile;


import mightypork.rogue.world.map.TileRenderContext;
import mightypork.util.annotations.DefaultImpl;


/**
 * Singleton-like tile implementation
 * 
 * @author MightyPork
 */
public abstract class TileModel {
	
	/** Model ID */
	public final int id;
	
	
	public TileModel(int id)
	{
		Tiles.register(id, this);
		this.id = id;
	}
	
	
	/**
	 * Create a tile. In case of null tiles / tiles with absolutely no
	 * variability, the same instance can be returned over and over (created ie.
	 * using lazy load)
	 * 
	 * @return new tile with this model
	 */
	@DefaultImpl
	public Tile createTile()
	{
		return new Tile(this);
	}
	
	
	/**
	 * Render the tile.
	 * 
	 * @param context
	 */
	public abstract void render(TileRenderContext context);
	
	
	/**
	 * @param tile
	 * @return is walkable at the current conditions
	 */
	public abstract boolean isWalkable(Tile tile);
	
	
	/**
	 * Check if the tile is walkable at some conditions. Used for world
	 * generation to distinguish between doors etc and regular walls.<br>
	 * Null tile should return true, if it can be replaced by a regular floor.
	 * 
	 * @return if it's potentially walkable
	 */
	public abstract boolean isPotentiallyWalkable();
	
	
	public boolean isNullTile()
	{
		return false;
	}
	
	
	/**
	 * Update a tile
	 * 
	 * @param tile tile
	 * @param delta delta time
	 */
	public abstract void update(Tile tile, double delta);
	
}
