package mightypork.rogue.world.tile;


import java.io.IOException;

import mightypork.rogue.world.tile.renderers.BasicTileRenderer;
import mightypork.rogue.world.tile.renderers.DoorTileRenderer;
import mightypork.rogue.world.tile.renderers.NullTileRenderer;
import mightypork.rogue.world.tile.tiles.DoorTile;
import mightypork.rogue.world.tile.tiles.FloorTile;
import mightypork.rogue.world.tile.tiles.NullTile;
import mightypork.rogue.world.tile.tiles.WallTile;
import mightypork.util.files.ion.IonInput;
import mightypork.util.files.ion.IonOutput;


/**
 * Tile registry
 * 
 * @author MightyPork
 */
public final class Tiles {
	
	private static final TileModel[] tiles = new TileModel[256];
	
	public static final TileModel NULL = new TileModel(0, NullTile.class, new NullTileRenderer());
	
	public static final TileModel FLOOR_DARK = new TileModel(10, FloorTile.class, new BasicTileRenderer("tile.floor.dark"));
	public static final TileModel WALL_BRICK = new TileModel(11, WallTile.class, new BasicTileRenderer("tile.wall.brick"));	
	public static final TileModel DOOR = new TileModel(12, DoorTile.class, new DoorTileRenderer("tile.door.closed", "tile.door.open"));

	
	public static void register(int id, TileModel model)
	{
		if (id < 0 || id >= tiles.length) { throw new IllegalArgumentException("Tile ID " + id + " is out of range."); }
		
		if (tiles[id] != null) { throw new IllegalArgumentException("Tile ID " + id + " already in use."); }
		
		tiles[id] = model;
	}
	
	
	public static TileModel get(int id)
	{
		final TileModel m = tiles[id];
		
		if (m == null) { throw new IllegalArgumentException("No tile with ID " + id + "."); }
		
		return m;
	}
	
	
	public static Tile loadTile(IonInput in) throws IOException
	{
		int id = in.readIntByte();
		
		TileModel model = get(id);
		return model.loadTile(in);
	}
	
	
	public static void saveTile(IonOutput out, Tile tile) throws IOException
	{
		TileModel model = tile.getModel();
		
		out.writeIntByte(model.id);		
		model.saveTile(out, tile);
	}


	public static Tile create(int tileId)
	{
		return get(tileId).createTile();
	}
}
