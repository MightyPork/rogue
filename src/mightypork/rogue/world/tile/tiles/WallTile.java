package mightypork.rogue.world.tile.tiles;

import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.TileType;


public class WallTile extends SolidTile {

	public WallTile(TileModel model, TileRenderer renderer)
	{
		super(model, renderer);
	}

	@Override
	public TileType getType()
	{
		return TileType.WALL;
	}
}
