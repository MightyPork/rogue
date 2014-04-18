package mightypork.rogue.world.tile;


import java.util.HashMap;
import java.util.Map;


public final class Tiles {
	
	private static final Map<Integer, TileModel> registered = new HashMap<>();
	
	
	public static void register(int id, TileModel model)
	{
		if (registered.containsKey(id)) throw new IllegalArgumentException("Tile ID " + id + " already in use.");
		registered.put(id, model);
	}
	
	
	public static TileModel get(int id)
	{
		final TileModel m = registered.get(id);
		if (m == null) throw new IllegalArgumentException("No tile with ID " + id + ".");
		return m;
	}
}
