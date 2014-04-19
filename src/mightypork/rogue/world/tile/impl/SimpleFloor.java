package mightypork.rogue.world.tile.impl;

public class SimpleFloor extends SimpleTile {

	public SimpleFloor(int id, String sheetKey) {
		super(id, sheetKey);
	}

	@Override
	public boolean isWalkable()
	{
		return true;
	}
	
}
