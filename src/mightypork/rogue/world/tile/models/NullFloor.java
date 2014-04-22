package mightypork.rogue.world.tile.models;


public class NullFloor extends AbstractNullTile {
	
	public NullFloor(int id)
	{
		super(id);
	}
	
	
	@Override
	public boolean isWalkable()
	{
		return true;
	}
	
}
