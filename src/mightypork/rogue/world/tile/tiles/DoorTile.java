package mightypork.rogue.world.tile.tiles;

import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.TileType;
import mightypork.util.math.color.Color;
import mightypork.util.math.color.PAL16;


public class DoorTile extends BasicTile {

	public DoorTile(int id, TileRenderer renderer)
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
		return TileType.DOOR;
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
		return PAL16.NEWPOOP;
	}
	
}
