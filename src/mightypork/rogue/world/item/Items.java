package mightypork.rogue.world.item;


import java.util.HashMap;
import java.util.Map;


public final class Items {
	
	private static final Map<Integer, ItemModel> registered = new HashMap<>();
	
	
	static void register(int id, ItemModel model)
	{
		if (registered.containsKey(id)) throw new IllegalArgumentException("Item ID " + id + " already in use.");
		registered.put(id, model);
	}
	
	
	public static ItemModel get(int id)
	{
		final ItemModel m = registered.get(id);
		if (m == null) throw new IllegalArgumentException("No item with ID " + id + ".");
		return m;
	}
}
