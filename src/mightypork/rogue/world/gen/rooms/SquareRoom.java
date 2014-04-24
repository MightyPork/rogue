package mightypork.rogue.world.gen.rooms;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mightypork.rogue.world.gen.Coord;
import mightypork.rogue.world.gen.RoomBuilder;
import mightypork.rogue.world.gen.RoomDesc;
import mightypork.rogue.world.gen.Theme;
import mightypork.rogue.world.gen.ScratchMap;


public class SquareRoom implements RoomBuilder {
	
	@Override
	public RoomDesc buildToFit(ScratchMap map, Theme theme, Random rand, Coord center)
	{
		int hside = getMinHalfSide();
		if (getMaxHalfSide() > getMinHalfSide()) hside += rand.nextInt(getMaxHalfSide() - getMinHalfSide());
		
		Coord min = new Coord(center.x - hside, center.y - hside);
		Coord max = new Coord(center.x + hside, center.y + hside);
		
		for (;; hside--) {
			if (hside < getMinHalfSide()) return null;
			if (map.isClear(min, max)) break;
		}
		
		map.fill(min, max, theme.floor());
		map.border(min, max, theme.wall());
		
		List<Coord> doors = new ArrayList<>();
		
		int door_types[] = getDoorTypes();
		
		int drs = door_types[rand.nextInt(door_types.length)];
		
		Coord door;
		
		if ((drs & 1) != 0) {
			door = min.add(hside, 0);
			map.set(door, theme.door());
			doors.add(door);
		}
		
		if ((drs & 2) != 0) {
			door = max.add(-hside, 0);
			map.set(door, theme.door());
		}
		
		if ((drs & 4) != 0) {
			door = min.add(0, hside);
			map.set(door, theme.door());
		}
		
		if ((drs & 8) != 0) {
			door = max.add(0, -hside);
			map.set(door, theme.door());
		}
		
		return new RoomDesc(min.add(-1, -1), max.add(1, 1), doors);
	}
	
	
	protected int[] getDoorTypes()
	{
		//@formatter:off
		return new int[] { 
				// one
				0b0001, 0b0010, 0b0100, 0b1000,
				
				// two
				0b0011, 0b0101, 0b0110, 0b1010, 0b1100, 0b1001, 
				0b0011, 0b0101, 0b0110, 0b1010, 0b1100, 0b1001, 
				
				//three+four
				0b0111, 0b1101, 0b1011, 0b1110, 0b1111,
				0b0111, 0b1101, 0b1011, 0b1110, 0b1111,
				0b0111, 0b1101, 0b1011, 0b1110, 0b1111
		};
		//@formatter:on
	}
	
	
	protected int getMinHalfSide()
	{
		return 2;
	}
	
	
	protected int getMaxHalfSide()
	{
		return 4;
	}
	
}
