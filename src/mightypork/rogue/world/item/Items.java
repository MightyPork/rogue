package mightypork.rogue.world.item;


import java.io.IOException;
import java.util.Collection;

import mightypork.gamecore.util.ion.IonInput;
import mightypork.gamecore.util.ion.IonOutput;
import mightypork.rogue.world.item.items.food.ItemCheese;
import mightypork.rogue.world.item.items.food.ItemMeat;
import mightypork.rogue.world.item.items.food.ItemSandwich;
import mightypork.rogue.world.item.items.weapons.ItemBone;
import mightypork.rogue.world.item.items.weapons.ItemClub;
import mightypork.rogue.world.item.items.weapons.ItemHammer;
import mightypork.rogue.world.item.items.weapons.ItemStone;
import mightypork.rogue.world.item.items.weapons.ItemSword;


/**
 * Item registry
 * 
 * @author MightyPork
 */
public final class Items {
	
	private static final ItemModel[] items = new ItemModel[256];
	
	public static final ItemModel MEAT = new ItemModel(1, ItemMeat.class);
	public static final ItemModel CHEESE = new ItemModel(2, ItemCheese.class);
	public static final ItemModel BONE = new ItemModel(3, ItemBone.class);
	public static final ItemModel SANDWICH = new ItemModel(4, ItemSandwich.class);
	public static final ItemModel CLUB = new ItemModel(5, ItemClub.class);
	public static final ItemModel HAMMER = new ItemModel(6, ItemHammer.class);
	public static final ItemModel SWORD = new ItemModel(7, ItemSword.class);
	public static final ItemModel ROCK = new ItemModel(8, ItemStone.class);
	
	
	public static void register(int id, ItemModel model)
	{
		if (id < 0 || id >= items.length) {
			throw new IllegalArgumentException("Item ID " + id + " is out of range.");
		}
		
		if (items[id] != null) {
			throw new IllegalArgumentException("Item ID " + id + " already in use.");
		}
		
		items[id] = model;
	}
	
	
	public static ItemModel get(int id)
	{
		final ItemModel m = items[id];
		
		if (m == null) {
			throw new IllegalArgumentException("No item with ID " + id + ".");
		}
		
		return m;
	}
	
	
	public static Item loadItem(IonInput in) throws IOException
	{
		final int id = in.readIntByte();
		
		final ItemModel model = get(id);
		return model.loadItem(in);
	}
	
	
	public static void saveItem(IonOutput out, Item item) throws IOException
	{
		final ItemModel model = item.getModel();
		
		out.writeIntByte(model.id);
		model.saveItem(out, item);
	}
	
	
	public static Item create(int tileId)
	{
		return get(tileId).createItem();
	}
	
	
	public static void loadItems(IonInput in, Collection<Item> items) throws IOException
	{
		items.clear();
		while (in.hasNextEntry()) {
			items.add(loadItem(in));
		}
	}
	
	
	public static void saveItems(IonOutput out, Collection<Item> items) throws IOException
	{
		for (final Item entity : items) {
			out.startEntry();
			saveItem(out, entity);
		}
		
		out.endSequence();
	}
	
}
