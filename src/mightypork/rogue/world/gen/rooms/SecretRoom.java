package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.gamecore.util.math.algo.Coord;
import mightypork.rogue.world.gen.MapTheme;
import mightypork.rogue.world.gen.TileProtectLevel;
import mightypork.rogue.world.tile.TileModel;


public class SecretRoom extends AbstractRectRoom {
	
	
	@Override
	protected TileModel getDoorType(MapTheme theme, Random rand)
	{
		return theme.secretDoor();
	}
	
	
	@Override
	protected int getDoorCount(Random rand)
	{
		return 1 + rand.nextInt(2);
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