package mightypork.rogue.world.tile.models;


import mightypork.rogue.world.tile.Tile;


/**
 * Null tile
 * 
 * @author MightyPork
 */
public abstract class AbstractNullTile extends SimpleTile {
	
	private Tile inst;
	
	
	public AbstractNullTile(int id)
	{
		super(id);
	}
	
	
	@Override
	public boolean isNullTile()
	{
		return true;
	}
	
	
	@Override
	public Tile createTile()
	{
		if (inst == null) {
			inst = new Tile(this);
		}
		
		return inst;
	}
	
	
	@Override
	public boolean hasMetadata()
	{
		return false;
	}
	
	
	@Override
	public boolean hasDroppedItems()
	{
		return false;
	}
	
	
	@Override
	public abstract boolean isWalkable();
	
	
	@Override
	public boolean doesCastShadow()
	{
		return false;
	}
}
