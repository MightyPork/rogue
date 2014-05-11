package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.gamecore.util.math.algo.Coord;
import mightypork.rogue.world.gen.MapTheme;
import mightypork.rogue.world.gen.ScratchMap;
import mightypork.rogue.world.gen.WorldGenError;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.Items;


public class HeartPieceRoom extends SecretRoom {
	
	@Override
	protected int getDoorCount(Random rand)
	{
		return 1;
	}
	
	
	@Override
	protected void buildExtras(ScratchMap map, MapTheme theme, Random rand, Coord min, Coord max)
	{
		final Item heart = Items.HEART_PIECE.createItem();
		
		if (!map.putItem(heart, min.add(2, 2))) {
			if (!map.putItemInArea(heart, min, max, 100)) {
				if (!map.putItemInMap(heart, 100)) {
					throw new WorldGenError("Could not place heart piece.");
				}
				
			}
		}
	}
	
	
	@Override
	protected Coord getInnerSize(Random rand)
	{
		return Coord.make(3, 3);
	}
}
