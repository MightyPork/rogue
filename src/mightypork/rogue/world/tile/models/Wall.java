package mightypork.rogue.world.tile.models;


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
	public boolean isWalkable()
	{
		return false;
	}
	
	
	@Override
	public boolean hasDroppedItems()
	{
		return false;
	}
	
}
