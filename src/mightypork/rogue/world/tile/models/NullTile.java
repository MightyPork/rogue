package mightypork.rogue.world.tile.models;


import mightypork.rogue.world.tile.Tile;
import mightypork.util.math.color.Color;
import mightypork.util.math.color.RGB;


public class NullTile extends AbstractNullTile {
	
	public NullTile(int id)
	{
		super(id);
	}
	
	
	@Override
	public boolean isPotentiallyWalkable()
	{
		return false;
	}
	
	
	@Override
	public Color getMapColor(Tile tile)
	{
		return RGB.NONE;
	}
}
