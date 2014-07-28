package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.rogue.world.entity.Entities;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.gen.MapTheme;
import mightypork.rogue.world.gen.ScratchMap;
import mightypork.rogue.world.gen.WorldGenError;
import mightypork.utils.math.algo.Coord;


public class BossRoom extends SecretRoom {

	@Override
	protected int getDoorCount(Random rand)
	{
		return 1;
	}


	@Override
	protected void buildExtras(ScratchMap map, MapTheme theme, Random rand, Coord min, Coord max)
	{
		final Coord bossPos = min.add(3, 3);

		final Entity boss = Entities.RAT_BOSS.createEntity();
		if (!map.addEntity(boss, bossPos)) {
			throw new WorldGenError("Could not place boss.");
		}

		Entity rat;

		// 4 guardian rats

		rat = Entities.RAT_BROWN.createEntity();
		map.addEntity(rat, min.add(1, 1));

		rat = Entities.RAT_BROWN.createEntity();
		map.addEntity(rat, Coord.make(max.x - 1, min.y + 1));

		rat = Entities.RAT_BROWN.createEntity();
		map.addEntity(rat, max.add(-1, -1));

		rat = Entities.RAT_BROWN.createEntity();
		map.addEntity(rat, Coord.make(min.x + 1, max.y - 1));

	}


	@Override
	protected Coord getInnerSize(Random rand)
	{
		return Coord.make(5, 5);
	}
}
