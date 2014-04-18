package mightypork.rogue.world;


import java.io.InputStream;
import java.io.OutputStream;

import mightypork.util.annotations.DefaultImpl;


public abstract class TileModel {
	
	public final int id;
	
	
	public TileModel(int id) {
		this.id = id;
		Tiles.register(id, this);
	}
	
	
	/**
	 * Get the id.
	 * 
	 * @return id
	 */
	public final int getId()
	{
		return id;
	}
	
	
	/**
	 * @return can be walked through (if discovered / open)
	 */
	public abstract boolean isPotentiallyWalkable();
	
	
	/**
	 * Populate a tile holder with data for this tile model
	 * 
	 * @param tile tile in world
	 */
	@DefaultImpl
	public void create(TileData tile)
	{
	}
	
	
	/**
	 * On search performed (reveal hidden door etc)
	 * 
	 * @param tile tile in world
	 */
	@DefaultImpl
	public void search(TileData tile)
	{
	}
	
	
	/**
	 * Check if an entity can walk this tile. If the tile is not potentially
	 * walkable, then this method must always return false.
	 * 
	 * @param tile tile in world
	 */
	public abstract void isWalkable(TileData tile);
	
	
	/**
	 * Load a tile from binary stream.
	 * 
	 * @param tile tile to load
	 * @param in input stream
	 */
	@DefaultImpl
	public void load(TileData tile, InputStream in)
	{
	}
	
	
	/**
	 * Save a tile to binary stream.
	 * 
	 * @param tile tile to save
	 * @param out output stream
	 */
	@DefaultImpl
	public void save(TileData tile, OutputStream out)
	{
	}
	
	
	public abstract void render(TileData tile, TileRenderContext context);
	
	
	/**
	 * Update the tile (animation, item spawning etc)
	 * 
	 * @param tile tile to update
	 * @param delta delta time
	 */
	@DefaultImpl
	public void update(TileData tile, double delta)
	{
	}
}
