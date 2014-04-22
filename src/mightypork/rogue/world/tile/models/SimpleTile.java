package mightypork.rogue.world.tile.models;


import mightypork.rogue.world.WorldAccess;
import mightypork.rogue.world.map.Level;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;
import mightypork.util.annotations.DefaultImpl;


/**
 * Basic implementation of a tile.
 * 
 * @author MightyPork
 */
public abstract class SimpleTile extends TileModel {
	
	public SimpleTile(int id)
	{
		super(id);
	}
	
	
	@Override
	public boolean isWalkable(Tile tile)
	{
		return isWalkable();
	}
	
	
	@Override
	public abstract boolean isWalkable();
	
	
	@Override
	public boolean hasPersistentMetadata()
	{
		return false;
	}
	
	
	@Override
	@DefaultImpl
	public void updateLogic(Tile tile, WorldAccess world, Level level, double delta)
	{
	}
	
	@Override
	@DefaultImpl
	public void updateVisual(Tile tile, WorldAccess world, Level level, double delta)
	{
	}
}
