package mightypork.rogue.world.tile;


import mightypork.rogue.world.tile.models.*;
import mightypork.rogue.world.tile.renderers.FloorRenderer;
import mightypork.rogue.world.tile.renderers.WallRenderer;


/**
 * Tile registry
 * 
 * @author MightyPork
 */
public final class Tiles {
	
	private static final TileModel[] tiles = new TileModel[256];
	
	public static final TileModel NULL_SOLID = new NullWall(0);
	public static final TileModel NULL_EMPTY = new NullFloor(1);
	public static final TileModel NULL_EMPTY_RESERVED = new NullFloor(2);
	
	public static final TileModel FLOOR_DARK = new Floor(10).setRenderer(new FloorRenderer("tile.floor.dark"));
	public static final TileModel WALL_BRICK = new Wall(11).setRenderer(new WallRenderer("tile.wall.brick"));
	
	public static final TileModel DOOR = new SimpleDoor(12);
	
	
//	public static final TileModel BRICK_FLOOR_VINES = new Floor(2).setTexture("tile.floor.mossy_bricks");
//	public static final TileModel BRICK_WALL_VINES = new Wall(3).setTexture("tile.wall.mossy_bricks");
//	
//	public static final TileModel BRICK_FLOOR_RECT = new Floor(4).setTexture("tile.floor.rect_bricks");
//	public static final TileModel BRICK_WALL_SMALL = new Wall(5).setTexture("tile.wall.small_bricks");
//	
//	public static final TileModel SANDSTONE_FLOOR = new Floor(6).setTexture("tile.floor.sandstone");
//	public static final TileModel SANDSTONE_WALL = new Wall(7).setTexture("tile.wall.sandstone");
//	
//	public static final TileModel BRCOBBLE_FLOOR = new Floor(8).setTexture("tile.floor.brown_cobble");
//	public static final TileModel BRCOBBLE_WALL = new Wall(9).setTexture("tile.wall.brown_cobble");
//	
//	public static final TileModel CRYSTAL_FLOOR = new Floor(10).setTexture("tile.floor.crystal");
//	public static final TileModel CRYSTAL_WALL = new Wall(11).setTexture("tile.wall.crystal");
	
	public static void register(int id, TileModel model)
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
