package mightypork.rogue.world.gen;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mightypork.rogue.world.Coord;
import mightypork.rogue.world.WorldPos;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.pathfinding.PathCostProvider;
import mightypork.rogue.world.pathfinding.PathFinder;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.Tiles;
import mightypork.util.logging.Log;


public class ScratchMap {
	
	private final Tile[][] map;
	private final int width;
	private final int height;
	
	private final List<RoomDesc> rooms = new ArrayList<>();
	private final List<Coord> nodes = new ArrayList<>(); // points to connect with corridors
	
	private final PathCostProvider pcp = new PathCostProvider() {
		
		@Override
		public boolean isAccessible(Coord pos)
		{
			return isIn(pos); // suffices for now
		}
		
		
		@Override
		public int getCost(Coord last, Coord pos)
		{
			final Tile t = get(pos);
			
			if (t.isNull()) return 15;
			
			if (t.isDoor()) return 10; // door
			if (t.isFloor()) return 20; // floor
				
			return 400; // wall
		}
		
		
		@Override
		public int getMinCost()
		{
			return 10;
		}
		
	};
	
	private final PathFinder pathFinder = new PathFinder(pcp, PathFinder.CORNER_HEURISTIC);
	
	Coord genMin;
	Coord genMax;
	private final Theme theme;
	private final Random rand;
	private Coord enterPoint;
	
	
	public ScratchMap(int max_size, Theme theme, Random rand)
	{
		map = new Tile[max_size][max_size];
		
		genMin = Coord.make((max_size / 2) - 1, (max_size / 2) - 1);
		genMax = genMin.add(1, 1);
		
		width = max_size;
		height = max_size;
		this.rand = rand;
		this.theme = theme;
		
		fill(Coord.make(0, 0), Coord.make(width - 1, height - 1), Tiles.NULL_EMPTY);
	}
	
	
	public void addRoom(RoomBuilder rb)
	{
		final Coord center = Coord.make(0, 0);
		
		int failed = 0;
		
		while (true) {
			
			final int sizeX = genMax.x - genMin.x;
			final int sizeY = genMax.y - genMin.y;
			
			center.x = genMin.x + rand.nextInt(sizeX);
			center.y = genMin.y + rand.nextInt(sizeY);
			
			switch (rand.nextInt(4)) {
				case 0:
					center.x += 1 + rand.nextInt(5);
					break;
				case 1:
					center.x -= 1 + rand.nextInt(5);
					break;
				case 2:
					center.y += 1 + rand.nextInt(5);
					break;
				case 3:
					center.y -= 1 + rand.nextInt(5);
			}
			
			final RoomDesc rd = rb.buildToFit(this, theme, rand, center);
			if (rd != null) {
				if (rooms.isEmpty()) {
					enterPoint = center.copy();
				}
				
				rooms.add(rd);
				
				genMin.x = Math.min(genMin.x, rd.min.x);
				genMin.y = Math.min(genMin.y, rd.min.y);
				
				genMax.x = Math.max(genMax.x, rd.max.x);
				genMax.y = Math.max(genMax.y, rd.max.y);
				
				nodes.add(center);
				Log.f3("placed room: " + rd.min + " -> " + rd.max);
				
				return;
			} else {
				failed++;
				
				if (failed > 150) {
					Log.w("Faild to build room.");
					return;
				}
			}
		}
		
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
		for (c.x = min.x; c.x <= max.x; c.x++) {
			for (c.y = min.y; c.y <= max.y; c.y++) {
				
				if (c.y > min.y && c.y < max.y && c.x > min.x && c.x < max.x) continue;
				
				set(c, tm.createTile());
			}
		}
	}
	
	
	public void buildCorridors()
	{
		Log.f3("Building corridors.");
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = i + 1; j < nodes.size(); j++) {
				buildCorridor(nodes.get(i), nodes.get(j));
			}
		}
	}
	
	
	private void buildCorridor(Coord node1, Coord node2)
	{
		//Log.f3("Finding path " + node1 + " -> " + node2);
		final List<Coord> steps = pathFinder.findPath(node1, node2);
		
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
				
				final Tile current = get(c);
				if (!current.isNull() && (current.isPotentiallyWalkable())) continue; // floor already, let it be
					
				if (i == 0 && j == 0) {
					set(c, theme.floor());
				} else {
					set(c, theme.wall());
				}
			}
		}
	}
	
	
	public Coord getNeededSize()
	{
		return Coord.make(genMax.x - genMin.x + 1, genMax.y - genMin.y + 1);
	}
	
	
	private int countBits(byte b)
	{
		int c = 0;
		for (int i = 0; i < 8; i++) {
			c += (b >> i) & 1;
		}
		return c;
	}
	
	
	public void writeToLevel(Level level)
	{
		//@formatter:off
		final Coord[] moves = {
				Coord.make(-1, 0),
				Coord.make(-1, -1),
				Coord.make(0, -1), 
				Coord.make(1, -1), 
				Coord.make(1, 0), 
				Coord.make(1, 1),
				Coord.make(0, 1),
				Coord.make(-1, 1)
		};
		//@formatter:on
		
		final byte cardinal = (byte) 0b10101010;
		final byte diagonal = (byte) 0b01010101;
		
		// make sure no walkable are at edges.
		final Coord c = Coord.make(0, 0);
		final Coord c1 = Coord.make(0, 0);
		for (c.x = 0; c.x < width; c.x++) {
			for (c.y = 0; c.y < height; c.y++) {
				
				final Tile t = get(c);
				final boolean isNull = t.isNull();
				
				final boolean isDoor = !isNull && t.isDoor();
				final boolean isFloor = !isNull && t.isFloor();
				final boolean isWall = !isNull && t.isWall();
				
				// bitmasks
				byte walls = 0;
				byte nils = 0;
				byte doors = 0;
				byte floors = 0;
				
				// gather info
				for (int i = 0; i <= 7; i++) {
					final Coord cc = c.add(moves[i]);
					
					if (!isIn(cc)) {
						nils |= 1 << (7 - i);
						continue;
					}
					
					final Tile t2 = get(cc);
					
					if (t2.isNull()) {
						nils |= 1 << (7 - i);
						continue;
					}
					
					if (t2.isDoor()) {
						doors |= 1 << (7 - i);
						continue;
					}
					
					if (t2.isWall()) {
						walls |= 1 << (7 - i);
						continue;
					}
					
					floors |= 1 << (7 - i);
				}
				
				boolean toWall = false;
				boolean toFloor = false;
				boolean toNull = false;
				
				if (isFloor && (nils & cardinal) != 0) {
					toWall = true; // floor with adjacent cardinal null
				}
				
				if (isNull && (floors & diagonal) != 0) {
					toWall = true; // null with adjacent diagonal floor
				}
				
				if (isWall && floors == 0) {
					toNull = true;
				}
				
				if (isDoor) {
					do {
						if (countBits((byte) (floors & cardinal)) < 2) {
							toWall = true;
							break;
						}
						
						if (countBits((byte) (walls & cardinal)) > 2) {
							toWall = true;
							break;
						}
						
						if (countBits((byte) (floors & cardinal)) > 2) {
							toFloor = true;
							break;
						}
						
						if ((floors & 0b11100000) == 0b11100000) toWall = true;
						if ((floors & 0b00111000) == 0b00111000) toWall = true;
						if ((floors & 0b00001110) == 0b00001110) toWall = true;
						if ((floors & 0b10000011) == 0b10000011) toWall = true;
					} while (false);
				}
				
				if (toWall) {
					set(c, theme.wall());
				} else if (toFloor) {
					set(c, theme.floor());
				} else if (toNull) {
					set(c, Tiles.NULL_EMPTY);
				}
			}
		}
		
		for (c.x = genMin.x, c1.x = 0; c.x <= genMax.x; c.x++, c1.x++) {
			for (c.y = genMin.y, c1.y = 0; c.y <= genMax.y; c.y++, c1.y++) {
				level.setTile(get(c), c1.x, c1.y);
			}
		}
		
		final WorldPos p = new WorldPos(enterPoint.x - genMin.x, enterPoint.y - genMin.y);
		level.setEnterPoint(p);
	}
}
