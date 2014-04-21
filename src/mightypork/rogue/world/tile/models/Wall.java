package mightypork.rogue.world.tile.models;


/**
 * Template for wall tiles with no metadata
 * 
 * @author MightyPork
 */
public class Wall extends SimpleTile {
	
	public Wall(int id, String sheetKey)
	{
		super(id, sheetKey);
	}
	
	
	@Override
	public boolean isPotentiallyWalkable()
	{
		return false;
	}
	
}
