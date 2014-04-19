package mightypork.rogue.world.item;


/**
 * Item registry
 * 
 * @author MightyPork
 */
public final class Items {
	
	private static final ItemModel[] items = new ItemModel[256];
	
	
	static void register(int id, ItemModel model)
	{
		if (id < 0 || id >= items.length) if (items[id] != null) throw new IllegalArgumentException("Item ID " + id + " already in use.");
		
		items[id] = model;
	}
	
	
	public static ItemModel get(int id)
	{
		final ItemModel m = items[id];
		if (m == null) throw new IllegalArgumentException("No item with ID " + id + ".");
		return m;
	}
}
