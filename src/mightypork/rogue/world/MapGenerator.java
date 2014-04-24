package mightypork.rogue.world;


import java.util.Random;

import mightypork.rogue.world.gen.LevelGenerator;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.tile.Tiles;
import mightypork.rogue.world.tile.models.TileModel;


public class MapGenerator {
	
	public static final Random rand = new Random();
	
	
	public static World createWorld(long seed)
	{
		synchronized (rand) {
			rand.setSeed(seed);
			
			final World w = new World();
			w.setSeed(seed);
			
			Level l;
			
			// first level
			l = LevelGenerator.build(rand.nextLong(), LevelGenerator.DUNGEON_THEME);
			w.addLevel(l);
			w.createPlayer(l.getEnterPoint(), 0);
			
			return w;
		}
	}
	
}
