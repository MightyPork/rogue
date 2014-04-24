package mightypork.rogue.world.tile.models;


/**
 * Template for wall tiles with no metadata
 * 
 * @author MightyPork
 */
public class Wall extends AbstractTile {
	
	public Wall(int id)
	{
		super(id);
	}
	
	
	@Override
	public final boolean hasDroppedItems()
	{
		return false;
	}
	
	
	@Override
	public final boolean doesCastShadow()
	{
		return true;
	}
	
	
	@Override
	public final boolean isWall()
	{
		return true;
	}
	
	
	@Override
	public final boolean isPotentiallyWalkable()
	{
		return false;
	}
	
}
