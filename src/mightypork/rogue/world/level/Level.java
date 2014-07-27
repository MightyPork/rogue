package mightypork.rogue.world.level;


import java.io.IOException;
import java.util.*;

import mightypork.rogue.world.World;
import mightypork.rogue.world.entity.Entities;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityType;
import mightypork.rogue.world.entity.impl.EntityPlayer;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.Tiles;
import mightypork.utils.eventbus.EventBus;
import mightypork.utils.eventbus.clients.DelegatingClient;
import mightypork.utils.eventbus.clients.ToggleableClient;
import mightypork.utils.interfaces.Updateable;
import mightypork.utils.ion.IonBinary;
import mightypork.utils.ion.IonDataBundle;
import mightypork.utils.ion.IonInput;
import mightypork.utils.ion.IonOutput;
import mightypork.utils.logging.Log;
import mightypork.utils.math.Calc;
import mightypork.utils.math.algo.Coord;
import mightypork.utils.math.algo.Move;
import mightypork.utils.math.algo.Moves;
import mightypork.utils.math.algo.floodfill.FloodFill;
import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.math.noise.NoiseGen;


/**
 * One level of the dungeon
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class Level implements Updateable, DelegatingClient, ToggleableClient, IonBinary {
	
	private final FloodFill exploreFiller = new FloodFill() {
		
		@Override
		public List<Move> getSpreadSides()
		{
			return Moves.ALL_SIDES;
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
	
	public static final int ION_MARK = 53;
	private static final Comparator<Entity> ENTITY_RENDER_CMP = new EntityRenderComparator();
	
	private Coord size = Coord.zero();
	private World world;
	
	private Coord enterPoint = Coord.zero();
	private Coord exitPoint = Coord.zero();
	
	/** Array of tiles [y][x] */
	private Tile[][] tiles;
	
	private final Map<Integer, Entity> entityMap = new HashMap<>();
	private final List<Entity> entityList = new LinkedList<>();
	
	private int playerCount = 0;
	
	/** Level seed (used for generation and tile variation) */
	public long seed;
	
	private transient NoiseGen noiseGen;
	private double timeSinceLastEntitySort;
	
	
	public Level() {
	}
	
	
	public Level(int width, int height) {
		size.setTo(width, height);
		buildArray();
	}
	
	
	private void buildArray()
	{
		this.tiles = new Tile[size.y][size.x];
	}
	
	
	/**
	 * Fill whole map with tile type
	 * 
	 * @param model tile model
	 */
	public void fill(TileModel model)
	{
		for (final Coord c = Coord.zero(); c.x < size.x; c.x++) {
			for (c.y = 0; c.y < size.y; c.y++) {
				setTile(c, model.createTile());
			}
		}
	}
	
	
	/**
	 * Ge tile at X,Y
	 * 
	 * @param pos
	 * @return tile
	 */
	public final Tile getTile(Coord pos)
	{
		if (!pos.isInRange(0, 0, size.x - 1, size.y - 1)) return Tiles.NULL.createTile(); // out of range
			
		return tiles[pos.y][pos.x];
	}
	
	
	/**
	 * Set tile at pos
	 * 
	 * @param pos tile pos
	 * @param tile the tile instance to set
	 */
	public final void setTile(Coord pos, Tile tile)
	{
		if (!pos.isInRange(0, 0, size.x - 1, size.y - 1)) {
			Log.w("Invalid tile coord to set: " + pos + ", map size: " + size);
			return; // out of range
		}
		
		tiles[pos.y][pos.x] = tile;
		
		// assign level (tile logic may need it)
		tile.setLevel(this);
	}
	
	
	/**
	 * @return map width in tiles
	 */
	public final int getWidth()
	{
		return size.x;
	}
	
	
	/**
	 * @return map height in tiles
	 */
	public final int getHeight()
	{
		return size.y;
	}
	
	
	/**
	 * Set level seed (used for visuals; the seed used for generation)
	 * 
	 * @param seed seed
	 */
	public void setSeed(long seed)
	{
		this.seed = seed;
	}
	
	
	/**
	 * @return map seed
	 */
	public long getSeed()
	{
		return seed;
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		// -- metadata --
		final IonDataBundle ib = in.readBundle();
		seed = ib.get("seed", 0L);
		size = ib.get("size");
		enterPoint = ib.get("enter_point", enterPoint);
		exitPoint = ib.get("exit_point", exitPoint);
		
		// -- binary data --
		
		// load tiles
		buildArray();
		
		for (final Coord c = Coord.zero(); c.x < size.x; c.x++) {
			for (c.y = 0; c.y < size.y; c.y++) {
				setTile(c, Tiles.loadTile(in));
			}
		}
		
		// load entities
		Entities.loadEntities(in, entityList);
		
		// prepare entities
		for (final Entity ent : entityList) {
			ent.setLevel(this);
			occupyTile(ent.getCoord());
			entityMap.put(ent.getEntityId(), ent);
			if (ent instanceof EntityPlayer) {
				playerCount++;
			}
		}
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		// -- metadata --
		final IonDataBundle ib = new IonDataBundle();
		ib.put("seed", seed);
		ib.put("size", size);
		ib.put("enter_point", enterPoint);
		ib.put("exit_point", exitPoint);
		out.writeBundle(ib);
		
		// -- binary data --
		
		// tiles
		for (final Coord c = Coord.zero(); c.x < size.x; c.x++) {
			for (c.y = 0; c.y < size.y; c.y++) {
				Tiles.saveTile(out, getTile(c));
			}
		}
		
		Entities.saveEntities(out, entityList);
	}
	
	
	@Override
	public void update(double delta)
	{
		timeSinceLastEntitySort += delta;
		
		if (timeSinceLastEntitySort > 0.2) {
			Collections.sort(entityList, ENTITY_RENDER_CMP);
			timeSinceLastEntitySort = 0;
		}
		
		// just update them all
		for (final Coord c = Coord.zero(); c.x < size.x; c.x++) {
			for (c.y = 0; c.y < size.y; c.y++) {
				getTile(c).updateTile(delta);
			}
		}
		
		final List<Entity> toRemove = new ArrayList<>();
		
		for (final Entity e : entityList) {
			if (e.isDead() && e.canRemoveCorpse()) toRemove.add(e);
		}
		
		for (final Entity e : toRemove) {
			removeEntity(e);
			e.onCorpseRemoved();
		}
	}
	
	
	/**
	 * @return level-specific noise generator
	 */
	public NoiseGen getNoiseGen()
	{
		if (noiseGen == null) {
			noiseGen = new NoiseGen(0.2, 0, 0.5, 1, seed);
		}
		
		return noiseGen;
	}
	
	
	/**
	 * Get entity by ID
	 * 
	 * @param eid entity ID
	 * @return the entity, or null
	 */
	public Entity getEntity(int eid)
	{
		return entityMap.get(eid);
	}
	
	
	/**
	 * Try to add entity at given pos, then near the pos.
	 * 
	 * @param entity the entity
	 * @param pos pos
	 * @return true if added
	 */
	public boolean addEntityNear(Entity entity, Coord pos)
	{
		if (addEntity(entity, pos)) return true;
		
		// closer
		for (int i = 0; i < 20; i++) {
			final Coord c = pos.add(Calc.randInt(-1, 1), Calc.randInt(-1, 1));
			if (addEntity(entity, c)) return true;
		}
		
		// further
		for (int i = 0; i < 20; i++) {
			final Coord c = pos.add(Calc.randInt(-2, 2), Calc.randInt(-2, 2));
			if (addEntity(entity, c)) return true;
		}
		
		return false;
		
	}
	
	
	/**
	 * Try to add entity at given pos
	 * 
	 * @param entity the entity
	 * @param pos pos
	 * @return true if added (false if void, wall etc)
	 */
	public boolean addEntity(Entity entity, Coord pos)
	{
		final Tile t = getTile(pos);
		if (!t.isWalkable() || t.isOccupied()) return false;
		
		// set level to init EID
		entity.setLevel(this);
		
		if (entityMap.containsKey(entity.getEntityId())) {
			Log.w("Entity already in level.");
			return false;
		}
		
		entityMap.put(entity.getEntityId(), entity);
		entityList.add(entity);
		if (entity instanceof EntityPlayer) playerCount++;
		
		// join to level & world
		occupyTile(entity.getCoord());
		
		entity.setCoord(pos);
		
		return true;
	}
	
	
	/**
	 * Remove an entity from the level, if present
	 * 
	 * @param entity entity
	 */
	public void removeEntity(Entity entity)
	{
		removeEntity(entity.getEntityId());
	}
	
	
	/**
	 * Remove an entity from the level, if present
	 * 
	 * @param eid entity id
	 */
	public void removeEntity(int eid)
	{
		final Entity removed = entityMap.remove(eid);
		if (removed == null) throw new NullPointerException("No such entity in level: " + eid);
		if (removed instanceof EntityPlayer) playerCount--;
		entityList.remove(removed);
		
		// upon kill, entities free tile themselves.
		if (!removed.isDead()) {
			freeTile(removed.getCoord());
		}
	}
	
	
	/**
	 * Check tile walkability
	 * 
	 * @param pos tile coord
	 * @return true if the tile is walkable by entity
	 */
	public boolean isWalkable(Coord pos)
	{
		final Tile t = getTile(pos);
		
		return t.isWalkable() && !t.isOccupied();
	}
	
	
	/**
	 * Mark tile as occupied (entity entered)
	 * 
	 * @param pos tile pos
	 */
	public void occupyTile(Coord pos)
	{
		getTile(pos).setOccupied(true);
	}
	
	
	/**
	 * Mark tile as free (entity left)
	 * 
	 * @param pos tile pos
	 */
	public void freeTile(Coord pos)
	{
		getTile(pos).setOccupied(false);
	}
	
	
	/**
	 * Check entity on tile
	 * 
	 * @param pos tile coord
	 * @return true if some entity is standing there
	 */
	public boolean isOccupied(Coord pos)
	{
		return getTile(pos).isOccupied();
	}
	
	
	/**
	 * Set level entry point
	 * 
	 * @param pos pos where the player appears upon descending to this level
	 */
	public void setEnterPoint(Coord pos)
	{
		this.enterPoint.setTo(pos);
	}
	
	
	/**
	 * Get location where the player appears upon descending to this level
	 * 
	 * @return pos
	 */
	public Coord getEnterPoint()
	{
		return enterPoint;
	}
	
	
	/**
	 * Set level exit point
	 * 
	 * @param pos pos where the player appears upon ascending to this level
	 */
	public void setExitPoint(Coord pos)
	{
		this.exitPoint.setTo(pos);
	}
	
	
	/**
	 * Get location where the player appears upon ascending to this level
	 * 
	 * @return pos
	 */
	public Coord getExitPoint()
	{
		return exitPoint;
	}
	
	
	/**
	 * Get the level's world
	 * 
	 * @return world
	 */
	public World getWorld()
	{
		return world;
	}
	
	
	/**
	 * Assign a world
	 * 
	 * @param world new world
	 */
	public void setWorld(World world)
	{
		this.world = world;
	}
	
	
	/**
	 * Mark tile and surrounding area as explored
	 * 
	 * @param center center the explored tile
	 */
	public void explore(Coord center)
	{
		final Collection<Coord> filled = new HashSet<>();
		
		exploreFiller.fill(center, filled);
		
		for (final Coord c : filled) {
			getTile(c).setExplored();
		}
	}
	
	
	/**
	 * Get entity of type closest to coord
	 * 
	 * @param pos the attack origin
	 * @param type wanted entity type
	 * @param radius search radius; -1 for unlimited.
	 * @return
	 */
	public Entity getClosestEntity(Vect pos, EntityType type, double radius)
	{
		Entity closest = null;
		double minDist = Double.MAX_VALUE;
		
		for (final Entity e : entityList) {
			if (e.isDead()) continue;
			
			if (e.getType() == type) {
				final double dist = e.pos.getVisualPos().dist(pos).value();
				
				if (dist <= radius && dist < minDist) {
					minDist = dist;
					closest = e;
				}
			}
		}
		
		return closest;
	}
	
	
	/**
	 * Free a tile. If entity is present, remove it.
	 * 
	 * @param pos the tile pos
	 */
	public void forceFreeTile(Coord pos)
	{
		if (getTile(pos).isOccupied()) {
			final Set<Entity> toButcher = new HashSet<>();
			for (final Entity e : entityList) {
				if (e.getCoord().equals(pos)) {
					toButcher.add(e);
					break;
				}
			}
			
			for (final Entity e : toButcher) {
				removeEntity(e);
				freeTile(pos);
			}
		}
		
		if (!getTile(pos).isWalkable()) {
			// this should never happen.
			setTile(pos, Tiles.BRICK_FLOOR.createTile());
		}
		
	}
	
	
	/**
	 * Check if entity is in the level
	 * 
	 * @param entity entity
	 * @return is present
	 */
	public boolean isEntityPresent(Entity entity)
	{
		return entityList.contains(entity);
	}
	
	
	/**
	 * Check if entity is in the level
	 * 
	 * @param eid entity ID
	 * @return true if present
	 */
	public boolean isEntityPresent(int eid)
	{
		return entityMap.containsKey(eid);
	}
	
	
	/**
	 * Get entity collection (for rendering)
	 * 
	 * @return entities
	 */
	public Collection<Entity> getEntities()
	{
		return entityList;
	}
	
	
	@Override
	public boolean isListening()
	{
		return playerCount > 0; // listen only when player is in this level
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection getChildClients()
	{
		return entityList;
	}
	
	
	@Override
	public boolean doesDelegate()
	{
		return isListening();
	}
	
	
	public boolean dropNear(Coord coord, Item itm)
	{
		if (getTile(coord).dropItem(itm)) return true;
		
		for (int i = 0; i < 6; i++) {
			final Coord c = coord.add(Calc.randInt(-1, 1), Calc.randInt(-1, 1));
			if (getTile(c).dropItem(itm)) return true;
		}
		
		return false;
	}
	
	private static class EntityRenderComparator implements Comparator<Entity> {
		
		@Override
		public int compare(Entity o1, Entity o2)
		{
			if (o1.isDead() && !o2.isDead()) {
				return -1;
			}
			
			if (!o1.isDead() && o2.isDead()) {
				return 1;
			}
			
			int c = Double.compare(o1.pos.getVisualPos().y(), o1.pos.getVisualPos().y());
			if (c == 0) c = Double.compare(o1.pos.getVisualPos().x(), o1.pos.getVisualPos().x());
			
			return c;
		}
		
	}
}
