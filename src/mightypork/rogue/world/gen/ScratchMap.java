package mightypork.rogue.world.gen;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.Tiles;
import mightypork.utils.Str;
import mightypork.utils.logging.Log;
import mightypork.utils.math.Calc;
import mightypork.utils.math.algo.Coord;
import mightypork.utils.math.algo.Move;
import mightypork.utils.math.algo.Moves;
import mightypork.utils.math.algo.pathfinding.Heuristic;
import mightypork.utils.math.algo.pathfinding.PathFinder;


/**
 * Temporary tile map used for level generation.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class ScratchMap {
	
	private Tile[][] map;
	private final int width;
	private final int height;
	
	private final List<RoomEntry> rooms = new ArrayList<>();
	
	/** Coords to connect with corridors */
	private final List<Coord> nodes = new ArrayList<>();
	
	private final List<Coord> occupied = new ArrayList<>();
	private final List<Entity> entities = new ArrayList<>();
	
	private final PathFinder pathf = new PathFinder() {
		
		@Override
		public boolean isAccessible(Coord pos)
		{
			if (!isIn(pos)) return false;
			final Tile t = getTile(pos);
			if (t.isStairs()) return false;
			return t.isPotentiallyWalkable() || (t.genData.protection != TileProtectLevel.STRONG);
		}
		
		
		@Override
		public int getCost(Coord last, Coord pos)
		{
			final Tile t = getTile(pos);
			
			switch (t.getType()) {
				case NULL:
					return 60;
					
				case DOOR:
				case PASSAGE:
					return 10;
					
				case STAIRS:
				case FLOOR:
					return 20;
					
				case WALL:
					if (t.genData.protection != TileProtectLevel.NONE) return 2000;
					
					return 100;
					
				default:
					throw new WorldGenError("Unknown tile type: " + t.getType());
			}
		}
		
		
		@Override
		public int getMinCost()
		{
			return 10;
		}
		
		
		@Override
		public Heuristic getHeuristic()
		{
			return PathFinder.CORNER_HEURISTIC;
		}
		
		
		@Override
		public List<Move> getWalkSides()
		{
			return Moves.CARDINAL_SIDES;
		}
		
	};
	
	{
		// needed for when the path starts / ends at stairs.
		pathf.setIgnoreEnd(true);
		pathf.setIgnoreStart(true);
	}
	
	Coord genMin;
	Coord genMax;
	
	private final MapTheme theme;
	private final Random rand;
	private final Coord enterPoint = new Coord();
	private final Coord exitPoint = new Coord();
	
	private static final boolean FIX_GLITCHES = true;
	
	
	public ScratchMap(int max_size, MapTheme theme, Random rand)
	{
		map = new Tile[max_size][max_size];
		
		genMin = Coord.make((max_size / 2) - 1, (max_size / 2) - 1);
		genMax = genMin.add(1, 1);
		
		width = max_size;
		height = max_size;
		this.rand = rand;
		this.theme = theme;
		
		fill(Coord.make(0, 0), Coord.make(width - 1, height - 1), Tiles.NULL);
	}
	
	
	public void addRoom(RoomBuilder rb, boolean critical) throws WorldGenError
	{
		try {
			if (rooms.size() > 0) minimizeBounds();
			
			final Coord roomPos = Coord.make(0, 0);
			
			int failed = 0;
			int failed_total = 0;
			
			while (true) {
				
				final int sizeX = genMax.x - genMin.x;
				final int sizeY = genMax.y - genMin.y;
				
				roomPos.x = genMin.x + rand.nextInt(sizeX + 1);
				roomPos.y = genMin.y + rand.nextInt(sizeY + 1);
				
				switch (rand.nextInt(4)) {
					case 0:
						roomPos.x += (failed_total / 35);
						break;
					case 1:
						roomPos.x -= (failed_total / 35);
						break;
					case 2:
						roomPos.y += (failed_total / 35);
						break;
					case 3:
						roomPos.y -= (failed_total / 35);
				}
				
				final RoomEntry rd = rb.buildRoom(this, theme, rand, roomPos);
				if (rd != null) {
					
					rooms.add(rd);
					
					genMin.x = Math.min(genMin.x, rd.min.x);
					genMin.y = Math.min(genMin.y, rd.min.y);
					
					genMax.x = Math.max(genMax.x, rd.max.x);
					genMax.y = Math.max(genMax.y, rd.max.y);
					clampBounds();
					
					nodes.add(roomPos);
					
					return;
					
				} else {
					failed++;
					failed_total++;
					
					if (failed_total > 1000) {
						throw new WorldGenError("Failed to add a room.");
					}
					
					if (failed > 300) {
						Log.w("Faild to build room.");
						if (critical) {
							
							// expand gen bounds
							genMin.x -= 5;
							genMin.y -= 5;
							genMax.x += 5;
							genMax.y += 5;
							
							clampBounds();
							
							failed = 0;
							Log.f3("Trying again.");
							continue;
							
						} else {
							throw new WorldGenError("Failed to add a room.");
						}
					}
				}
			}
		} catch (final WorldGenError e) {
			if (!critical) {
				Log.w("Could not place a room.", e);
				return;
			} else {
				// rethrow
				throw e;
			}
		}
	}
	
	
	/**
	 * Clamp bounds to available area
	 */
	private void clampBounds()
	{
		genMin.x = Calc.clamp(genMin.x, 0, width - 1);
		genMin.y = Calc.clamp(genMin.y, 0, height - 1);
		genMax.x = Calc.clamp(genMax.x, 0, width - 1);
		genMax.y = Calc.clamp(genMax.y, 0, height - 1);
	}
	
	
	/**
	 * Minimize gen bounds based on defined room bounds
	 */
	private void minimizeBounds()
	{
		final Coord low = Coord.make(width, height);
		final Coord high = Coord.make(0, 0);
		
		for (final RoomEntry rd : rooms) {
			low.x = Math.min(low.x, rd.min.x);
			low.y = Math.min(low.y, rd.min.y);
			
			high.x = Math.max(high.x, rd.max.x);
			high.y = Math.max(high.y, rd.max.y);
		}
		
		genMin.setTo(low);
		genMax.setTo(high);
	}
	
	
	public boolean isIn(Coord pos)
	{
		return pos.x >= 0 && pos.x < width && pos.y >= 0 && pos.y < height;
	}
	
	
	public Tile getTile(Coord pos)
	{
		if (!isIn(pos)) {
			throw new WorldGenError("Tile not in map: " + pos);
		}
		
		return map[pos.y][pos.x];
	}
	
	
	public boolean set(Coord pos, TileModel tm)
	{
		return set(pos, tm.createTile());
	}
	
	
	public boolean set(Coord pos, Tile tile)
	{
		if (!isIn(pos)) {
			throw new WorldGenError("Tile not in map: " + pos);
		}
		
		map[pos.y][pos.x] = tile;
		return true;
	}
	
	
	public boolean isClear(Coord min, Coord max)
	{
		if (!isIn(min) || !isIn(max)) return false;
		for (final RoomEntry r : rooms) {
			if (r.intersectsWith(min, max)) return false;
		}
		
		return true;
	}
	
	
	public void protect(Coord pos, TileProtectLevel prot)
	{
		protect(pos, pos, prot);
	}
	
	
	public void protect(Coord min, Coord max, TileProtectLevel prot)
	{
		if (!isIn(min) || !isIn(max)) throw new WorldGenError("Tile(s) not in map: " + min + " , " + max);
		
		final Coord c = Coord.make(0, 0);
		for (c.x = min.x; c.x <= max.x; c.x++)
			for (c.y = min.y; c.y <= max.y; c.y++)
				getTile(c).genData.protection = prot;
	}
	
	
	public void fill(Coord min, Coord max, TileModel tm)
	{
		if (!isIn(min) || !isIn(max)) throw new WorldGenError("Tile(s) not in map: " + min + " , " + max);
		
		final Coord c = Coord.make(0, 0);
		for (c.x = min.x; c.x <= max.x; c.x++)
			for (c.y = min.y; c.y <= max.y; c.y++)
				set(c, tm.createTile());
	}
	
	
	public void border(Coord min, Coord max, TileModel tm)
	{
		if (!isIn(min) || !isIn(max)) throw new WorldGenError("Tile(s) not in map: " + min + " , " + max);
		
		final Coord c = Coord.make(0, 0);
		
		// top
		for (c.x = min.x, c.y = min.y; c.x <= max.x; c.x++)
			set(c, tm.createTile());
		
		//bottom
		for (c.x = min.x, c.y = max.y; c.x <= max.x; c.x++)
			set(c, tm.createTile());
		
		//left
		for (c.x = min.x, c.y = min.y + 1; c.y < max.y; c.y++)
			set(c, tm.createTile());
		
		//right
		for (c.x = max.x, c.y = min.y + 1; c.y < max.y; c.y++)
			set(c, tm.createTile());
		
	}
	
	
	public void buildCorridors()
	{
//		Log.f3("Building corridors.");
		
		Coord start = nodes.get(0);
		final Set<Coord> starts = new HashSet<>();
		
		for (int i = 0; i < 2 + rooms.size() / 5; i++) {
			if (!starts.contains(start)) {
				for (int j = 0; j < nodes.size(); j++) {
					buildCorridor(start, nodes.get(j));
				}
			}
			starts.add(start);
			start = Calc.pick(rand, nodes);
		}
	}
	
	
	private void buildCorridor(Coord node1, Coord node2)
	{
//		Log.f3("Building corridor " + node1 + " -> " + node2);
		final List<Coord> steps = pathf.findPath(node1, node2);
		
		if (steps == null) {
			Log.w("Could not build corridor " + node1 + "->" + node2);
			return;
		}
		
		for (final Coord c : steps) {
			buildCorridorPiece(c);
		}
	}
	
	
	private void buildCorridorPiece(Coord pos)
	{
		final Coord c = Coord.make(0, 0);
		int i, j;
		for (i = -1, c.x = pos.x - 1; c.x <= pos.x + 1; c.x++, i++) {
			for (j = -1, c.y = pos.y - 1; c.y <= pos.y + 1; c.y++, j++) {
				if (!isIn(c)) continue;
				
				genMin.x = Math.min(genMin.x, c.x);
				genMin.y = Math.min(genMin.y, c.y);
				
				genMax.x = Math.max(genMax.x, c.x);
				genMax.y = Math.max(genMax.y, c.y);
				clampBounds();
				
				final Tile current = getTile(c);
				if (!current.isNull() && (current.isPotentiallyWalkable() || current.isStairs())) continue; // floor already, let it be

				if (i == 0 && j == 0) {
					set(c, theme.floor());
				} else {
					if (current.isWall()) continue;
					set(c, theme.wall());
				}
			}
		}
	}
	
	
	/**
	 * @return dimensions of the area taken by non-null tiles
	 */
	public Coord getNeededSize()
	{
		return Coord.make(genMax.x - genMin.x + 1, genMax.y - genMin.y + 1);
	}
	
	
	public byte findWalls(Coord pos)
	{
		byte walls = 0;
		for (int i = 0; i < 8; i++) {
			final Coord cc = pos.add(Moves.getSide(i));
			if (!isIn(cc)) continue;
			
			if (getTile(cc).isWall()) {
				walls |= Moves.getBit(i);
			}
		}
		return walls;
	}
	
	
	public byte findFloors(Coord pos)
	{
		byte floors = 0;
		for (int i = 0; i <= 7; i++) {
			final Coord cc = pos.add(Moves.getSide(i));
			if (!isIn(cc)) continue;
			
			if (getTile(cc).isFloor()) {
				floors |= Moves.getBit(i);
			}
		}
		return floors;
	}
	
	
	public byte findDoors(Coord pos)
	{
		byte doors = 0;
		for (int i = 0; i <= 7; i++) {
			final Coord cc = pos.add(Moves.getSide(i));
			if (!isIn(cc)) continue;
			
			if (getTile(cc).isDoor()) {
				doors |= Moves.getBit(i);
			}
		}
		return doors;
	}
	
	
	public byte findNils(Coord pos)
	{
		byte nils = 0;
		for (int i = 0; i <= 7; i++) {
			final Coord cc = pos.add(Moves.getSide(i));
			
			if (!isIn(cc) || getTile(cc).isNull()) {
				nils |= Moves.getBit(i);
			}
		}
		return nils;
	}
	
	
	/**
	 * Fix generator glitches and reduce size to the actual used size
	 */
	public void fixGlitches()
	{
		final Tile[][] out = new Tile[height][width];
		
		// bounds will be adjusted by the actual tiles in the map
		genMin.x = width;
		genMin.y = height;
		genMax.x = 0;
		genMax.y = 0;
		
		final Coord c = Coord.make(0, 0);
		for (c.x = 0; c.x < width; c.x++) {
			for (c.y = 0; c.y < height; c.y++) {
				
				final Tile t = getTile(c);
				final boolean isNull = t.isNull();
				
				final boolean isDoor = !isNull && t.isDoor();
				final boolean isFloor = !isNull && t.isFloor();
				final boolean isWall = !isNull && t.isWall();
				
				// bitmasks
				final byte walls = findWalls(c);
				final byte nils = findNils(c);
				final byte floors = findFloors(c);
				
				boolean toWall = false;
				boolean toFloor = false;
				boolean toNull = false;
				
				do {
					if (isWall && floors == 0) {
						toNull = true;
						break;
					}
					
					if (isFloor && (nils & Moves.BITS_CARDINAL) != 0) {
						toWall = true; // floor with adjacent cardinal null
						break;
					}
					
					if (isNull && (floors & Moves.BITS_DIAGONAL) != 0) {
						toWall = true; // null with adjacent diagonal floor
						break;
					}
					
					if (isDoor) {
						
						if (Calc.countBits((byte) (floors & Moves.BITS_CARDINAL)) < 2) {
							toWall = true;
							break;
						}
						
						if (Calc.countBits((byte) (walls & Moves.BITS_CARDINAL)) > 2) {
							toWall = true;
							break;
						}
						
						if (Calc.countBits((byte) (floors & Moves.BITS_CARDINAL)) > 2) {
							toFloor = true;
							break;
						}
						
						if ((floors & Moves.BITS_NW_CORNER) == Moves.BITS_NW_CORNER) toWall = true;
						if ((floors & Moves.BITS_NE_CORNER) == Moves.BITS_NE_CORNER) toWall = true;
						if ((floors & Moves.BITS_SW_CORNER) == Moves.BITS_SW_CORNER) toWall = true;
						if ((floors & Moves.BITS_SE_CORNER) == Moves.BITS_SE_CORNER) toWall = true;
						
					}
				} while (false);
				
				if (toNull) {
					out[c.y][c.x] = Tiles.NULL.createTile();
				} else if (toWall) {
					out[c.y][c.x] = theme.wall().createTile();
				} else if (toFloor) {
					out[c.y][c.x] = theme.floor().createTile();
				} else {
					out[c.y][c.x] = map[c.y][c.x];
				}
				
				if (!out[c.y][c.x].isNull()) {
					genMin.x = Math.min(genMin.x, c.x);
					genMin.y = Math.min(genMin.y, c.y);
					
					genMax.x = Math.max(genMax.x, c.x);
					genMax.y = Math.max(genMax.y, c.y);
				}
			}
		}
		
		map = out;
	}
	
	
	/**
	 * Write tiles and entities into a level
	 *
	 * @param level the level
	 */
	public void writeToLevel(Level level)
	{
		if (level.getWorld() == null) {
			throw new WorldGenError("Level has no world assigned."); // need for entities
		}
		
		// make sure no walkable are at edges.
		final Coord c = Coord.make(0, 0);
		final Coord c1 = Coord.make(0, 0);
		
		for (c.x = genMin.x, c1.x = 0; c.x <= genMax.x; c.x++, c1.x++) {
			for (c.y = genMin.y, c1.y = 0; c.y <= genMax.y; c.y++, c1.y++) {
				level.setTile(c1, getTile(c));
			}
		}
		
		final Coord entrance = new Coord(enterPoint.x - genMin.x, enterPoint.y - genMin.y);
		level.setEnterPoint(entrance);
		
		final Coord exit = new Coord(exitPoint.x - genMin.x, exitPoint.y - genMin.y);
		level.setExitPoint(exit);
		
		for (final Entity e : entities) {
			final Coord pos = e.getCoord().add(-genMin.x, -genMin.y);
			if (!level.addEntityNear(e, pos)) {
				final Tile t = level.getTile(pos);
				//@formatter:off
				throw new WorldGenError(
						"Could not put entity into a level map: e_pos="	+ pos
						+ ", tile: " + Str.val(t)
						+ ", t.wa " + t.isWalkable()
						+ ", t.oc " + t.isOccupied()
						+ ", ent.. " + e.getVisualName());
				//@formatter:on
			}
		}
	}
	
	
	public void setEntrance(Coord pos)
	{
		enterPoint.setTo(pos);
	}
	
	
	public void setExit(Coord pos)
	{
		exitPoint.setTo(pos);
	}
	
	
	public boolean addItem(Item item, Coord pos)
	{
		return addItem(item, pos, true);
	}
	
	
	public boolean addItem(Item item, Coord pos, boolean canStack)
	{
		if (!isIn(pos)) return false;
		
		final Tile t = getTile(pos);
		if (!canStack && t.hasItem()) return false;
		if (t.dropItem(item)) return true;
		
		return false;
	}
	
	
	public boolean addItemInArea(Item item, Coord min, Coord max, int tries)
	{
		final Coord pos = Coord.zero();
		
		for (int i = 0; i < tries / 2; i++) {
			pos.x = Calc.randInt(rand, min.x, max.x);
			pos.y = Calc.randInt(rand, min.y, max.y);
			if (addItem(item, pos, false)) return true;
		}
		
		for (int i = 0; i < tries - (tries / 2); i++) {
			pos.x = Calc.randInt(rand, min.x, max.x);
			pos.y = Calc.randInt(rand, min.y, max.y);
			if (addItem(item, pos, true)) return true;
		}
		
		return false;
	}
	
	
	public boolean addItemInMap(Item item, int tries)
	{
		return addItemInArea(item, genMin, genMax, tries);
	}
	
	
	public boolean addEntityInArea(Entity entity, Coord min, Coord max, int tries)
	{
		final Coord pos = Coord.zero();
		
		for (int i = 0; i < tries; i++) {
			pos.x = Calc.randInt(rand, min.x, max.x);
			pos.y = Calc.randInt(rand, min.y, max.y);
			if (!isIn(pos)) continue;
			
			if (addEntity(entity, pos)) return true;
		}
		
		return false;
	}
	
	
	public boolean addEntityInMap(Entity entity, int tries)
	{
		return addEntityInArea(entity, genMin, genMax, tries);
	}
	
	
	public boolean addEntity(Entity entity, Coord pos)
	{
		if (!isIn(pos)) return false;
		
		if (pos.dist(enterPoint) < 4) return false; // protected distance.

		final Tile t = getTile(pos);
		if (!t.isWalkable()) return false;
		
		if (occupied.contains(pos)) return false;
		
		occupied.add(pos.copy());
		entity.setCoord(pos);
		entities.add(entity);
		
		return true;
	}
}
