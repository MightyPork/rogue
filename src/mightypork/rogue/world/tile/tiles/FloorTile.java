package mightypork.rogue.world.tile.tiles;


import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.TileType;


public class FloorTile extends GroundTile {
	
	public FloorTile(int id, TileRenderer renderer)
	{
		super(id, renderer);
	}
	
	
	@Override
	public TileType getType()
	{
		return TileType.FLOOR;
	}
}
