package mightypork.rogue.world.tile.models;


import mightypork.rogue.world.tile.Tile;


/**
 * Template for wall tiles with no metadata
 * 
 * @author MightyPork
 */
public class Wall extends SimpleTile {
	
	public Wall(int id)
	{
		super(id);
	}
	
	
	@Override
	public boolean isWalkable(Tile tile)
	{
		return false;
	}
	
	
	@Override
	public boolean hasDroppedItems()
	{
		return false;
	}
	
	
	@Override
	public boolean doesCastShadow()
	{
		return true;
	}
	
}
