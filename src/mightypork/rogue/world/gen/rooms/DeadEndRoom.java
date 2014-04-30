package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.gamecore.util.math.algo.Coord;
import mightypork.rogue.world.gen.RoomBuilder;
import mightypork.rogue.world.gen.RoomDesc;
import mightypork.rogue.world.gen.ScratchMap;
import mightypork.rogue.world.gen.Theme;


public class DeadEndRoom implements RoomBuilder {
	
	@Override
	public RoomDesc buildToFit(ScratchMap map, Theme theme, Random rand, Coord center)
	{
		
		if (!map.isClear(center, center)) return null;
		
		map.set(center, theme.floor());
		
		return new RoomDesc(center, center);
	}
}
