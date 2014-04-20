package mightypork.rogue.world;


import java.util.Random;

import mightypork.rogue.world.map.LevelMap;
import mightypork.rogue.world.tile.Tiles;


public class MapGenerator {
	
	public static final Random rand = new Random();
	
	
	public static World createWorld(long seed)
	{
		synchronized (rand) {
			rand.setSeed(seed);
			
			World w = new World();
			w.setSeed(seed);
			
			int levels = 4 + rand.nextInt(6);
			
			for (int i = 0; i < levels; i++) {
				w.addLevel(createLevel(rand.nextLong()));
			}
			
			// TODO place on start position
			w.setPlayer(new LocalPlayer(10, 10, 0));
			return w;	
		}
	}
	
	
	private static LevelMap createLevel(long seed)
	{
		// TODO
		
		LevelMap lm = new LevelMap(20, 20);
		
		lm.fill(Tiles.CRYSTAL_FLOOR);
		
		Random rand = new Random();
		rand.setSeed(seed);
		
		for (int i = 0; i < 150; i++) {
			lm.setTile(Tiles.CRYSTAL_WALL, rand.nextInt(20), rand.nextInt(20));
		}
		
		return lm;
	}
	
}
