package mightypork.rogue.world.tile.models;


import mightypork.rogue.world.tile.Tile;


public class NullFloor extends AbstractNullTile {
	
	public NullFloor(int id)
	{
		super(id);
	}
	
	
	@Override
	public boolean isWalkable(Tile tile)
	{
		return true;
	}
	
}
