package mightypork.rogue.world;


import java.util.Random;

import mightypork.rogue.world.map.Level;
import mightypork.rogue.world.tile.Tiles;


public class MapGenerator {
	
	public static final Random rand = new Random();
	
	
	public static World createWorld(long seed)
	{
		synchronized (rand) {
			rand.setSeed(seed);
			
			final World w = new World();
			w.setSeed(seed);
			
			final int levels = 4 + rand.nextInt(6);
			
			for (int i = 0; i < levels; i++) {
				w.addLevel(createLevel(rand.nextLong()));
			}
			
			// TODO place on start position
			w.setPlayer(new PlayerInfo(10, 10, 0));
			return w;
		}
	}
	
	
	private static Level createLevel(long seed)
	{
		// TODO
		
		final Level lm = new Level(20, 20);
		lm.setSeed(seed);
		
		lm.fill(Tiles.CRYSTAL_FLOOR);
		
		final Random rand = new Random();
		rand.setSeed(seed);
		
		for (int i = 0; i < 150; i++) {
			lm.setTile(Tiles.CRYSTAL_WALL, rand.nextInt(20), rand.nextInt(20));
		}
		
		return lm;
	}
	
}
