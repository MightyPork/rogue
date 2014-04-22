package mightypork.rogue.world.tile.models;


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
	public boolean isWalkable()
	{
		return true;
	}
	
	
	@Override
	public boolean hasDroppedItems()
	{
		return true;
	}
	
}
