package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.gamecore.util.math.algo.Coord;
import mightypork.rogue.world.gen.MapTheme;
import mightypork.rogue.world.gen.RoomBuilder;
import mightypork.rogue.world.gen.RoomDesc;
import mightypork.rogue.world.gen.ScratchMap;


public class DeadEndRoom implements RoomBuilder {
	
	@Override
	public RoomDesc buildToFit(ScratchMap map, MapTheme theme, Random rand, Coord center)
	{
		final Coord low = center.add(-1, -1);
		final Coord high = center;
		if (!map.isClear(low, high)) return null;
		
		map.set(center, theme.floor());
		
		return new RoomDesc(low, high);
	}
}
