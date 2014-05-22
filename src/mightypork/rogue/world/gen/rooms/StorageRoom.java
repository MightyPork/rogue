package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.gamecore.util.math.Calc;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.rogue.world.gen.MapTheme;
import mightypork.rogue.world.gen.ScratchMap;
import mightypork.rogue.world.item.Items;


public class StorageRoom extends SecretRoom {
	
	@Override
	protected void buildExtras(ScratchMap map, MapTheme theme, Random rand, Coord min, Coord max)
	{
		int maxStuff = Calc.randInt(rand, 3, 5);
		
		// at least one meat or cheese.
		boolean oneMeat = rand.nextBoolean();
		
		for (int i = 0; i < Calc.randInt(rand, oneMeat ? 1 : 0, 3); i++) {
			map.addItemInArea(Items.MEAT.createItem(), min, max, 50);
			if (--maxStuff == 0) return;
		}
		
		for (int i = 0; i < Calc.randInt(rand, oneMeat ? 0 : 1, 2); i++) {
			map.addItemInArea(Items.CHEESE.createItem(), min, max, 50);
			if (--maxStuff == 0) return;
		}
		
		for (int i = 0; i < Calc.randInt(rand, 0, 1); i++) {
			map.addItemInArea(Items.ROCK.createItemDamaged(30), min, max, 50);
			if (--maxStuff == 0) return;
		}
		
		for (int i = 0; i < Calc.randInt(rand, 0, 1); i++) {
			map.addItemInArea(Items.SANDWICH.createItem(), min, max, 50);
			if (--maxStuff == 0) return;
		}
		
		for (int i = 0; i < Calc.randInt(rand, 0, 2); i++) {
			map.addItemInArea(Items.TWIG.createItemDamaged(40), min, max, 50);
			if (--maxStuff == 0) return;
		}
		
		for (int i = 0; i < Calc.randInt(rand, 0, 2); i++) {
			map.addItemInArea(Items.BONE.createItemDamaged(40), min, max, 50);
			if (--maxStuff == 0) return;
		}
		
		
	}
}
