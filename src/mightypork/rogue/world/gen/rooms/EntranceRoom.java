package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.gamecore.util.math.algo.Coord;
import mightypork.rogue.world.gen.MapTheme;
import mightypork.rogue.world.gen.ScratchMap;
import mightypork.rogue.world.gen.TileProtectLevel;
import mightypork.rogue.world.tile.TileModel;


public class EntranceRoom extends AbstractRectRoom {
	
	@Override
	protected Coord getInnerSize(Random rand)
	{
		return Coord.make(3 + rand.nextInt(3), 3 + rand.nextInt(3));
	}
	
	
	@Override
	protected TileProtectLevel getWallProtectionLevel()
	{
		return TileProtectLevel.WEAK;
	}
	
	
	@Override
	protected TileModel getDoorType(MapTheme theme, Random rand)
	{
		switch (rand.nextInt(5)) {
			case 0:
			case 1:
				return theme.passage();
			case 2:
				return theme.secretDoor();
			default:
				return theme.door();
		}
	}
	
	
	@Override
	protected void buildExtras(ScratchMap map, MapTheme theme, Random rand, Coord min, Coord max)
	{
		final Coord c = Coord.make((max.x + min.x) / 2, (max.y + min.y) / 2);
		map.set(c, theme.entrance());
		map.protect(c, c, TileProtectLevel.STRONG);
		map.setEntrance(c.add(1, 0));
	}
	
	
	@Override
	protected int getDoorCount(Random rand)
	{
		return 1 + rand.nextInt(4);
	}
	
}
