package mightypork.rogue.world.tile.impl.brick;


import mightypork.gamecore.resources.Res;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.impl.TileBaseExit;
import mightypork.rogue.world.tile.render.OneFrameTileRenderer;


public class TileBrickExit extends TileBaseExit {

	public TileBrickExit(TileModel model)
	{
		super(model);
	}


	@Override
	protected TileRenderer makeRenderer()
	{
		return new OneFrameTileRenderer(this, Res.getTxQuad("tile.brick.stairs.down"));
	}

}
