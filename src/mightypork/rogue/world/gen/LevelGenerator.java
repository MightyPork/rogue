package mightypork.rogue.world.gen;


import java.util.Random;

import mightypork.rogue.world.Coord;
import mightypork.rogue.world.gen.rooms.SimpleRectRoom;
import mightypork.rogue.world.gen.themes.ThemeDungeon;
import mightypork.rogue.world.level.Level;


public class LevelGenerator {
	
	public static final Theme DUNGEON_THEME = new ThemeDungeon();
	
	public static final RoomBuilder ROOM_SQUARE = new SimpleRectRoom();
	
	
	public static Level build(long seed, Theme theme)
	{
		final Random rand = new Random(seed + 13);
		
		final int max_size = 512;
		
		final ScratchMap map = new ScratchMap(max_size, theme, rand);
		
		// start
		map.addRoom(ROOM_SQUARE);
		
		for (int i = 0; i < 6 + rand.nextInt(6); i++) {
			map.addRoom(ROOM_SQUARE);
		}
		
		map.buildCorridors();
		
		final Coord size = map.getNeededSize();
		final Level lvl = new Level(size.x, size.y);
		
		map.writeToLevel(lvl);
		
		return lvl;
	}
}
