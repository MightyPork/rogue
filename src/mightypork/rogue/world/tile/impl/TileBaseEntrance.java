package mightypork.rogue.world.tile.impl;


import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.color.Color;
import mightypork.rogue.world.events.WorldAscendRequest;
import mightypork.rogue.world.tile.TileColors;
import mightypork.rogue.world.tile.TileModel;


public abstract class TileBaseEntrance extends TileBaseStairs {
	
	public TileBaseEntrance(TileModel model)
	{
		super(model);
	}
	
	
	@Override
	public boolean onClick()
	{
		final Coord plpos = getWorld().getPlayer().getCoord();
		if (!plpos.equals(getLevel().getEnterPoint())) return false;
		
		getEventBus().send(new WorldAscendRequest());
		
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
		return TileColors.ENTRANCE;
	}
}
