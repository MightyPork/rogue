package mightypork.rogue.world.tile.impl.brick;


import mightypork.rogue.Res;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.impl.TileBasePassage;


public class TileBrickPassage extends TileBasePassage {
	
	public TileBrickPassage(TileModel model)
	{
		super(model, Res.txs("tile.brick.passage"));
	}
	
}
