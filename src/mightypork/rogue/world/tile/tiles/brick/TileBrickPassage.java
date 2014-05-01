package mightypork.rogue.world.tile.tiles.brick;


import mightypork.rogue.Res;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.tiles.TileBasePassage;


public class TileBrickPassage extends TileBasePassage {
	
	public TileBrickPassage(TileModel model)
	{
		super(model, Res.sheet("tile.brick.passage"));
	}
	
}
