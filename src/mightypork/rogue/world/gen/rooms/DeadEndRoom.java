package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Sides;
import mightypork.rogue.world.gen.RoomBuilder;
import mightypork.rogue.world.gen.RoomDesc;
import mightypork.rogue.world.gen.ScratchMap;
import mightypork.rogue.world.gen.MapTheme;


public class DeadEndRoom implements RoomBuilder {
	
	@Override
	public RoomDesc buildToFit(ScratchMap map, MapTheme theme, Random rand, Coord center)
	{
		Coord low = center.add(-1, -1);
		Coord high = center;
		if (!map.isClear(low, high)) return null;
		
		map.set(center, theme.floor());
		
		return new RoomDesc(low, high);
	}
}
