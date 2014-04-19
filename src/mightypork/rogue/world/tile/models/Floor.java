package mightypork.rogue.world.tile.models;


public class Floor extends SimpleTile {
	
	public Floor(int id, String sheetKey)
	{
		super(id, sheetKey);
	}
	
	
	@Override
	public boolean isPotentiallyWalkable()
	{
		return true;
	}
	
}
