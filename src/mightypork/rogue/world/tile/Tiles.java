package mightypork.rogue.world.tile;


import mightypork.rogue.world.tile.models.Floor;
import mightypork.rogue.world.tile.models.NullTile;


/**
 * Tile registry
 * 
 * @author MightyPork
 */
public final class Tiles {
	
	private static final TileModel[] tiles = new TileModel[256];
	
	public static final TileModel NONE = new NullTile(0);
	public static final TileModel FLOOR_MOSSY = new Floor(1, "tile.mossy_bricks.floor");
	public static final TileModel WALL_MOSSY = new Floor(2, "tile.mossy_bricks.wall");
	
	
	static void register(int id, TileModel model)
	{
		if (id < 0 || id >= tiles.length) if (tiles[id] != null) {
			throw new IllegalArgumentException("Tile ID " + id + " already in use.");
		}
		
		tiles[id] = model;
	}
	
	
	public static TileModel get(int id)
	{
		final TileModel m = tiles[id];
		
		if (m == null) {
			throw new IllegalArgumentException("No tile with ID " + id + ".");
		}
		
		return m;
	}
}
