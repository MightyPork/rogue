package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.rogue.world.gen.MapTheme;
import mightypork.rogue.world.gen.ScratchMap;
import mightypork.rogue.world.gen.WorldGenError;
import mightypork.rogue.world.item.Item;
import mightypork.utils.math.Calc;
import mightypork.utils.math.algo.Coord;


public class ItemShrineRoom extends SecretRoom {

	private final Item item;


	public ItemShrineRoom(Item item)
	{
		this.item = item;
	}


	@Override
	protected int getDoorCount(Random rand)
	{
		return Calc.randInt(rand, 1, 4);
	}


	@Override
	protected void buildExtras(ScratchMap map, MapTheme theme, Random rand, Coord min, Coord max)
	{
		final Coord center = min.add(2, 2);

		if (!map.addItem(item, center)) {
			throw new WorldGenError("Could not place item in chest.");
		}
	}


	@Override
	protected Coord getInnerSize(Random rand)
	{
		return Coord.make(3, 3);
	}
}
