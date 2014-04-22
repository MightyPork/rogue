package mightypork.rogue.world.map;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mightypork.rogue.world.PlayerEntity;
import mightypork.rogue.world.WorldAccess;
import mightypork.rogue.world.WorldEntity;
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
	
	private final List<WorldEntity> entities = new ArrayList<>();
	
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
		
		ib.loadSequence("entities", entities);
		
		// init array of size
		buildArray();
		
		// load tiles
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// no mark
				tiles[y][x] = new Tile();
				tiles[y][x].load(in);
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
		ib.putSequence("entities", entities);
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
	
	
	public void updateLogic(WorldAccess world, double delta)
	{
		if (!world.isServer()) {
			throw new RuntimeException("Not allowed for client.");
		}
		
		// just update them all
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				getTile(x, y).updateLogic(world, this, delta);
			}
		}
	}
	
	
	/**
	 * Update visuals for player (particle effects etc)
	 * 
	 * @param world
	 * @param player
	 * @param delta
	 */
	public void updateVisual(WorldAccess world, PlayerEntity player, double delta)
	{
		if (world.isServer()) {
			throw new RuntimeException("Not allowed for server.");
		}
		
		final int viewRange = player.getViewRange();
		final WorldPos eyepos = player.getViewPosition();
		
		int x1 = eyepos.x - viewRange;
		int y1 = eyepos.y - viewRange;
		
		int x2 = x1 + viewRange * 2;
		int y2 = y1 + viewRange * 2;
		
		x1 = Math.min(Math.max(0, x1), width);
		y1 = Math.min(Math.max(0, y1), height);
		x2 = Math.min(x2, width);
		y2 = Math.max(y2, height);
		
		for (int y = y1; y <= y2; y++) {
			for (int x = x1; x <= x2; x++) {
				getTile(x, y).updateVisual(world, this, delta);
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
