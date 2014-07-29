package mightypork.rogue.world.tile.impl.brick;


import mightypork.gamecore.resources.Res;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.impl.TileBaseFloor;


public class TileBrickFloor extends TileBaseFloor {
	
	public TileBrickFloor(TileModel model)
	{
		super(model, Res.txSheet("tile.brick.floor"));
	}
	
}
