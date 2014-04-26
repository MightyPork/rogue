package mightypork.rogue.world.tile.models;


import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;
import mightypork.util.annotations.DefaultImpl;


/**
 * Basic implementation of a tile.
 * 
 * @author MightyPork
 */
public abstract class AbstractTile extends TileModel {
	
	public AbstractTile(int id)
	{
		super(id);
	}
	
	
	@Override
	public boolean isWalkable(Tile tile)
	{
		return isPotentiallyWalkable();
	}
	
	
	@Override
	public boolean isDoor()
	{
		return false;
	}
	
	
	@Override
	public boolean isWall()
	{
		return false;
	}
	
	
	@Override
	public boolean isFloor()
	{
		return false;
	}
	
	@Override
	public boolean doesReceiveShadow()
	{
		return isFloor();
	}
	
	
	@Override
	@DefaultImpl
	public void update(Tile tile, Level level, double delta)
	{
	}
}
