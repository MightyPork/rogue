package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.rogue.world.Coord;
import mightypork.rogue.world.gen.RoomBuilder;
import mightypork.rogue.world.gen.RoomDesc;
import mightypork.rogue.world.gen.ScratchMap;
import mightypork.rogue.world.gen.Theme;


public class DeadEndRoom implements RoomBuilder {
	
	@Override
	public RoomDesc buildToFit(ScratchMap map, Theme theme, Random rand, Coord center)
	{
		final Coord min = new Coord(center.x - 1, center.y - 1);
		final Coord max = new Coord(center.x + 1, center.y + 1);
		
		if (!map.isClear(min, max)) return null;
		
		map.fill(min, max, theme.floor());
		map.border(min, max, theme.wall());
		
		return new RoomDesc(min, max);
	}
}
