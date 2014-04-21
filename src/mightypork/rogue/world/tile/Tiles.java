package mightypork.rogue.world.tile;


import mightypork.rogue.world.tile.models.Floor;
import mightypork.rogue.world.tile.models.NullFloor;
import mightypork.rogue.world.tile.models.NullWall;
import mightypork.rogue.world.tile.models.Wall;


/**
 * Tile registry
 * 
 * @author MightyPork
 */
public final class Tiles {
	
	private static final TileModel[] tiles = new TileModel[256];
	
	public static final TileModel NULL_SOLID = new NullWall(0);
	public static final TileModel NULL_EMPTY = new NullFloor(1);
	public static final TileModel BRICK_FLOOR_VINES = new Floor(2, "tile.floor.mossy_bricks");
	public static final TileModel BRICK_WALL_VINES = new Wall(3, "tile.wall.mossy_bricks");
	public static final TileModel BRICK_FLOOR_RECT = new Floor(4, "tile.floor.rect_bricks");
	public static final TileModel BRICK_WALL_SMALL = new Wall(5, "tile.wall.small_bricks");
	public static final TileModel SANDSTONE_FLOOR = new Floor(6, "tile.floor.sandstone");
	public static final TileModel SANDSTONE_WALL = new Wall(7, "tile.wall.sandstone");
	public static final TileModel BRCOBBLE_FLOOR = new Floor(8, "tile.floor.brown_cobble");
	public static final TileModel BRCOBBLE_WALL = new Wall(9, "tile.wall.brown_cobble");
	public static final TileModel CRYSTAL_FLOOR = new Floor(10, "tile.floor.crystal");
	public static final TileModel CRYSTAL_WALL = new Wall(11, "tile.wall.crystal");
	
	
	static void register(int id, TileModel model)
	{
		if (id < 0 || id >= tiles.length) {
			throw new IllegalArgumentException("Tile ID " + id + " is out of range.");
		}
		
		if (tiles[id] != null) {
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