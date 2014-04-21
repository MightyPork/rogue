package mightypork.rogue.world.tile.models;


/**
 * Template for floor tiles with no metadata
 * 
 * @author MightyPork
 */
public class Floor extends SimpleTile {
	
	public Floor(int id, String sheetKey)
	{
		super(id, sheetKey);
	}
	
	
	@Override
	public boolean isPotentiallyWalkable()
	{
		return true;
	}
	
}
