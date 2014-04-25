package mightypork.rogue.world.tile.models;


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
	
}
