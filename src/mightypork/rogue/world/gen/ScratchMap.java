package mightypork.rogue.world.gen;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mightypork.rogue.world.WorldPos;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.Tiles;
import mightypork.rogue.world.tile.models.TileModel;


public class ScratchMap {
	
	private Tile[][] map;
	private int width;
	private int height;
	
	private List<RoomDesc> rooms = new ArrayList<>();
	
	Coord genMin;
	Coord genMax;
	private Theme theme;
	private Random rand;
	private Coord enterPoint;
	
	
	public ScratchMap(int max_size, Theme theme, Random rand)
	{
		map = new Tile[max_size][max_size];
		
		genMin = Coord.make(max_size / 2, max_size / 2);
		genMax = genMin.add(1, 1);
		
		width = max_size;
		height = max_size;
		this.rand = rand;
		this.theme = theme;
		
		fill(Coord.make(0, 0), Coord.make(width - 1, height - 1), Tiles.NULL_EMPTY);
	}
	
	
	public void addRoom(RoomBuilder rb)
	{
		Coord center = Coord.make(0, 0);
		
		int failed = 0;
		
		while (true) {
			center.x = genMin.x + rand.nextInt(genMax.x - genMin.x);
			center.y = genMin.y + rand.nextInt(genMax.y - genMin.y);
			
			RoomDesc rd = rb.buildToFit(this, theme, rand, center);
			if (rd != null) {
				if (rooms.isEmpty()) {
					enterPoint = center.copy();
				}
				
				rooms.add(rd);
				
				genMin.x = Math.min(genMin.x, rd.min.x);
				genMin.y = Math.min(genMin.y, rd.min.y);
				
				genMax.x = Math.max(genMax.x, rd.max.x);
				genMax.y = Math.max(genMax.y, rd.max.y);
				
				return;
			} else {
				failed++;
				if (failed % 5 == 0) {
					switch(rand.nextInt(4)) {
						case 0: genMin.x--; break;
						case 1: genMin.y--; break;
						case 2: genMax.x++; break;
						case 3: genMax.y++; break;
					}
				}
				
				if (failed > 200) {
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
			return Tiles.NULL_SOLID.createTile();
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
			return false;
		}
		
		map[pos.y][pos.x] = tile;
		return true;
	}
	
	
	public boolean canBuild(Coord pos)
	{
		if (!isIn(pos)) return false;
		TileModel tm = get(pos).getModel();
		return tm.isNullTile() && tm.isWalkable();
	}
	
	
	public boolean isClear(Coord min, Coord max)
	{
		if (!isIn(min)) return false;
		if (!isIn(max)) return false;
		
		for (RoomDesc r : rooms) {
			if (r.intersectsWith(min, max)) return false;
		}
		
		return true;
	}
	
	
	public void fill(Coord min, Coord max, TileModel tm)
	{
		Coord c = Coord.make(0, 0);
		for (c.y = min.y; c.y <= max.y; c.y++)
			for (c.x = min.x; c.x <= max.x; c.x++)
				set(c, tm.createTile());
	}
	
	
	public void border(Coord min, Coord max, TileModel tm)
	{
		Coord c = Coord.make(0, 0);
		for (c.y = min.y; c.y <= max.y; c.y++) {
			for (c.x = min.x; c.x <= max.x; c.x++) {
				
				if (c.y > min.y && c.y < max.y && c.x > min.x && c.x < max.x) continue;
				
				set(c, tm.createTile());
			}
		}
	}
	
	
	public Coord getNeededSize()
	{
		return Coord.make(genMax.x - genMin.x + 1, genMax.y - genMin.y + 1);
	}
	
	
	public void writeToLevel(Level level)
	{
		
		Coord c1 = Coord.make(0, 0);
		Coord c = Coord.make(0, 0);
		for (c.x = genMin.x, c1.x = 0; c.x <= genMax.x; c.x++, c1.x++) {
			for (c.y = genMin.y, c1.y = 0; c.y <= genMax.y; c.y++, c1.y++) {
				level.setTile(get(c), c1.x, c1.y);
			}
		}
		
		WorldPos p = new WorldPos(enterPoint.x - genMin.x, enterPoint.y - genMin.y);
		level.setEnterPoint(p);
	}
}
