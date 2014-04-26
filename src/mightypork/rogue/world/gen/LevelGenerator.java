package mightypork.rogue.world.gen;


import java.util.Random;

import mightypork.rogue.world.Coord;
import mightypork.rogue.world.gen.rooms.DeadEndRoom;
import mightypork.rogue.world.gen.rooms.SimpleRectRoom;
import mightypork.rogue.world.gen.themes.ThemeDungeon;
import mightypork.rogue.world.level.Level;


public class LevelGenerator {
	
	public static final Theme DUNGEON_THEME = new ThemeDungeon();
	
	public static final RoomBuilder ROOM_SQUARE = new SimpleRectRoom();
	private static final RoomBuilder DEAD_END = new DeadEndRoom();
	
	
	public static Level build(long seed, int complexity, Theme theme)
	{
		final Random rand = new Random(seed + 13);
		
		final int max_size = 128;
		
		final ScratchMap map = new ScratchMap(max_size, theme, rand);
		
		// start
		map.addRoom(ROOM_SQUARE);
		
		for (int i = 0; i < 1+complexity/4 + rand.nextInt(3 + complexity); i++) {
			map.addRoom(ROOM_SQUARE);
		}
		
		for (int i = 0; i < 1 + complexity/8 + rand.nextInt(1 + complexity); i++) {
			map.addRoom(DEAD_END);
		}
		
		map.buildCorridors();
		
		final Coord size = map.getNeededSize();
		final Level lvl = new Level(size.x, size.y);
		
		map.writeToLevel(lvl);
		
		return lvl;
	}
}
