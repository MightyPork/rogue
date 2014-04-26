package mightypork.rogue.world.tile.models;


import mightypork.rogue.world.tile.Tile;
import mightypork.util.math.color.Color;
import mightypork.util.math.color.RGB;


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
	
	
	@Override
	public Color getMapColor(Tile tile)
	{
		return RGB.GRAY_LIGHT;
	}
}
