package mightypork.rogue.world;


import java.util.Random;

import mightypork.rogue.world.gen.LevelGenerator;


public class WorldCreator {
	
	public static final Random rand = new Random();
	
	
	public static World createWorld(long seed)
	{
		synchronized (rand) {
			rand.setSeed(seed);
			
			final World w = new World();
			w.setSeed(seed);
			
			final int count = 7;
			
			for (int lvl = 1; lvl <= count; lvl++) {
				w.addLevel(LevelGenerator.build(w, rand.nextLong(), lvl, LevelGenerator.DUNGEON_THEME, lvl == count));
			}
			
			w.createPlayer();
			
			return w;
		}
	}
	
}
