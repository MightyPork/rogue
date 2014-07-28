package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.rogue.world.gen.MapTheme;
import mightypork.rogue.world.gen.TileProtectLevel;
import mightypork.rogue.world.tile.TileModel;
import mightypork.utils.math.Calc;
import mightypork.utils.math.algo.Coord;


public abstract class SecretRoom extends AbstractRectRoom {

	@Override
	protected TileModel getDoorType(MapTheme theme, Random rand)
	{
		return rand.nextInt(5) == 0 ? theme.passage() : theme.secretDoor();
	}


	@Override
	protected int getDoorCount(Random rand)
	{
		return Calc.randInt(rand, 1, 3);
	}


	@Override
	protected TileProtectLevel getWallProtectionLevel()
	{
		return TileProtectLevel.STRONG;
	}


	@Override
	protected Coord getInnerSize(Random rand)
	{
		return Coord.make(3 + rand.nextInt(2), 3 + rand.nextInt(2));
	}
}
