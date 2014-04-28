package mightypork.rogue.world.tile.tiles;


import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.TileType;
import mightypork.util.math.color.Color;
import mightypork.util.math.color.RGB;


public class FloorTile extends TileWithItems {
	
	public FloorTile(int id, TileRenderer renderer)
	{
		super(id, renderer);
	}
	
	
	@Override
	public boolean isPotentiallyWalkable()
	{
		return true;
	}
	
	
	@Override
	public TileType getType()
	{
		return TileType.FLOOR;
	}
	
	
	@Override
	public boolean doesCastShadow()
	{
		return false;
	}
	
	
	@Override
	public Color getMapColor()
	{
		return RGB.GRAY_DARK;
	}
	
}
