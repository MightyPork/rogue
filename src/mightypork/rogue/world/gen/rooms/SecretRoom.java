package mightypork.rogue.world.gen.rooms;


import java.util.Random;

import mightypork.gamecore.util.math.Calc;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.rogue.world.gen.MapTheme;
import mightypork.rogue.world.gen.ScratchMap;
import mightypork.rogue.world.gen.TileProtectLevel;
import mightypork.rogue.world.item.Items;
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
	protected void buildExtras(ScratchMap map, MapTheme theme, Random rand, Coord min, Coord max)
	{
		for (int i = 0; i < Calc.randInt(rand, 0, 1); i++) {
			map.dropInArea(Items.SANDWICH.createItem(), min, max, 50);
		}
		
		for (int i = 0; i < Calc.randInt(rand, 0, 2); i++) {
			map.dropInArea(Items.BONE.createItemDamaged(20), min, max, 50);
		}
		
		for (int i = 0; i < Calc.randInt(rand, 0, 1); i++) {
			map.dropInArea(Items.ROCK.createItemDamaged(30), min, max, 50);
		}
		
		for (int i = 0; i < Calc.randInt(rand, 0, 2); i++) {
			map.dropInArea(Items.MEAT.createItem(), min, max, 50);
		}
		
		for (int i = 0; i < Calc.randInt(rand, 0, 2); i++) {
			map.dropInArea(Items.CHEESE.createItem(), min, max, 50);
		}
	}
	
	
	@Override
	protected Coord getInnerSize(Random rand)
	{
		return Coord.make(3 + rand.nextInt(2), 3 + rand.nextInt(2));
	}
}
