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
	public static Level build(World world, long seed, int complexity, MapTheme theme, boolean lastLevel)
	{
		Log.f3("Generating level of complexity: " + complexity);
		
		final Random rand = new Random(seed + 13);
		
		final int max_size = 128;
		
		final ScratchMap map = new ScratchMap(max_size, theme, rand);
		
		// start
		map.addRoom(Rooms.ENTRANCE, true);
		
		for (int i = 0; i < 1 + complexity / 2 + rand.nextInt((int) (1 + complexity * 0.2)); i++) {
			map.addRoom(Rooms.BASIC, false);
			if (rand.nextInt(7) > 0) map.addRoom(Rooms.SECRET, false);
			if (rand.nextInt(7) > 0) map.addRoom(Rooms.DEAD_END, false);
		}
		
		if (!lastLevel) map.addRoom(Rooms.EXIT, true);
		
		map.buildCorridors();
		
		switch(complexity) {
			default:
			case 3:
			case 2:
				if(rand.nextInt(2)==0) map.dropInMap(Items.CLUB.createItem(), 50);
			case 1:
			case 0:
				if(rand.nextInt(2)==0) map.dropInMap(Items.ROCK.createItem(), 50);
		}
		
		if(complexity == 6) {
			map.dropInMap(Items.SWORD.createItem(), 200);
		}
		
		if(complexity == 5) {
			map.dropInMap(Items.HAMMER.createItem(), 100);
		}
		
		final Coord size = map.getNeededSize();
		final Level lvl = new Level(size.x, size.y);
		
		map.writeToLevel(lvl);
		
		// TODO tmp
		// spawn rats
		
		// TODO entities in ScratchMap itself
		
		final Coord pos = Coord.make(0, 0);
		for (int i = 0; i < 3 + complexity + rand.nextInt(1 + complexity); i++) {
			
			final Entity e = Entities.RAT.createEntity(world);
			
			for (int j = 0; j < 20; j++) {
				pos.x = rand.nextInt(lvl.getWidth());
				pos.y = rand.nextInt(lvl.getHeight());
				
				if (lvl.addEntity(e, pos)) break;
			}
		}
		
		return lvl;
	}
}
