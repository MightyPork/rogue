package mightypork.rogue.world.tile.tiles;

import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.TileType;
import mightypork.util.math.color.Color;
import mightypork.util.math.color.RGB;


public class WallTile extends BasicTile {

	public WallTile(int id, TileRenderer renderer)
	{
		super(id, renderer);
	}

	@Override
	public boolean isPotentiallyWalkable()
	{
		return false;
	}

	@Override
	public TileType getType()
	{
		return TileType.WALL;
	}

	@Override
	public boolean canHaveItems()
	{
		return false;
	}

	@Override
	public boolean doesCastShadow()
	{
		return true;
	}

	@Override
	public Color getMapColor()
	{
		return RGB.GRAY_LIGHT;
	}
	
}
