package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.rogue.world.gen.MapTheme;
import mightypork.rogue.world.gen.ScratchMap;
import mightypork.rogue.world.gen.TileProtectLevel;
import mightypork.rogue.world.tile.TileModel;
import mightypork.utils.math.algo.Coord;


public class ExitRoom extends AbstractRectRoom {

	@Override
	protected Coord getInnerSize(Random rand)
	{
		return Coord.make(3 + rand.nextInt(2), 3 + rand.nextInt(2));
	}


	@Override
	protected TileProtectLevel getWallProtectionLevel()
	{
		return TileProtectLevel.NONE;
	}


	@Override
	protected TileModel getDoorType(MapTheme theme, Random rand)
	{
		return null;
	}


	@Override
	protected void buildExtras(ScratchMap map, MapTheme theme, Random rand, Coord min, Coord max)
	{
		final Coord c = Coord.make((max.x + min.x) / 2, (max.y + min.y) / 2);
		map.set(c, theme.exit());
		map.protect(c, c, TileProtectLevel.STRONG);
		map.setExit(c.add(-1, 0));
	}


	@Override
	protected int getDoorCount(Random rand)
	{
		return 0;
	}

}
