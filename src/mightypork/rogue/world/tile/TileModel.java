package mightypork.rogue.world.tile;


import mightypork.rogue.world.map.TileRenderContext;
import mightypork.util.annotations.DefaultImpl;
import mightypork.util.ion.IonBundle;


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
	 * Update tile state etc
	 * 
	 * @param tile tile
	 * @param delta delta time
	 */
	public abstract void updateLogic(Tile tile, double delta);
	
	
	/**
	 * Update tile effects
	 * 
	 * @param tile tile
	 * @param delta delta time
	 */
	public abstract void updateVisual(Tile tile, double delta);
	
	
	/**
	 * Store tile metadata (door lock state etc)
	 * 
	 * @param tile stored tile
	 * @param ib written data bundle
	 */
	public abstract void saveMetadata(Tile tile, IonBundle ib);
	
	
	/**
	 * Load from an IonBundle. The bundle is guaranteed to not be null, but
	 * could be empty.
	 * 
	 * @param tile loaded tile
	 * @param ib item data bundle
	 */
	public abstract void loadMetadata(Tile tile, IonBundle ib);
	
	
	/**
	 * True if this tile's data should be saved/loaded.<br>
	 * Must be a constant value.
	 * 
	 * @return has data
	 */
	public abstract boolean hasMetadata();
	
	
	/**
	 * Check if the tile can hold dropped items. Walls and such can return false
	 * to save disk space (no need to write empty list).
	 * 
	 * @return true
	 */
	public abstract boolean hasDroppedItems();
}
