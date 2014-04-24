package mightypork.rogue.world.tile.models;


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
}
