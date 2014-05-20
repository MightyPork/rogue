package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.gamecore.util.math.algo.Coord;
import mightypork.rogue.world.gen.MapTheme;
import mightypork.rogue.world.gen.RoomBuilder;
import mightypork.rogue.world.gen.RoomEntry;
import mightypork.rogue.world.gen.ScratchMap;
import mightypork.rogue.world.gen.TileProtectLevel;
import mightypork.rogue.world.tile.TileModel;


public class DeadEndRoom extends AbstractRectRoom {

	@Override
	protected Coord getInnerSize(Random rand)
	{
		return Coord.make(1,1);
	}

	@Override
	protected TileProtectLevel getWallProtectionLevel()
	{
		return TileProtectLevel.STRONG;
	}

	@Override
	protected TileModel getDoorType(MapTheme theme, Random rand)
	{
		return theme.floor();
	}

	@Override
	protected int getDoorCount(Random rand)
	{
		return 1;
	}
}
