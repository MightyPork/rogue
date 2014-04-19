package mightypork.rogue.world.tile.models;


public class Wall extends SimpleTile {
	
	public Wall(int id, String sheetKey)
	{
		super(id, sheetKey);
	}
	
	
	@Override
	public boolean isPotentiallyWalkable()
	{
		return false;
	}
	
}
