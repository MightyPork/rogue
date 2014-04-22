package mightypork.rogue.world.level;


import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mightypork.gamecore.render.Render;
import mightypork.rogue.Res;
import mightypork.rogue.world.World;
import mightypork.rogue.world.WorldPos;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.level.render.EntityRenderContext;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.Tiles;
import mightypork.rogue.world.tile.models.TileModel;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.RectConst;
import mightypork.util.constraints.rect.proxy.RectBound;
import mightypork.util.constraints.vect.VectConst;
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
	
	private static final boolean USE_BATCH_RENDERING = true;
	
	private int width, height;
	
	/** Array of tiles [y][x] */
	private Tile[][] tiles;
	
	private final Map<Integer, Entity> entity_map = new HashMap<>();
	private final Set<Entity> entity_set = new HashSet<>();
	
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
		
		ib.loadSequence("entities", entity_set);
		for (final Entity ent : entity_set) {
			entity_map.put(ent.getEID(), ent);
		}
		
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
		
		// mark tiles as occupied
		for (final Entity e : entity_set) {
			occupyTile(e.getPosition().x, e.getPosition().y);
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
		ib.putSequence("entities", entity_set);
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
	
	
	public void update(World w, double delta)
	{
		// just update them all
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				getTile(x, y).update(this, delta);
			}
		}
		
		for (final Entity e : entity_set) {
			e.update(w, this, delta);
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
	
	
	/**
	 * Draw on screen
	 * 
	 * @param playerInfo layer
	 * @param viewport rendering area on screen
	 * @param xTiles Desired nr of tiles horizontally
	 * @param yTiles Desired nr of tiles vertically
	 * @param minSize minimum tile size
	 */
	public void render(WorldPos pos, RectBound viewport, final int xTiles, final int yTiles, final int minSize)
	{
		final Rect r = viewport.getRect();
		final double vpH = r.height().value();
		final double vpW = r.width().value();
		
		// adjust tile size to fit desired amount of tiles
		
		final double allowedSizeW = vpW / xTiles;
		final double allowedSizeH = vpH / yTiles;
		final int tileSize = (int) Math.round(Math.max(Math.min(allowedSizeH, allowedSizeW), minSize));
		
		//tileSize -= tileSize % 8;
		
		final VectConst vpCenter = r.center().sub(tileSize * 0.5, tileSize).freeze(); // 0.5 to center, 1 to move up (down is teh navbar)
		
		final double playerX = pos.getVisualX();
		final double playerY = pos.getVisualY();
		
		// total map area
		//@formatter:off
		final RectConst mapRect = vpCenter.startRect().grow(
				playerX*tileSize,
				(getWidth() - playerX) * tileSize,
				playerY*tileSize,
				(getHeight() - playerY) * tileSize
		).freeze();
		//@formatter:on
		
		// tiles to render
		final int x1 = (int) Math.floor(playerX - (vpW / tileSize / 2));
		
		final int y1 = (int) Math.floor(playerY - (vpH / tileSize / 2));
		final int x2 = (int) Math.ceil(playerX + (vpW / tileSize / 2));
		final int y2 = (int) Math.ceil(playerY + (vpH / tileSize / 2));
		
		final TileRenderContext trc = new TileRenderContext(this, mapRect); //-tileSize*0.5
		
		// batch rendering of the tiles
		if (USE_BATCH_RENDERING) {
			Render.enterBatchTexturedQuadMode(Res.getTexture("tiles16"));
		}
		
		for (trc.y = y1; trc.y <= y2; trc.y++) {
			for (trc.x = x1; trc.x <= x2; trc.x++) {
				trc.renderTile();
			}
		}
		
		if (USE_BATCH_RENDERING) {
			Render.leaveBatchTexturedQuadMode();
		}
		
		// render extras
		for (trc.y = y1; trc.y <= y2; trc.y++) {
			for (trc.x = x1; trc.x <= x2; trc.x++) {
				trc.renderItems();
			}
		}
		
		// render entities
		final EntityRenderContext erc = new EntityRenderContext(this, mapRect);
		for (final Entity e : entity_set) {
			e.render(erc);
		}
	}
	
	
	public Entity getEntity(int eid)
	{
		return entity_map.get(eid);
	}
	
	
	public void addEntity(Entity entity)
	{
		entity_map.put(entity.getEID(), entity);
		entity_set.add(entity);
	}
	
	
	public void removeEntity(Entity entity)
	{
		entity_map.remove(entity.getEID());
		entity_set.remove(entity);
	}
	
	
	public void removeEntity(int eid)
	{
		final Entity removed = entity_map.remove(eid);
		entity_set.remove(removed);
	}
	
	
	public boolean canWalkInto(int x, int y)
	{
		final Tile t = getTile(x, y);
		
		return t.isWalkable() && !t.isOccupied();
	}
	
	
	/**
	 * Mark tile as occupied by an entity
	 */
	public void occupyTile(int x, int y)
	{
		getTile(x, y).setOccupied(true);
	}
	
	
	/**
	 * Mark tile as free (no longet occupied)
	 */
	public void freeTile(int x, int y)
	{
		getTile(x, y).setOccupied(false);
	}
}
