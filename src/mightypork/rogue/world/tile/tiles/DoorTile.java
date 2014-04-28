package mightypork.rogue.world.tile.tiles;


import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.TileType;


public class DoorTile extends SolidTile {
	
	public DoorTile(int id, TileRenderer renderer) {
		super(id, renderer);
	}
	
	@Override
	public boolean isWalkable()
	{
		return true;
	}
	
	@Override
	public TileType getType()
	{
		return TileType.DOOR;
	}
}
