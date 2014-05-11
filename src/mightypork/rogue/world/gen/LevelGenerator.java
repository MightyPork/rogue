package mightypork.rogue.world.gen;


import java.util.Random;

import mightypork.gamecore.logging.Log;
import mightypork.gamecore.util.math.Calc;
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
	public static Level build(World world, long seed, int level, MapTheme theme, boolean lastLevel) throws WorldGenError
	{
		Log.f3("Generating level of complexity: " + level);
		
		final Random rand = new Random(seed + 13);
		
		final int max_size = 128;
		
		final ScratchMap map = new ScratchMap(max_size, theme, rand);
		
		// start
		if (!map.addRoom(Rooms.ENTRANCE, true)) {
			throw new WorldGenError("Could not place entrance room.");
		}
		
		for (int i = 0; i < Calc.randInt(rand, 1 + level, (int) (1 + level * 1.5)); i++) {
			map.addRoom(Rooms.BASIC, false);
			
			// spice it up with dead ends
			if (rand.nextInt(6) > 0) map.addRoom(Rooms.DEAD_END, false);
		}
		
		for (int i = 0; i < Calc.randInt(rand, 1, level / 3); i++) {
			map.addRoom(Rooms.TREASURE, false);
		}
		
		if (!lastLevel) {
			if (!map.addRoom(Rooms.EXIT, true)) {
				throw new WorldGenError("Could not place exit room.");
			}
		}
		
		if (lastLevel) {
			if (!map.addRoom(Rooms.BOSS, true)) {
				throw new WorldGenError("Could not place boss room.");
			}
		}
		
		map.addRoom(Rooms.HEART_ROOM, true);
		
		
		map.buildCorridors();
		
		switch (level) {
			default:
			case 3:
			case 2:
				if (rand.nextInt(2) == 0) map.putItemInMap(Items.CLUB.createItemDamaged(30), 50);
			case 1:
				if (rand.nextInt(2) == 0) map.putItemInMap(Items.ROCK.createItemDamaged(10), 50);
		}
		
		if (level == 1) {
			map.putItemInMap(Items.BONE.createItemDamaged(20), 60);
		}
		
		if (level == 2) {
			map.putItemInMap(Items.CLUB.createItemDamaged(50), 60);
		}
		
		if (level == 6) {
			map.putItemInMap(Items.SWORD.createItemDamaged(60), 200);
		}
		
		if (level == 4) {
			map.putItemInMap(Items.HAMMER.createItemDamaged(60), 100);
		}
		
		// entities - random rats
		
		
		for (int i = 0; i < Calc.randInt(rand, (int) (3 + level * 1.5), (int) (3 + level * 2.5)); i++) {
			Entity e;
			
			if (level > 2 && rand.nextInt(level - 2 + 1) != 0) {
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
