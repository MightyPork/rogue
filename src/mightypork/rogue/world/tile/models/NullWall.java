package mightypork.rogue.world.tile.models;


import mightypork.rogue.world.tile.Tile;


public class NullWall extends AbstractNullTile {
	
	public NullWall(int id)
	{
		super(id);
	}
	
	
	@Override
	public boolean isWalkable(Tile tile)
	{
		return false;
	}
}
