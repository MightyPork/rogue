package mightypork.rogue.world.map;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mightypork.rogue.world.MapObserver;
import mightypork.rogue.world.WorldPos;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.Tiles;
import mightypork.util.files.ion.Ion;
import mightypork.util.files.ion.IonConstructor;
import mightypork.util.files.ion.Ionizable;
import mightypork.util.logging.Log;


/**
 * One level of the dungeon
 * 
 * @author MightyPork
 */
public class LevelMap implements MapAccess, Ionizable {
	
	public static final int ION_MARK = 702;
	
	private int width, height;
	
	/** Array of tiles [y][x] */
	private Tile[][] tiles;
	
	/** Level seed (used for generation and tile variation) */
	public long seed;
	
	
	@IonConstructor
	public LevelMap()
	{
	}
	
	
	public LevelMap(int width, int height)
	{
		this.width = width;
		this.height = height;
		buildArray();
	}
	
	
	private void buildArray()
	{
		this.tiles = new Tile[height][width];
		
		fill(Tiles.NULL_EMPTY);
	}
	
	
	public void fill(int id)
	{
		fill(Tiles.get(id));
	}
	
	
	public void fill(TileModel model)
	{
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[y][x] = model.createTile();
			}
		}
	}
	
	
	@Override
	public final Tile getTile(int x, int y)
	{
		if (x < 0 || x >= width || y < 0 || y >= height) return Tiles.NULL_SOLID.createTile(); // out of range
			
		return tiles[y][x];
	}
	
	
	public final void setTile(TileModel model, int x, int y)
	{
		setTile(model.createTile(), x, y);
	}
	
	
	public final void setTile(int tileId, int x, int y)
	{
		setTile(new Tile(tileId), x, y);
	}
	
	
	public final void setTile(Tile tile, int x, int y)
	{
		if (x < 0 || x > width || y < 0 || y >= height) return; // out of range
			
		tiles[y][x] = tile;
	}
	
	
	@Override
	public final int getWidth()
	{
		return width;
	}
	
	
	@Override
	public final int getHeight()
	{
		return height;
	}
	
	
	public void setSeed(long seed)
	{
		this.seed = seed;
	}
	
	
	@Override
	public long getSeed()
	{
		return seed;
	}
	
	
	@Override
	public void load(InputStream in) throws IOException
	{
		width = Ion.readInt(in);
		height = Ion.readInt(in);
		
		buildArray();
		
		while (Ion.hasNextEntry(in)) {
			
			final int x = Ion.readInt(in);
			final int y = Ion.readInt(in);
			
			final Tile tile = (Tile) Ion.readObject(in);
			
			setTile(tile, x, y);
		}
	}
	
	
	@Override
	public void save(OutputStream out) throws IOException
	{
		Ion.writeInt(out, width);
		Ion.writeInt(out, height);
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				final Tile t = getTile(x, y);
				// skip null tiles
				if (!t.getModel().isNullTile()) {
					Ion.writeMark(out, Ion.ENTRY);
					Ion.writeInt(out, x);
					Ion.writeInt(out, y);
					Ion.writeObject(out, t);
				}
			}
		}
		
		// end of sequence
		Ion.writeMark(out, Ion.END);
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
	
	public void update(MapObserver observer, double delta)
	{
		int viewRange = observer.getViewRange();
		WorldPos position = observer.getPosition();
		
		int x1 = position.x - viewRange;
		int y1 = position.y - viewRange;
		
		int x2 = x1 + viewRange * 2;
		int y2 = y1 + viewRange * 2;
		
		x1 = Math.min(Math.max(0, x1), width);
		y1 = Math.min(Math.max(0, y1), height);
		x2 = Math.min(x2, width);
		y2 = Math.max(y2, height);
		
		for (int y = y1; y <= y2; y++) {
			for (int x = x1; x <= x2; x++) {
				getTile(x, y).update(delta);
			}
		}
	}
	
}
