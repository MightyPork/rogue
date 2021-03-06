package mightypork.rogue.world.tile;


import java.io.IOException;

import mightypork.rogue.world.tile.impl.NullTile;
import mightypork.rogue.world.tile.impl.brick.TileBrickChest;
import mightypork.rogue.world.tile.impl.brick.TileBrickDoor;
import mightypork.rogue.world.tile.impl.brick.TileBrickEntrance;
import mightypork.rogue.world.tile.impl.brick.TileBrickExit;
import mightypork.rogue.world.tile.impl.brick.TileBrickFloor;
import mightypork.rogue.world.tile.impl.brick.TileBrickPassage;
import mightypork.rogue.world.tile.impl.brick.TileBrickSecretDoor;
import mightypork.rogue.world.tile.impl.brick.TileBrickWall;
import mightypork.utils.ion.IonInput;
import mightypork.utils.ion.IonOutput;


/**
 * Tile registry
 *
 * @author Ondřej Hruška (MightyPork)
 */
public final class Tiles {
	
	private static final TileModel[] tiles = new TileModel[256];
	
	public static final TileModel NULL = new TileModel(0, NullTile.class);
	
	public static final TileModel BRICK_FLOOR = new TileModel(10, TileBrickFloor.class);
	public static final TileModel BRICK_WALL = new TileModel(11, TileBrickWall.class);
	public static final TileModel BRICK_DOOR = new TileModel(12, TileBrickDoor.class);
	public static final TileModel BRICK_PASSAGE = new TileModel(13, TileBrickPassage.class);
	public static final TileModel BRICK_HIDDEN_DOOR = new TileModel(14, TileBrickSecretDoor.class);
	public static final TileModel BRICK_ENTRANCE = new TileModel(15, TileBrickEntrance.class);
	public static final TileModel BRICK_EXIT = new TileModel(16, TileBrickExit.class);
	public static final TileModel BRICK_CHEST = new TileModel(17, TileBrickChest.class);
	
	
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
	
	
	public static Tile loadTile(IonInput in) throws IOException
	{
		final int id = in.readIntByte();
		
		final TileModel model = get(id);
		return model.loadTile(in);
	}
	
	
	public static void saveTile(IonOutput out, Tile tile) throws IOException
	{
		final TileModel model = tile.getModel();
		
		out.writeIntByte(model.id);
		model.saveTile(out, tile);
	}
	
	
	public static Tile create(int tileId)
	{
		return get(tileId).createTile();
	}
}
