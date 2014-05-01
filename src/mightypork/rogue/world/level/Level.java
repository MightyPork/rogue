package mightypork.rogue.world.level;


import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mightypork.gamecore.logging.Log;
import mightypork.gamecore.util.ion.IonBundle;
import mightypork.gamecore.util.ion.IonInput;
import mightypork.gamecore.util.ion.IonObjBinary;
import mightypork.gamecore.util.ion.IonOutput;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Sides;
import mightypork.gamecore.util.math.algo.Step;
import mightypork.gamecore.util.math.algo.floodfill.FillContext;
import mightypork.gamecore.util.math.algo.floodfill.FloodFill;
import mightypork.gamecore.util.math.noise.NoiseGen;
import mightypork.rogue.world.World;
import mightypork.rogue.world.entity.Entities;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityType;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.Tiles;


/**
 * One level of the dungeon
 * 
 * @author MightyPork
 */
public class Level implements MapAccess, IonObjBinary {
	
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
		setTile(pos, Tiles.create(tileId));
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
		// -- metadata --
		final IonBundle ib = in.readBundle();
		seed = ib.get("seed", 0L);
		ib.loadBundled("size", size);
		ib.loadBundled("enter_point", enterPoint);
		
		// -- binary data --
		
		// load tiles
		buildArray();
		
		for (final Coord c = Coord.zero(); c.x < size.x; c.x++) {
			for (c.y = 0; c.y < size.y; c.y++) {
				setTile(c, Tiles.loadTile(in));
			}
		}
		
		// load entities
		Entities.loadEntities(in, entitySet);
		
		// prepare entities
		for (final Entity ent : entitySet) {
			ent.setLevel(this);
			occupyTile(ent.getCoord());
			entityMap.put(ent.getEntityId(), ent);
		}
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		// -- metadata --
		final IonBundle ib = new IonBundle();
		ib.put("seed", seed);
		ib.putBundled("size", size);
		ib.putBundled("enter_point", enterPoint);
		out.writeBundle(ib);
		
		// -- binary data --
		
		// tiles
		for (final Coord c = Coord.zero(); c.x < size.x; c.x++) {
			for (c.y = 0; c.y < size.y; c.y++) {
				Tiles.saveTile(out, getTile(c));
			}
		}
		
		Entities.saveEntities(out, entitySet);
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
	
	
	/**
	 * Try to add entity at given pos
	 * @param entity the entity
	 * @param pos pos
	 * @return true if added (false if void, wall etc)
	 */
	public boolean addEntity(Entity entity, Coord pos)
	{
		Tile t = getTile(pos);
		if (!t.isWalkable()) return false;
		
		addEntity(entity);
		
		entity.setCoord(pos);
		
		return true;
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
	
	
	public boolean isWalkable(Coord pos)
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
	
	
	public void cleanCorpses()
	{
		for (final Entity e : entitySet) {
			if (e.isDead() && e.canRemoveCorpse()) {
				e.onCorpseRemoved();
				removeEntity(e);
			}
		}
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
	
	
	public void explore(Coord center)
	{
		final Collection<Coord> filled = new HashSet<>();
		
		FloodFill.fill(center, exploreFc, filled);
		
		for (final Coord c : filled) {
			getTile(c).setExplored();
		}
	}
	
	private final FillContext exploreFc = new FillContext() {
		
		@Override
		public Step[] getSpreadSides()
		{
			return Sides.ALL_SIDES;
		}
		
		
		@Override
		public double getMaxDistance()
		{
			return 5.4;
		}
		
		
		@Override
		public boolean canSpreadFrom(Coord pos)
		{
			final Tile t = getTile(pos);
			return t.isWalkable() && !t.isDoor();
		}
		
		
		@Override
		public boolean canEnter(Coord pos)
		{
			final Tile t = getTile(pos);
			return !t.isNull();
		}
		
		
		@Override
		public boolean forceSpreadStart()
		{
			return true;
		}
	};
	
	
	public Entity getClosestEntity(Entity self, EntityType type, double radius)
	{
		Entity closest = null;
		double minDist = Double.MAX_VALUE;
		
		for (final Entity e : entitySet) {
			if (e == self) continue;
			if (e.isDead()) continue;
			
			if (e.getType() == type) {
				final double dist = e.getCoord().dist(self.getCoord());
				
				if (dist <= radius && dist < minDist) {
					minDist = dist;
					closest = e;
				}
			}
		}
		
		return closest;
	}
	
	
	public boolean isEntityPresent(Entity entity)
	{
		return entitySet.contains(entity);
	}
}
