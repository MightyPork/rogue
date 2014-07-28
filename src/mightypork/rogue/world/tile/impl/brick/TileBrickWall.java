package mightypork.rogue.world.tile.impl.brick;


import mightypork.gamecore.resources.Res;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.impl.TileBaseWall;


public class TileBrickWall extends TileBaseWall {

	public TileBrickWall(TileModel model)
	{
		super(model, Res.txSheet("tile.brick.wall"));
	}

}
