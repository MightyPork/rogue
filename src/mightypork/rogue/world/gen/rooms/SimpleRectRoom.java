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
		final Coord max = new Coord(center.x + width, center.y + height);
		
		if (!map.isClear(min, max)) return null;
		
		map.fill(min, max, theme.floor());
		map.border(min, max, theme.wall());
		
		for (int i = 0; i < 2 + rand.nextInt(4); i++) {
			final Coord d = min.copy();
			switch (rand.nextInt(4)) {
				case 0:
					d.y = min.y;
					d.x += 1 + rand.nextInt((width - 1) * 2);
					break;
				case 1:
					d.y = max.y;
					d.x += 1 + rand.nextInt((width - 1) * 2);
					break;
				case 2:
					d.x = min.x;
					d.y += 1 + rand.nextInt((height - 1) * 2);
					break;
				case 3:
					d.x = max.x;
					d.y += 1 + rand.nextInt((height - 1) * 2);
					break;
			}
			
			map.set(d, theme.door());
		}
		
		return new RoomDesc(min.add(-1, -1), max);
	}
	
}
