package mightypork.rogue.world;


import java.util.Random;

import mightypork.rogue.world.map.Level;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.Tiles;


public class MapGenerator {
	
	public static final Random rand = new Random();
	
	
	public static World createWorld(long seed)
	{
		synchronized (rand) {
			rand.setSeed(seed);
			
			final World w = new World();
			w.setSeed(seed);
			
			w.addLevel(createLevel(rand.nextLong(), Tiles.CRYSTAL_FLOOR, Tiles.CRYSTAL_WALL));
			w.addLevel(createLevel(rand.nextLong(), Tiles.BRCOBBLE_FLOOR, Tiles.BRCOBBLE_WALL));
			
			// TODO place on start position
			w.getPlayer().teleport(new WorldPos(10, 10, 0));
			return w;
		}
	}
	
	
	private static Level createLevel(long seed, TileModel floor, TileModel wall)
	{
		// TODO
		
		final Level lm = new Level(20, 20);
		lm.setSeed(seed);
		
		lm.fill(floor);
		
		final Random rand = new Random();
		rand.setSeed(seed);
		
		for (int i = 0; i < 150; i++) {
			lm.setTile(wall, rand.nextInt(20), rand.nextInt(20));
		}
		
		return lm;
	}
	
}
