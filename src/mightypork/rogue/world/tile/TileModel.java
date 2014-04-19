package mightypork.rogue.world.tile;


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
	 * @return new tile with this model
	 */
	@DefaultImpl
	public Tile create()
	{
		return new Tile(this);
	}
	
	
	/**
	 * Render the tile.
	 * 
	 * @param tile
	 * @param context
	 */
	public abstract void render(Tile tile, TileRenderContext context);
	
	
	/**
	 * @param tile
	 * @return is walkable at the current conditions
	 */
	public abstract boolean isWalkable(Tile tile);
	
	
	/**
	 * @return true if the tile can be walkable at some conditions
	 */
	public abstract boolean isPotentiallyWalkable();
	
	
	public boolean isNullTile()
	{
		return false;
	}
	
}
