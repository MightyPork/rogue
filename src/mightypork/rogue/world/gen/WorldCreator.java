package mightypork.rogue.world.gen;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mightypork.gamecore.logging.Log;
import mightypork.gamecore.util.math.Calc;
import mightypork.gamecore.util.math.Range;
import mightypork.rogue.world.World;
import mightypork.rogue.world.entity.Entities;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.gen.LevelBuilder.BuildOrder;
import mightypork.rogue.world.gen.themes.ThemeBrick;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.ItemModel;
import mightypork.rogue.world.item.Items;


public class WorldCreator {
	
	public static Random rand = new Random();
	
	
	public static World createWorld(long seed)
	{
		synchronized (rand) {
			
			Log.f2("Generating a new world...");
			
			rand.setSeed(seed);
			final MapTheme theme = new ThemeBrick();
			
			final World w = new World();
			w.setSeed(seed);
			
			final LevelBuilder levelBuilders[] = new LevelBuilder[7];
			
			// build the level rooms
			for (int floor = 1; floor <= 7; floor++) {
				
				Log.f3("Placing rooms for level: " + floor);
				
				final LevelBuilder lb = prepareFloor(rand.nextLong(), floor, theme, floor == 7);
				
				levelBuilders[floor - 1] = lb;
			}
			
			Log.f3("Placing items...");
			final List<ItemModel> weaponsBasic = new ArrayList<>();
			weaponsBasic.add(Items.ROCK);
			weaponsBasic.add(Items.BONE);
			weaponsBasic.add(Items.TWIG);
			
			
			final List<ItemModel> weaponsMedium = new ArrayList<>();
			weaponsMedium.add(Items.CLUB);
			
			
			final List<ItemModel> weaponsGood = new ArrayList<>();
			weaponsGood.add(Items.AXE);
			weaponsGood.add(Items.SWORD);
			weaponsGood.add(Items.KNIFE);
			
			
			for (int i = 0; i < Calc.randInt(rand, 8, 13); i++) {
				final Item item = Calc.pick(rand, weaponsBasic).createItemDamaged(50);
				final LevelBuilder lb = levelBuilders[-1 + Calc.randInt(1, 7)];
				lb.addItem(item, false);
			}
			
			
			for (int i = 0; i < Calc.randInt(rand, 1, 2); i++) {
				final Item item = Calc.pick(rand, weaponsMedium).createItemDamaged(60);
				final LevelBuilder lb = levelBuilders[-1 + Calc.randInt(1, 3)];
				lb.addItem(item, false);
			}
			
			
			for (int i = 0; i < Calc.randInt(rand, 2, 3); i++) {
				final Item item = Calc.pick(rand, weaponsGood).createItemDamaged(60);
				final LevelBuilder lb = levelBuilders[-1 + Calc.randInt(3, 7)];
				
				lb.addRoom(Rooms.treasure(item), BuildOrder.MIDDLE, true);
			}
			
			
			// place random foods
			final List<ItemModel> randomFood = new ArrayList<>();
			randomFood.add(Items.CHEESE);
			randomFood.add(Items.MEAT);
			
			for (int level = 1; level <= 7; level++) {
				final LevelBuilder lb = levelBuilders[level - 1];
				final Range amount = Range.make(1, level);
				
				for (int i = 0; i < amount.randInt(rand); i++) {
					lb.addItem(Calc.pick(rand, randomFood).createItem(), false);
				}
			}
			
			
			// place monsters		
			
			Log.f3("Placing monsters...");
			for (int level = 1; level <= 7; level++) {
				
				final LevelBuilder lb = levelBuilders[level - 1];
				
				final Range amount = Range.make(2 + level * 2, 5 + level * 3.5);
				
				for (int i = 0; i < amount.randInt(rand); i++) {
					Entity e;
					
					if (level > 2 && rand.nextInt(7 - level + 1) == 0) {
						e = Entities.RAT_BROWN.createEntity();
					} else {
						e = Entities.RAT_GRAY.createEntity();
					}
					
					lb.addEntity(e, false);
				}
			}
			
			
			// compile levels
			Log.f3("Building levels...");
			int i = 1;
			for (final LevelBuilder lb : levelBuilders) {
				Log.f3("Building level " + i);
				w.addLevel(lb.build(w));
				i++;
			}
			
			w.createPlayer();
			
			Log.f2("World generation finished.");
			
			return w;
		}
	}
	
	
	public static LevelBuilder prepareFloor(long seed, int floor, MapTheme theme, boolean lastLevel) throws WorldGenError
	{
		final LevelBuilder lb = new LevelBuilder(128, theme, seed);
		
		lb.addRoom(Rooms.ENTRANCE, BuildOrder.FIRST, true);
		
		lb.addRoom(Rooms.BASIC, Range.make(1 + floor, 1 + floor * 1.5), BuildOrder.MIDDLE, false);
		lb.addRoom(Rooms.DEAD_END, Range.make(0, 1 + floor), BuildOrder.MIDDLE, false);
		lb.addRoom(Rooms.STORAGE, Range.make(1, Math.ceil(floor / 3D)), BuildOrder.MIDDLE, false);
		
		if (lastLevel) lb.addRoom(Rooms.BOSS, BuildOrder.LAST, true);
		if (!lastLevel) lb.addRoom(Rooms.EXIT, BuildOrder.LAST, true);
		
		final RoomBuilder heartRoom = Rooms.shrine(Items.HEART_PIECE.createItem());
		lb.addRoom(heartRoom, BuildOrder.LAST, true);
		
		return lb;
	}
}
