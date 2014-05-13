package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.gamecore.util.math.Calc;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.rogue.world.gen.MapTheme;
import mightypork.rogue.world.gen.ScratchMap;
import mightypork.rogue.world.item.Items;


public class TreasureRoom extends SecretRoom {
	
	@Override
	protected void buildExtras(ScratchMap map, MapTheme theme, Random rand, Coord min, Coord max)
	{
		for (int i = 0; i < Calc.randInt(rand, 0, 1); i++) {
			map.putItemInArea(Items.SANDWICH.createItem(), min, max, 50);
		}
		
		for (int i = 0; i < Calc.randInt(rand, 0, 2); i++) {
			map.putItemInArea(Items.BONE.createItemDamaged(20), min, max, 50);
		}
		
		for (int i = 0; i < Calc.randInt(rand, 0, 1); i++) {
			map.putItemInArea(Items.ROCK.createItemDamaged(30), min, max, 50);
		}
		
		for (int i = 0; i < Calc.randInt(rand, 0, 3); i++) {
			map.putItemInArea(Items.MEAT.createItem(), min, max, 50);
		}
		
		for (int i = 0; i < Calc.randInt(rand, 0, 2); i++) {
			map.putItemInArea(Items.CHEESE.createItem(), min, max, 50);
		}
	}
}
