package mightypork.rogue.world.item;


import java.io.IOException;
import java.util.Collection;

import mightypork.rogue.world.item.impl.active.ItemHeartPiece;
import mightypork.rogue.world.item.impl.food.ItemCheese;
import mightypork.rogue.world.item.impl.food.ItemMeat;
import mightypork.rogue.world.item.impl.food.ItemSandwich;
import mightypork.rogue.world.item.impl.weapons.ItemAxe;
import mightypork.rogue.world.item.impl.weapons.ItemBone;
import mightypork.rogue.world.item.impl.weapons.ItemClub;
import mightypork.rogue.world.item.impl.weapons.ItemKnife;
import mightypork.rogue.world.item.impl.weapons.ItemRock;
import mightypork.rogue.world.item.impl.weapons.ItemSword;
import mightypork.rogue.world.item.impl.weapons.ItemTwig;
import mightypork.utils.ion.IonDataBundle;
import mightypork.utils.ion.IonInput;
import mightypork.utils.ion.IonOutput;


/**
 * Item registry
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public final class Items {
	
	private static final ItemModel[] items = new ItemModel[256];
	
	public static final ItemModel MEAT = new ItemModel(1, ItemMeat.class);
	public static final ItemModel CHEESE = new ItemModel(2, ItemCheese.class);
	public static final ItemModel BONE = new ItemModel(3, ItemBone.class);
	public static final ItemModel SANDWICH = new ItemModel(4, ItemSandwich.class);
	public static final ItemModel CLUB = new ItemModel(5, ItemClub.class);
	public static final ItemModel AXE = new ItemModel(6, ItemAxe.class);
	public static final ItemModel SWORD = new ItemModel(7, ItemSword.class);
	public static final ItemModel ROCK = new ItemModel(8, ItemRock.class);
	public static final ItemModel HEART_PIECE = new ItemModel(9, ItemHeartPiece.class);
	public static final ItemModel TWIG = new ItemModel(10, ItemTwig.class);
	public static final ItemModel KNIFE = new ItemModel(11, ItemKnife.class);
	
	
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
		return model.loadItem(in.readBundle());
	}
	
	
	public static void saveItem(IonOutput out, Item item) throws IOException
	{
		final ItemModel model = item.getModel();
		
		out.writeIntByte(model.id);
		
		final IonDataBundle ib = new IonDataBundle();
		model.saveItem(ib, item);
		out.writeBundle(ib);
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
