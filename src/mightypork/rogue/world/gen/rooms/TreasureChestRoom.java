package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.gamecore.util.math.algo.Coord;
import mightypork.rogue.world.gen.MapTheme;
import mightypork.rogue.world.gen.ScratchMap;
import mightypork.rogue.world.gen.TileProtectLevel;
import mightypork.rogue.world.item.Item;


public class TreasureChestRoom extends ItemShrineRoom {
	
	public TreasureChestRoom(Item item)
	{
		super(item);
	}
	
	
	@Override
	protected void buildExtras(ScratchMap map, MapTheme theme, Random rand, Coord min, Coord max)
	{
		// set tile
		final Coord center = min.add(2, 2);
		
		map.set(center, theme.chest());
		map.protect(center, TileProtectLevel.STRONG);
		
		// drop item
		super.buildExtras(map, theme, rand, min, max);
	}
}
