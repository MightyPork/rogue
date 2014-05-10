package mightypork.rogue.world.tile.tiles;


import mightypork.gamecore.util.math.algo.Coord;
import mightypork.rogue.world.events.WorldDescendRequest;
import mightypork.rogue.world.tile.TileModel;


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
		
		getEventBus().send(new WorldDescendRequest());
		
		return true;
	}
	
	
	@Override
	public boolean doesCastShadow()
	{
		return false;
	}
}
