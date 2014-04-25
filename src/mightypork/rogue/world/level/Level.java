package mightypork.rogue.world.level;


import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mightypork.rogue.world.Coord;
import mightypork.rogue.world.World;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.Tiles;
import mightypork.util.files.ion.IonBinary;
import mightypork.util.files.ion.IonBundle;
import mightypork.util.files.ion.IonInput;
import mightypork.util.files.ion.IonOutput;
import mightypork.util.logging.Log;
import mightypork.util.math.Calc;
import mightypork.util.math.noise.NoiseGen;


/**
 * One level of the dungeon
 * 
 * @author MightyPork
 */
public class Level implements MapAccess, IonBinary {
	
	public static final int ION_MARK = 53;
	
	private final Coord size = Coord.zero();
	private World world;
	
	private final Coord enterPoint = Coord.zero();
	
	/** Array of tiles [y][x] */
	private Tile[][] tiles;
	
	private final Map<Integer, Entity> entityMap = new HashMap<>();
	private final Set<Entity> entitySet = new HashSet<>();
	
	/** Level seed (used for generation and tile variation) */
	public long seed;
	
	private transient NoiseGen noiseGen;
	
	
	public Level()
	{
	}
	
	
	public Level(int width, int height)
	{
		size.setTo(width, height);
		buildArray();
	}
	
	
	private void buildArray()
	{
		this.tiles = new Tile[size.y][size.x];
	}
	
	
	public void fill(short id)
	{
		fill(Tiles.get(id));
	}
	
	
	public void fill(TileModel model)
	{
		for (final Coord c = Coord.zero(); c.x < size.x; c.x++) {
			for (c.y = 0; c.y < size.y; c.y++) {
				setTile(c, model.createTile());
			}
		}
	}
	
	
	@Override
	public final Tile getTile(Coord pos)
	{
		if (!pos.isInRange(0, 0, size.x - 1, size.y - 1)) return Tiles.NULL.createTile(); // out of range
		
		return tiles[pos.y][pos.x];
	}
	
	
	public final void setTile(Coord pos, TileModel model)
	{
		setTile(pos, model.createTile());
	}
	
	
	public final void setTile(Coord pos, int tileId)
	{
		setTile(pos, new Tile(tileId));
	}
	
	
	public final void setTile(Coord pos, Tile tile)
	{
		if (!pos.isInRange(0, 0, size.x - 1, size.y - 1)) {
			Log.w("Invalid tile coord to set: " + pos + ", map size: " + size);
			return; // out of range
		}
		
		tiles[pos.y][pos.x] = tile;
	}
	
	
	@Override
	public final int getWidth()
	{
		return size.x;
	}
	
	
	@Override
	public final int getHeight()
	{
		return size.y;
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
		ib.loadBundled("size", size);
		ib.loadBundled("enter_point", enterPoint);
		
		ib.loadSequence("entities", entitySet);
		for (final Entity ent : entitySet) {
			ent.setLevel(this);
			entityMap.put(ent.getEntityId(), ent);
		}
		
		// init array of size
		buildArray();
		
		// load tiles
		for (final Coord c = Coord.zero(); c.x < size.x; c.x++) {
			for (c.y = 0; c.y < size.y; c.y++) {
				// no mark
				final Tile tile = new Tile();
				tile.load(in);
				setTile(c, tile);
			}
		}
		
		// mark tiles as occupied
		for (final Entity e : entitySet) {
			occupyTile(e.getPosition().getCoord());
		}
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		// metadata
		final IonBundle ib = new IonBundle();
		ib.put("seed", seed);
		ib.putBundled("size", size);
		ib.putBundled("enter_point", enterPoint);
		ib.putSequence("entities", entitySet);
		out.writeBundle(ib);
		
		// tiles (writing this way to save space)
		
		for (final Coord c = Coord.zero(); c.x < size.x; c.x++) {
			for (c.y = 0; c.y < size.y; c.y++) {
				// no mark to save space
				getTile(c).save(out);
			}
		}
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
	
	public void update(double delta)
	{
		// just update them all
		for (final Coord c = Coord.zero(); c.x < size.x; c.x++) {
			for (c.y = 0; c.y < size.y; c.y++) {
				getTile(c).update(this, delta);
			}
		}
		
		for (final Entity e : entitySet) {
			e.update(delta);
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
	
	
	public Entity getEntity(int eid)
	{
		return entityMap.get(eid);
	}
	
	
	public void addEntity(Entity entity)
	{
		if (entityMap.containsKey(entity.getEntityId())) {
			Log.w("Entity already in level.");
			return;
		}
		
		entityMap.put(entity.getEntityId(), entity);
		entitySet.add(entity);
		
		// join to level & world
		entity.setLevel(this);
	}
	
	
	public void removeEntity(Entity entity)
	{
		entityMap.remove(entity.getEntityId());
		entitySet.remove(entity);
	}
	
	
	public void removeEntity(int eid)
	{
		final Entity removed = entityMap.remove(eid);
		entitySet.remove(removed);
	}
	
	
	public boolean canWalkInto(Coord pos)
	{
		final Tile t = getTile(pos);
		
		return t.isWalkable() && !t.isOccupied();
	}
	
	
	/**
	 * Mark tile as occupied by an entity
	 */
	public void occupyTile(Coord pos)
	{
		getTile(pos).setOccupied(true);
	}
	
	
	/**
	 * Mark tile as free (no longet occupied)
	 */
	public void freeTile(Coord pos)
	{
		getTile(pos).setOccupied(false);
	}
	
	
	public Collection<Entity> getEntities()
	{
		return entitySet;
	}
	
	
	public void setEnterPoint(Coord pos)
	{
		this.enterPoint.setTo(pos);
	}
	
	
	public Coord getEnterPoint()
	{
		return enterPoint;
	}
	
	
	public World getWorld()
	{
		return world;
	}
	
	
	public void setWorld(World world)
	{
		this.world = world;
	}
	
	
	public void markExplored(Coord coord, double radius)
	{
		final int cr = (int) Math.ceil(radius);
		
		final Coord c = Coord.zero();
		for (c.y = coord.y - cr; c.y <= coord.y + cr; c.y++) {
			for (c.x = coord.x - cr; c.x <= coord.x + cr; c.x++) {
				if (Calc.dist(coord.x, coord.y, c.x, c.y) > radius) continue;
				final Tile t = getTile(c);
				if (!t.isNull()) {
					t.data.explored = true;
				}
			}
		}
		
	}
}
