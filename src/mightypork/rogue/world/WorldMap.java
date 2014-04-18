package mightypork.rogue.world;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileHolder;
import mightypork.util.files.ion.Ion;
import mightypork.util.files.ion.Ionizable;


public class WorldMap implements TileHolder, Ionizable {
	
	private int width, height;
	
	/** Array of tiles [y][x] */
	private Tile[][] tiles;
	
	
	public WorldMap(int width, int height) {
		this.width = width;
		this.height = height;
		buildArray();
	}
	
	
	private void buildArray()
	{
		this.tiles = new Tile[height][width];
	}
	
	
	@Override
	public Tile getTile(int x, int y)
	{
		return tiles[y][x];
	}
	
	
	public void setTile(Tile tile, int x, int y)
	{
		tiles[y][x] = tile;
	}
	
	
	@Override
	public int getWidth()
	{
		return width;
	}
	
	
	@Override
	public int getHeight()
	{
		return height;
	}
	
	
	@Override
	public void loadFrom(InputStream in) throws IOException
	{
		width = Ion.readInt(in);
		height = Ion.readInt(in);
		
		buildArray();
		
		short mark;
		
		mark = Ion.readMark(in);
		if(mark == Ion.START);
	}
	
	
	@Override
	public void saveTo(OutputStream out) throws IOException
	{
	}
	
	
	@Override
	public short getIonMark()
	{
		return 0;
	}
	
}
