package mightypork.rogue.world.tile.models;


public class NullWall extends AbstractNullTile {
	
	public NullWall(int id)
	{
		super(id);
	}
	
	
	@Override
	public boolean isWalkable()
	{
		return false;
	}
	
}
