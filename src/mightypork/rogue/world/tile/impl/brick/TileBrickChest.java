package mightypork.rogue.world.tile.impl.brick;


import mightypork.gamecore.resources.Res;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.impl.TileBaseChest;
import mightypork.rogue.world.tile.render.ChestRenderer;


public class TileBrickChest extends TileBaseChest {
	
	public TileBrickChest(TileModel model)
	{
		super(model);
	}
	
	
	@Override
	protected TileRenderer makeRenderer()
	{
		return new ChestRenderer(this, Res.getTxQuad("tile.brick.floor"), Res.getTxQuad("tile.extra.chest.closed"), Res.getTxQuad("tile.extra.chest.open"));
	}
	
}
