package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Sides;
import mightypork.rogue.world.gen.MapTheme;
import mightypork.rogue.world.gen.RoomBuilder;
import mightypork.rogue.world.gen.RoomDesc;
import mightypork.rogue.world.gen.ScratchMap;
import mightypork.rogue.world.gen.TileProtectLevel;
import mightypork.rogue.world.tile.TileModel;


public abstract class AbstractRectRoom implements RoomBuilder {
	
	@Override
	public RoomDesc buildToFit(ScratchMap map, MapTheme theme, Random rand, Coord center)
	{
		// half width, half height actually
		final Coord innerSize = getInnerSize(rand);
		final int width = 2 + innerSize.x;
		final int height = 2 + innerSize.y;
		
		final int wLow = width / 2;
		final int wHigh = width - wLow;
		final int hLow = height / 2;
		final int hHigh = height - hLow;
		
		final Coord min = new Coord(center.x - wLow, center.y - hLow);
		final Coord max = new Coord(center.x + wHigh, center.y + hHigh);
		
		if (!map.isClear(min.add(-1, -1), max)) return null;
		
		map.fill(min, max, theme.floor());
		map.border(min, max, theme.wall());
		map.protect(min, max, getWallProtectionLevel());
		
		placeDoors(map, theme, rand, min, max);
		
		buildExtras(map, theme, rand, min, max);
		
		return new RoomDesc(min.add(-1, -1), max);
	}
	
	
	protected void placeDoors(ScratchMap map, MapTheme theme, Random rand, Coord min, Coord max)
	{
		final int width = max.x - min.x;
		final int height = max.y - min.y;
		
		for (int i = 0, j = 0; i <= getDoorCount(rand) && j < 100; j++) { // j is to prevent inf loop
			final Coord door = min.copy();
			switch (rand.nextInt(4)) {
				case 0:
					door.y = min.y;
					door.x += 1 + rand.nextInt(width - 1);
					break;
				case 1:
					door.y = max.y;
					door.x += 1 + rand.nextInt(width - 1);
					break;
				case 2:
					door.x = min.x;
					door.y += 1 + rand.nextInt(height - 1);
					break;
				case 3:
					door.x = max.x;
					door.y += 1 + rand.nextInt(height - 1);
					break;
			}
			
			if ((map.findDoors(door) & Sides.MASK_CARDINAL) == 0) {
				map.set(door, getDoorType(theme, rand));
				i++; // increment pointer
			}
		}
	}
	
	
	@DefaultImpl
	protected void buildExtras(ScratchMap map, MapTheme theme, Random rand, Coord min, Coord max)
	{
	}
	
	
	protected abstract Coord getInnerSize(Random rand);
	
	
	protected abstract TileProtectLevel getWallProtectionLevel();
	
	
	protected abstract TileModel getDoorType(MapTheme theme, Random rand);
	
	
	protected abstract int getDoorCount(Random rand);
}
