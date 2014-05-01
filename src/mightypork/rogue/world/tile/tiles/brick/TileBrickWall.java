package mightypork.rogue.world.tile.tiles.brick;

import mightypork.gamecore.resources.textures.TxSheet;
import mightypork.rogue.Res;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.tiles.TileBaseWall;


public class TileBrickWall extends TileBaseWall {

	public TileBrickWall(TileModel model)
	{
		super(model, Res.sheet("tile.brick.wall"));
	}
	
}
