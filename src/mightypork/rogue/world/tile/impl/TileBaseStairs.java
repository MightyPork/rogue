package mightypork.rogue.world.tile.impl;


import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileType;


public abstract class TileBaseStairs extends TileSolid {

	public TileBaseStairs(TileModel model)
	{
		super(model);
	}


	@Override
	public TileType getType()
	{
		return TileType.STAIRS;
	}

}
