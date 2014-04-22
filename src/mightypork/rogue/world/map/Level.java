package mightypork.rogue.world.map;


import java.io.IOException;

import mightypork.rogue.world.MapObserver;
import mightypork.rogue.world.Player;
import mightypork.rogue.world.WorldPos;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.Tiles;
import mightypork.util.ion.IonBinary;
import mightypork.util.ion.IonBundle;
import mightypork.util.ion.IonInput;
import mightypork.util.ion.IonOutput;
import mightypork.util.math.noise.NoiseGen;


/**
 * One level of the dungeon
 * 
 * @author MightyPork
 */
public class Level implements MapAccess, IonBinary {
	
	public static final int ION_MARK = 53;
	
	private int width, height;
	
	/** Array of tiles [y][x] */
	private Tile[][] tiles;
	
	/** Level seed (used for generation and tile variation) */
	public long seed;
	
	private transient NoiseGen noiseGen;
	
	
	public Level()
	{
	}
	
	
	public Level(int width, int height)
	{
		this.width = width;
		this.height = height;
		buildArray();
	}
	
	
	private void buildArray()
	{
		this.tiles = new Tile[height][width];
	}
	
	
	public void fill(short id)
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
	public void load(IonInput in) throws IOException
	{
		// metadata
		final IonBundle ib = in.readBundle();
		seed = ib.get("seed", 0L);
		width = ib.get("w", 0);
		height = ib.get("h", 0);
		
		// init array of size
		buildArray();
		
		// load tiles
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// no mark
				final Tile t = new Tile();
				t.load(in);
				tiles[y][x] = t;
			}
		}
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		// metadata
		final IonBundle ib = new IonBundle();
		ib.put("seed", seed);
		ib.put("w", width);
		ib.put("h", height);
		out.writeBundle(ib);
		
		// tiles (writing this way to save space)
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// no mark to save space
				tiles[y][x].save(out);
			}
		}
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
	
	public void updateLogic(MapObserver observer, double delta)
	{
		updateForObserver(observer, delta, true, false);
	}
	
	
	public void updateVisual(Player player, double delta)
	{
		updateForObserver(player, delta, false, true);
	}
	
	
	private void updateForObserver(MapObserver observer, double delta, boolean logic, boolean visual)
	{
		final int viewRange = observer.getViewRange();
		final WorldPos position = observer.getPosition();
		
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
				if (logic) getTile(x, y).updateLogic(delta);
				if (visual) getTile(x, y).updateVisual(delta);
			}
		}
	}
	
	
	@Override
	public NoiseGen getNoiseGen()
	{
		if (noiseGen == null) {
			noiseGen = new NoiseGen(0.2, 0, 0.5, 1, seed);
		}
		
		return noiseGen;
	}
	
}
