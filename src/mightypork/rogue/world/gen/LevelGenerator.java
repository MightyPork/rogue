package mightypork.rogue.world.gen;


import java.util.Random;

import mightypork.gamecore.logging.Log;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.rogue.world.World;
import mightypork.rogue.world.entity.Entities;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.gen.rooms.Rooms;
import mightypork.rogue.world.gen.themes.ThemeBrick;
import mightypork.rogue.world.item.Items;
import mightypork.rogue.world.level.Level;


public class LevelGenerator {
	
	public static final MapTheme DUNGEON_THEME = new ThemeBrick();
	
	
	@SuppressWarnings("fallthrough")
	public static Level build(World world, long seed, int complexity, MapTheme theme, boolean lastLevel) throws WorldGenError
	{
		Log.f3("Generating level of complexity: " + complexity);
		
		final Random rand = new Random(seed + 13);
		
		final int max_size = 128;
		
		final ScratchMap map = new ScratchMap(max_size, theme, rand);
		
		// start
		if (!map.addRoom(Rooms.ENTRANCE, true)) {
			throw new WorldGenError("Could not place entrance room.");
		}
		
		for (int i = 0; i < 1 + complexity / 2 + rand.nextInt((int) (1 + complexity * 0.2)); i++) {
			map.addRoom(Rooms.BASIC, false);
			if (rand.nextInt(7) > 0) map.addRoom(Rooms.SECRET, false);
			if (rand.nextInt(7) > 0) map.addRoom(Rooms.DEAD_END, false);
		}
		
		if (!lastLevel) {
			if (!map.addRoom(Rooms.EXIT, true)) {
				throw new WorldGenError("Could not place exit room.");
			}
		}
		
		if (lastLevel) {
			if (!map.addRoom(Rooms.BOSS_ROOM, true)) {
				throw new WorldGenError("Could not place boss room.");
			}
		}
		
		
		map.buildCorridors();
		
		switch (complexity) {
			default:
			case 3:
			case 2:
				if (rand.nextInt(2) == 0) map.dropInMap(Items.CLUB.createItemDamaged(60), 50);
			case 1:
			case 0:
				if (rand.nextInt(2) == 0) map.dropInMap(Items.ROCK.createItemDamaged(10), 50);
		}
		
		if (complexity == 6) {
			map.dropInMap(Items.SWORD.createItemDamaged(40), 200);
		}
		
		if (complexity == 5) {
			map.dropInMap(Items.HAMMER.createItemDamaged(40), 100);
		}
		
		// entities - random rats
		
		
		for (int i = 0; i < 3 + complexity + rand.nextInt(1 + complexity); i++) {
			Entity e;
			
			if (rand.nextInt((int) (complexity / 1.5) + 1) != 0) {
				e = Entities.RAT_BROWN.createEntity();
			} else {
				e = Entities.RAT_GRAY.createEntity();
			}
			
			map.putEntityInMap(e, 20);
		}
		
		
		final Coord size = map.getNeededSize();
		final Level lvl = new Level(size.x, size.y);
		lvl.setWorld(world); // important for creating entities
		
		map.writeToLevel(lvl);
		
		return lvl;
	}
}
