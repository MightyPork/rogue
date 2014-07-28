package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.rogue.world.gen.MapTheme;
import mightypork.rogue.world.gen.TileProtectLevel;
import mightypork.rogue.world.tile.TileModel;
import mightypork.utils.math.algo.Coord;


public class BasicRoom extends AbstractRectRoom {

	@Override
	protected TileModel getDoorType(MapTheme theme, Random rand)
	{
		return rand.nextInt(4) == 0 ? theme.passage() : theme.door();
	}


	@Override
	protected int getDoorCount(Random rand)
	{
		return 1 + rand.nextInt(5);
	}


	@Override
	protected TileProtectLevel getWallProtectionLevel()
	{
		return TileProtectLevel.WEAK;
	}


	@Override
	protected Coord getInnerSize(Random rand)
	{
		return Coord.make(3 + rand.nextInt(3), 3 + rand.nextInt(3));
	}
}
