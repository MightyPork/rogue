package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.gamecore.util.math.Calc;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.rogue.world.entity.Entities;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.gen.MapTheme;
import mightypork.rogue.world.gen.ScratchMap;
import mightypork.rogue.world.gen.WorldGenError;


public class BossRoom extends SecretRoom {
	
	@Override
	protected int getDoorCount(Random rand)
	{
		return Calc.randInt(rand, 1, 3);
	}
	
	
	@Override
	protected void buildExtras(ScratchMap map, MapTheme theme, Random rand, Coord min, Coord max)
	{
		final Entity boss = Entities.RAT_BOSS.createEntity();
		if (!map.putEntityInArea(boss, min, max, 100)) {
			
			// just place it anywhere then
			if (!map.putEntityInMap(boss, 100)) {
				throw new WorldGenError("Could not place boss.");
			}
			
		}
	}
}
