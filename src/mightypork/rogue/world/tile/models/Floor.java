package mightypork.rogue.world.tile.models;


import mightypork.rogue.world.tile.Tile;


/**
 * Template for floor tiles with no metadata
 * 
 * @author MightyPork
 */
public class Floor extends SimpleTile {
	
	public Floor(int id)
	{
		super(id);
	}
	
	
	@Override
	public boolean isWalkable(Tile tile)
	{
		return true;
	}
	
	
	@Override
	public boolean hasDroppedItems()
	{
		return true;
	}
	
	
	@Override
	public boolean doesCastShadow()
	{
		return false;
	}
}
