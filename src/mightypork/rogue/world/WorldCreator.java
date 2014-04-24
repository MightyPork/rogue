package mightypork.rogue.world;


import java.util.Random;

import mightypork.rogue.world.gen.LevelGenerator;
import mightypork.rogue.world.level.Level;


public class WorldCreator {
	
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
