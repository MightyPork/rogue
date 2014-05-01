package mightypork.rogue.world.tile.tiles.brick;


import mightypork.rogue.Res;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.tiles.TileBaseFloor;


public class TileBrickFloor extends TileBaseFloor {
	
	public TileBrickFloor(TileModel model)
	{
		super(model, Res.sheet("tile.brick.floor"));
	}
	
}
