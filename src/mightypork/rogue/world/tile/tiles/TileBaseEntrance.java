package mightypork.rogue.world.tile.tiles;


import mightypork.gamecore.util.math.algo.Coord;
import mightypork.rogue.world.events.WorldAscendRequest;
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
}
