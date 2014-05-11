package mightypork.rogue.world.gen;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import mightypork.gamecore.logging.Log;
import mightypork.gamecore.util.math.Calc;
import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Sides;
import mightypork.gamecore.util.math.algo.Step;
import mightypork.gamecore.util.math.algo.pathfinding.Heuristic;
import mightypork.gamecore.util.math.algo.pathfinding.PathFinder;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.Tiles;


/**
 * Temporary tile map used for level generation.
 * 
 * @author MightyPork
 */
public class ScratchMap {
	
	private Tile[][] map;
	private final int width;
	private final int height;
	
	private final List<RoomDesc> rooms = new ArrayList<>();
	
	/** Coords to connect with corridors */
	private final List<Coord> nodes = new ArrayList<>();
	
	private final PathFinder pathf = new PathFinder() {
		
		@Override
		public boolean isAccessible(Coord pos)
		{
			if (!isIn(pos)) return false;
			final Tile t = get(pos);
			if (t.isStairs()) return false;
			return t.isPotentiallyWalkable() || (t.genData.protection != TileProtectLevel.STRONG);
		}
		
		
		@Override
		public int getCost(Coord last, Coord pos)
		{
			final Tile t = get(pos);
			
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
					throw new RuntimeException("Unknown tile type: " + t.getType());
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
		public Step[] getWalkSides()
		{
			return Sides.CARDINAL_SIDES;
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
	
	
	public void addRoom(RoomBuilder rb, boolean critical)
	{
		final Coord center = Coord.make(0, 0);
		
		int failed = 0;
		
		int failed_total = 0;
		
		while (true) {
			
			final int sizeX = genMax.x - genMin.x;
			final int sizeY = genMax.y - genMin.y;
			
			center.x = genMin.x + rand.nextInt(sizeX);
			center.y = genMin.y + rand.nextInt(sizeY);
			
			switch (rand.nextInt(4)) {
				case 0:
					center.x += 1 + (failed_total / 50);
					break;
				case 1:
					center.x -= 1 + (failed_total / 50);
					break;
				case 2:
					center.y += 1 + (failed_total / 50);
					break;
				case 3:
					center.y -= 1 + (failed_total / 50);
			}
			
			final RoomDesc rd = rb.buildToFit(this, theme, rand, center);
			if (rd != null) {
				rooms.add(rd);
				
				genMin.x = Math.min(genMin.x, rd.min.x);
				genMin.y = Math.min(genMin.y, rd.min.y);
				
				genMax.x = Math.max(genMax.x, rd.max.x);
				genMax.y = Math.max(genMax.y, rd.max.y);
				clampBounds();
				
				nodes.add(center);
//				Log.f3("placed room: " + rd.min + " -> " + rd.max);
				
				return;
			} else {
				failed++;
				failed_total++;
				
				if (failed > 400) {
					Log.w("Faild to build room.");
					if (critical) {
						
						genMin.x -= 5;
						genMin.y -= 5;
						genMax.x += 5;
						genMax.y += 5;
						clampBounds();
						
						failed = 0;
						continue;
						
					} else {
						return;
					}
				}
				
				if (failed_total > 1000) {
					throw new RuntimeException("Generation error - could not place critical room.");
				}
			}
		}
		
	}
	
	
	private void clampBounds()
	{
		genMin.x = Calc.clamp(genMin.x, 0, width - 1);
		genMin.y = Calc.clamp(genMin.y, 0, height - 1);
		genMax.x = Calc.clamp(genMax.x, 0, width - 1);
		genMax.y = Calc.clamp(genMax.y, 0, height - 1);
	}
	
	
	public boolean isIn(Coord pos)
	{
		return pos.x >= 0 && pos.x < width && pos.y >= 0 && pos.y < height;
	}
	
	
	public Tile get(Coord pos)
	{
		if (!isIn(pos)) {
			throw new IndexOutOfBoundsException("Tile not in map: " + pos);
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
			throw new IndexOutOfBoundsException("Tile not in map: " + pos);
		}
		
		map[pos.y][pos.x] = tile;
		return true;
	}
	
	
	public boolean isClear(Coord min, Coord max)
	{
		if (!isIn(min) || !isIn(max)) return false;
		for (final RoomDesc r : rooms) {
			if (r.intersectsWith(min, max)) return false;
		}
		
		return true;
	}
	
	
	public void protect(Coord min, Coord max, TileProtectLevel prot)
	{
		if (!isIn(min) || !isIn(max)) throw new IndexOutOfBoundsException("Tile(s) not in map: " + min + " , " + max);
		
		final Coord c = Coord.make(0, 0);
		for (c.x = min.x; c.x <= max.x; c.x++)
			for (c.y = min.y; c.y <= max.y; c.y++)
				get(c).genData.protection = prot;
	}
	
	
	public void fill(Coord min, Coord max, TileModel tm)
	{
		if (!isIn(min) || !isIn(max)) throw new IndexOutOfBoundsException("Tile(s) not in map: " + min + " , " + max);
		
		final Coord c = Coord.make(0, 0);
		for (c.x = min.x; c.x <= max.x; c.x++)
			for (c.y = min.y; c.y <= max.y; c.y++)
				set(c, tm.createTile());
	}
	
	
	public void border(Coord min, Coord max, TileModel tm)
	{
		if (!isIn(min) || !isIn(max)) throw new IndexOutOfBoundsException("Tile(s) not in map: " + min + " , " + max);
		
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
			start = Calc.pick(nodes);
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
				
				final Tile current = get(c);
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
	
	
	public Coord getNeededSize()
	{
		return Coord.make(genMax.x - genMin.x + 1, genMax.y - genMin.y + 1);
	}
	
	
	public int countBits(byte b)
	{
		int c = 0;
		for (int i = 0; i < 8; i++) {
			c += (b >> i) & 1;
		}
		return c;
	}
	
	
	public byte findWalls(Coord pos)
	{
		byte walls = 0;
		for (int i = 0; i < 8; i++) {
			final Coord cc = pos.add(Sides.get(i));
			if (!isIn(cc)) continue;
			
			if (get(cc).isWall()) {
				walls |= Sides.bit(i);
			}
		}
		return walls;
	}
	
	
	public byte findFloors(Coord pos)
	{
		byte floors = 0;
		for (int i = 0; i <= 7; i++) {
			final Coord cc = pos.add(Sides.get(i));
			if (!isIn(cc)) continue;
			
			if (get(cc).isFloor()) {
				floors |= Sides.bit(i);
			}
		}
		return floors;
	}
	
	
	public byte findDoors(Coord pos)
	{
		byte doors = 0;
		for (int i = 0; i <= 7; i++) {
			final Coord cc = pos.add(Sides.get(i));
			if (!isIn(cc)) continue;
			
			if (get(cc).isDoor()) {
				doors |= Sides.bit(i);
			}
		}
		return doors;
	}
	
	
	public byte findNils(Coord pos)
	{
		byte nils = 0;
		for (int i = 0; i <= 7; i++) {
			final Coord cc = pos.add(Sides.get(i));
			
			if (!isIn(cc) || get(cc).isNull()) {
				nils |= Sides.bit(i);
			}
		}
		return nils;
	}
	
	
	public void writeToLevel(Level level)
	{
		// make sure no walkable are at edges.
		final Coord c = Coord.make(0, 0);
		final Coord c1 = Coord.make(0, 0);
		
		if (FIX_GLITCHES) {
			
			final Tile[][] out = new Tile[height][width];
			
			for (c.x = 0; c.x < width; c.x++) {
				for (c.y = 0; c.y < height; c.y++) {
					
					final Tile t = get(c);
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
						
						if (isFloor && (nils & Sides.MASK_CARDINAL) != 0) {
							toWall = true; // floor with adjacent cardinal null
							break;
						}
						
						if (isNull && (floors & Sides.MASK_DIAGONAL) != 0) {
							toWall = true; // null with adjacent diagonal floor
							break;
						}
						
						if (isDoor) {
							
							if (countBits((byte) (floors & Sides.MASK_CARDINAL)) < 2) {
								toWall = true;
								break;
							}
							
							if (countBits((byte) (walls & Sides.MASK_CARDINAL)) > 2) {
								toWall = true;
								break;
							}
							
							if (countBits((byte) (floors & Sides.MASK_CARDINAL)) > 2) {
								toFloor = true;
								break;
							}
							
							if ((floors & Sides.NW_CORNER) == Sides.NW_CORNER) toWall = true;
							if ((floors & Sides.NE_CORNER) == Sides.NE_CORNER) toWall = true;
							if ((floors & Sides.SW_CORNER) == Sides.SW_CORNER) toWall = true;
							if ((floors & Sides.SE_CORNER) == Sides.SE_CORNER) toWall = true;
							
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
				}
			}
			
			map = out;
		}
		
		for (c.x = genMin.x, c1.x = 0; c.x <= genMax.x; c.x++, c1.x++) {
			for (c.y = genMin.y, c1.y = 0; c.y <= genMax.y; c.y++, c1.y++) {
				level.setTile(c1, get(c));
			}
		}
		
		final Coord entrance = new Coord(enterPoint.x - genMin.x, enterPoint.y - genMin.y);
		level.setEnterPoint(entrance);
//		System.out.println("Entrance = " + entrance + ", original: " + enterPoint + ", minG=" + genMin + ", maxG=" + genMax);
		
		final Coord exit = new Coord(exitPoint.x - genMin.x, exitPoint.y - genMin.y);
		level.setExitPoint(exit);
	}
	
	
	public void setEntrance(Coord pos)
	{
		enterPoint.setTo(pos);
	}
	
	
	public void setExit(Coord pos)
	{
		exitPoint.setTo(pos);
	}
}
