package mightypork.rogue.world.tile.models;


import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.tile.Tile;
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
	public abstract boolean isWalkable(Tile tile);
	
	
	@Override
	public boolean isDoor()
	{
		return false;
	}
	
	
	@Override
	public boolean hasMetadata()
	{
		return false;
	}
	
	
	@Override
	@DefaultImpl
	public void update(Tile tile, Level level, double delta)
	{
	}
}
