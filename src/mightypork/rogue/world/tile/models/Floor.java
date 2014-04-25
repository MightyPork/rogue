package mightypork.rogue.world.tile.models;


import mightypork.rogue.world.tile.Tile;
import mightypork.util.math.color.COMMODORE;
import mightypork.util.math.color.Color;


public class Floor extends AbstractTile {
	
	public Floor(int id)
	{
		super(id);
	}
	
	
	@Override
	public boolean isPotentiallyWalkable()
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
	
	
	@Override
	public boolean isFloor()
	{
		return true;
	}
	
	
	@Override
	public Color getMapColor(Tile tile)
	{
		return COMMODORE.GRAY_DARK;
	}
}
