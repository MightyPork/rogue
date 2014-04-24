package mightypork.rogue.world.gen;


import java.util.Random;

import mightypork.rogue.world.WorldPos;
import mightypork.rogue.world.gen.rooms.IntersectionRoom;
import mightypork.rogue.world.gen.rooms.SquareRoom;
import mightypork.rogue.world.gen.themes.ThemeDungeon;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.Tiles;
import mightypork.util.logging.Log;


public class LevelGenerator {
	
	public static final Theme DUNGEON_THEME = new ThemeDungeon();
	
	public static final RoomBuilder ROOM_SQUARE = new SquareRoom();
	public static final RoomBuilder ROOM_INTERSECTION = new IntersectionRoom();
	
	
	public static Level build(long seed, Theme theme)
	{
		Random rand = new Random(seed + 47);
		
		final int max_size = 500;
		
		ScratchMap map = new ScratchMap(max_size, theme, rand);
		
		// start
		map.addRoom(ROOM_SQUARE);
		
		for (int i = 0; i < 5+rand.nextInt(4); i++) {
			map.addRoom(ROOM_SQUARE);
			for(int j=0;j<4;j++) map.addRoom(ROOM_INTERSECTION);
		}
		
		
		Coord size = map.getNeededSize();
		Level lvl = new Level(size.x, size.y);
		
		map.writeToLevel(lvl);
		
		return lvl;
	}
}
