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
import mightypork.rogue.world.tile.Tiles;
import mightypork.rogue.world.tile.models.TileModel;
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
			
			if (t.isWalkable()) return 10;
			
			return 100; // wall
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
			
			center.x = genMin.x + rand.nextInt(sizeX + 1);
			center.y = genMin.y + rand.nextInt(sizeY + 1);
			
			switch (rand.nextInt(4)) {
				case 0:
					center.x += 1 + rand.nextInt(3);
					break;
				case 1:
					center.x -= 1 + rand.nextInt(3);
					break;
				case 2:
					center.y += 1 + rand.nextInt(3);
					break;
				case 3:
					center.y -= 1 + rand.nextInt(3);
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
				
				if (failed > 40) {
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
		for (c.y = min.y; c.y <= max.y; c.y++)
			for (c.x = min.x; c.x <= max.x; c.x++)
				set(c, tm.createTile());
	}
	
	
	public void border(Coord min, Coord max, TileModel tm)
	{
		if (!isIn(min) || !isIn(max)) throw new IndexOutOfBoundsException("Tile(s) not in map: " + min + " , " + max);
		
		final Coord c = Coord.make(0, 0);
		for (c.y = min.y; c.y <= max.y; c.y++) {
			for (c.x = min.x; c.x <= max.x; c.x++) {
				
				if (c.y > min.y && c.y < max.y && c.x > min.x && c.x < max.x) continue;
				
				set(c, tm.createTile());
			}
		}
	}
	
	
	public void buildCorridors()
	{
		for (final Coord door1 : nodes) {
			for (final Coord door2 : nodes) {
				if (door1 == door2) continue;
				
				buildCorridor(door1, door2);
			}
		}
	}
	
	
	private void buildCorridor(Coord node1, Coord node2)
	{
		
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
				if (!current.getModel().isNullTile() && current.isWalkable()) continue; // floor already, let it be
					
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
	
	
	public void writeToLevel(Level level)
	{
		final Coord c1 = Coord.make(0, 0);
		final Coord c = Coord.make(0, 0);
		for (c.x = genMin.x, c1.x = 0; c.x <= genMax.x; c.x++, c1.x++) {
			for (c.y = genMin.y, c1.y = 0; c.y <= genMax.y; c.y++, c1.y++) {
				level.setTile(get(c), c1.x, c1.y);
			}
		}
		
		final WorldPos p = new WorldPos(enterPoint.x - genMin.x, enterPoint.y - genMin.y);
		level.setEnterPoint(p);
	}
}
