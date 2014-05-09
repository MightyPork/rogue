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
			
			for (int i = 0; i < 7; i++) {
				final Level l = LevelGenerator.build(w, rand.nextLong(), i, LevelGenerator.DUNGEON_THEME);
				w.addLevel(l);
			}
			
			w.createPlayer(0);
			
			return w;
		}
	}
	
}
