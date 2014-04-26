package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.rogue.world.Coord;
import mightypork.rogue.world.Sides;
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
		map.protect(min, max);
		
		for (int i = 0; i <= rand.nextInt(6); i++) {
			final Coord door = min.copy();
			switch (rand.nextInt(4)) {
				case 0:
					door.y = min.y;
					door.x += 1 + rand.nextInt((width - 1) * 2);
					break;
				case 1:
					door.y = max.y;
					door.x += 1 + rand.nextInt((width - 1) * 2);
					break;
				case 2:
					door.x = min.x;
					door.y += 1 + rand.nextInt((height - 1) * 2);
					break;
				case 3:
					door.x = max.x;
					door.y += 1 + rand.nextInt((height - 1) * 2);
					break;
			}
			
			if ((map.findDoors(door) & Sides.CARDINAL) == 0) {
				map.set(door, theme.door());
			}
		}
		
		return new RoomDesc(min.add(-1, -1), max);
	}
	
}
