package mightypork.rogue.world;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileGrid;
import mightypork.rogue.world.tile.Tiles;
import mightypork.util.files.ion.Ion;
import mightypork.util.files.ion.IonConstructor;
import mightypork.util.files.ion.Ionizable;


public class WorldMap implements TileGrid, Ionizable {
	
	public static final int ION_MARK = 702;
	
	private int width, height;
	
	/** Array of tiles [y][x] */
	private Tile[][] tiles;
	
	
	@IonConstructor
	public WorldMap()
	{
	}
	
	
	public WorldMap(int width, int height)
	{
		this.width = width;
		this.height = height;
		buildArray();
	}
	
	
	private void buildArray()
	{
		this.tiles = new Tile[height][width];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[y][x] = Tiles.NONE.create();
			}
		}
	}
	
	
	@Override
	public final Tile getTile(int x, int y)
	{
		return tiles[y][x];
	}
	
	
	public final void setTile(int tileId, int x, int y)
	{
		setTile(new Tile(tileId), x, y);
	}
	
	
	public final void setTile(Tile tile, int x, int y)
	{
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
	
}
