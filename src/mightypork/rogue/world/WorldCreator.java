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
			
//			final int count = 7;
//			
//			for (int i = 1; i <= count; i++) {
//				final Level l = LevelGenerator.build(w, rand.nextLong(), i, LevelGenerator.DUNGEON_THEME, i == count);
//				w.addLevel(l);
//			}
			
			w.addLevel(LevelGenerator.build(w, rand.nextLong(), 6, LevelGenerator.DUNGEON_THEME, false));
			w.addLevel(LevelGenerator.build(w, rand.nextLong(), 7, LevelGenerator.DUNGEON_THEME, true));
			
			w.createPlayer();
			
			return w;
		}
	}
	
}
