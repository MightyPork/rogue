package mightypork.rogue.world.gen;


import java.util.Random;

import mightypork.gamecore.util.math.algo.Coord;
import mightypork.rogue.world.gen.rooms.DeadEndRoom;
import mightypork.rogue.world.gen.rooms.Rooms;
import mightypork.rogue.world.gen.themes.ThemeBrick;
import mightypork.rogue.world.level.Level;


public class LevelGenerator {
	
	public static final MapTheme DUNGEON_THEME = new ThemeBrick();
	
	
	public static Level build(long seed, int complexity, MapTheme theme)
	{
		final Random rand = new Random(seed + 13);
		
		final int max_size = 128;
		
		final ScratchMap map = new ScratchMap(max_size, theme, rand);
		
		// start
		map.addRoom(Rooms.BASIC);
		
		for (int i = 0; i < 2 + complexity / 2 + rand.nextInt((int) (1 + complexity * 0.3)); i++) {
			map.addRoom(Rooms.BASIC);
			if (rand.nextInt(7) > 0) map.addRoom(Rooms.SECRET);
			if (rand.nextInt(6) > 0) map.addRoom(Rooms.DEAD_END);
		}
		
		map.buildCorridors();
		
		final Coord size = map.getNeededSize();
		final Level lvl = new Level(size.x, size.y);
		
		map.writeToLevel(lvl);
		
		return lvl;
	}
}
