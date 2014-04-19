package mightypork.rogue.world.tile.impl;


public class SimpleWall extends SimpleTile {

	public SimpleWall(int id, String sheetKey) {
		super(id, sheetKey);
	}

	@Override
	public boolean isWalkable()
	{
		return false;
	}
	
}
