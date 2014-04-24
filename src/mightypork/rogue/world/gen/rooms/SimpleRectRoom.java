package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.rogue.world.Coord;
import mightypork.rogue.world.gen.RoomBuilder;
import mightypork.rogue.world.gen.RoomDesc;
import mightypork.rogue.world.gen.ScratchMap;
import mightypork.rogue.world.gen.Theme;


public class SimpleRectRoom implements RoomBuilder {
	
	@Override
	public RoomDesc buildToFit(ScratchMap map, Theme theme, Random rand, Coord center)
	{
		// half width, half height actually
		final int width = 2 + rand.nextInt(2);
		final int height = 2 + rand.nextInt(2);
		
		final Coord min = new Coord(center.x - width, center.y - height);
		final Coord max = new Coord(center.x + height, center.y + height);
		
		if (!map.isClear(min, max)) return null;
		
		map.fill(min, max, theme.floor());
		map.border(min, max, theme.wall());
		
		// TODO place some doors
		
		return new RoomDesc(min.add(-1, -1), max.add(1, 1));
	}
	
}
