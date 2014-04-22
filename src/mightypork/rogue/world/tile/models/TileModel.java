package mightypork.rogue.world.tile.models;


import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.Tiles;
import mightypork.rogue.world.tile.renderers.BasicTileRenderer;
import mightypork.rogue.world.tile.renderers.TileRenderer;


/**
 * Tile model (logic of a tile)
 * 
 * @author MightyPork
 */
public abstract class TileModel {
	
	/** Model ID */
	public final int id;
	public TileRenderer renderer = TileRenderer.NONE;
	
	
	public TileModel(int id)
	{
		Tiles.register(id, this);
		this.id = id;
	}
	
	
	public TileModel setRenderer(TileRenderer renderer)
	{
		this.renderer = renderer;
		return this;
	}
	
	
	public TileModel setTexture(String sheetKey)
	{
		this.renderer = new BasicTileRenderer(sheetKey);
		return this;
	}
	
	
	/**
	 * @return new tile of this type; if 100% invariant, can return cached one.
	 */
	public Tile createTile()
	{
		return new Tile(this);
	}
	
	
	/**
	 * @param tile
	 * @return true if walkable right now
	 */
	public abstract boolean isWalkable(Tile tile);
	
	
	/**
	 * @return true if walkable at some conditions
	 */
	public abstract boolean isWalkable();
	
	
	public abstract boolean doesCastShadow();
	
	
	public boolean isNullTile()
	{
		return false;
	}
	
	
	/**
	 * Update tile in world
	 */
	public abstract void update(Tile tile, Level level, double delta);
	
	
	/**
	 * @return true if this item type has metadata worth saving
	 */
	public abstract boolean hasMetadata();
	
	
	/**
	 * @return true if this item can have dropped items
	 */
	public abstract boolean hasDroppedItems();
	
}
