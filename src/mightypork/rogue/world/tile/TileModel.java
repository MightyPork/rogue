package mightypork.rogue.world.tile;


import mightypork.rogue.world.EntityModel;
import mightypork.util.annotations.DefaultImpl;


public abstract class TileModel extends EntityModel<TileData, TileRenderContext> {
	
	public TileModel(int id) {
		super(id);
		Tiles.register(id, this);
	}
	
	@Override
	public TileData createData()
	{
		return null;
	}
	
	
	/**
	 * Test if this tile type is potentially walkable. Used during world
	 * generation.
	 * 
	 * @return can be walked through (if discovered / open)
	 */
	public abstract boolean isWalkable();
	
	
	/**
	 * Try to reveal a secret.
	 * 
	 * @param data tile data
	 */
	@DefaultImpl
	public void search(TileData data)
	{
		// do nothing.
	}
	
	
	/**
	 * Check if a mob can walk through.
	 * 
	 * @param data tile data
	 * @return is walkable
	 */
	public abstract boolean isWalkable(TileData data);
	
}
