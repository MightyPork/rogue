package mightypork.rogue.world.tile.tiles.brick;


import mightypork.rogue.Res;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.render.OneFrameTileRenderer;
import mightypork.rogue.world.tile.tiles.TileBaseEntrance;


public class TileBrickEntrance extends TileBaseEntrance {
	
	public TileBrickEntrance(TileModel model)
	{
		super(model);
	}
	
	
	@Override
	protected TileRenderer makeRenderer()
	{
		return new OneFrameTileRenderer(this, Res.txq("tile.brick.stairs.up"));
	}
	
}
