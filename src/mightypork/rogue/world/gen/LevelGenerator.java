package mightypork.rogue.world.gen;


import java.util.Random;

import mightypork.gamecore.logging.Log;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.rogue.world.World;
import mightypork.rogue.world.entity.Entities;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.gen.rooms.Rooms;
import mightypork.rogue.world.gen.themes.ThemeBrick;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.Items;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.tile.Tile;


public class LevelGenerator {
	
	public static final MapTheme DUNGEON_THEME = new ThemeBrick();
	
	
	public static Level build(World world, long seed, int complexity, MapTheme theme)
	{
		Log.f3("Generating level of complexity: " + complexity);
		
		final Random rand = new Random(seed + 13);
		
		final int max_size = 128;
		
		final ScratchMap map = new ScratchMap(max_size, theme, rand);
		
		// start
		map.addRoom(Rooms.BASIC);
		
		for (int i = 0; i < 1 + complexity / 2 + rand.nextInt((int) (1 + complexity * 0.3)); i++) {
			map.addRoom(Rooms.BASIC);
			if (rand.nextInt(7) > 0) map.addRoom(Rooms.SECRET);
			if (rand.nextInt(6) > 0) map.addRoom(Rooms.DEAD_END);
		}
		
		map.buildCorridors();
		
		final Coord size = map.getNeededSize();
		final Level lvl = new Level(size.x, size.y);
		
		map.writeToLevel(lvl);
		
		// TODO tmp
		// spawn rats
		
		final Coord pos = Coord.make(0, 0);
		for (int i = 0; i < 3 + complexity + rand.nextInt(1 + complexity); i++) {
			
			final Entity e = Entities.RAT.createEntity(world);
			
			for (int j = 0; j < 20; j++) {
				pos.x = rand.nextInt(lvl.getWidth());
				pos.y = rand.nextInt(lvl.getHeight());
				
				if (lvl.addEntity(e, pos)) break;
			}
		}
		
		for (int i = 0; i < 4 + complexity + rand.nextInt(1 + complexity); i++) {
			
			final Item meat = Items.MEAT.createItem();
			
			for (int j = 0; j < 20; j++) {
				pos.x = rand.nextInt(lvl.getWidth());
				pos.y = rand.nextInt(lvl.getHeight());
				
				final Tile t = lvl.getTile(pos);
				if (t.dropItem(meat)) break;
			}
		}
		
		
		return lvl;
	}
}
