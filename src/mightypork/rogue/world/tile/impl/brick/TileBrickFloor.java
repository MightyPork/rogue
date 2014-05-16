package mightypork.rogue.world.tile.impl.brick;


import mightypork.rogue.Res;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.impl.TileBaseFloor;


public class TileBrickFloor extends TileBaseFloor {
	
	public TileBrickFloor(TileModel model)
	{
		super(model, Res.getTxSheet("tile.brick.floor"));
	}
	
}
