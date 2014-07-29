package mightypork.rogue.world.tile.impl;


import mightypork.gamecore.core.App;
import mightypork.rogue.world.events.WorldDescendRequest;
import mightypork.rogue.world.tile.TileColors;
import mightypork.rogue.world.tile.TileModel;
import mightypork.utils.math.algo.Coord;
import mightypork.utils.math.color.Color;


public abstract class TileBaseExit extends TileBaseStairs {
	
	public TileBaseExit(TileModel model)
	{
		super(model);
	}
	
	
	@Override
	public boolean onClick()
	{
		final Coord plpos = getWorld().getPlayer().getCoord();
		if (!plpos.equals(getLevel().getExitPoint())) return false;
		
		App.bus().send(new WorldDescendRequest());
		
		return true;
	}
	
	
	@Override
	public boolean doesCastShadow()
	{
		return false;
	}
	
	
	@Override
	public Color getMapColor()
	{
		return TileColors.EXIT;
	}
}
