package mightypork.test;


import java.io.IOException;

import mightypork.rogue.world.WorldMap;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;
import mightypork.util.files.ion.Ion;


public class TestTileMap {
	
	public static void main(String[] args) throws IOException
	{
		
		Ion.registerIonizable(WorldMap.ION_MARK, WorldMap.class);
		Ion.registerIonizable(Tile.ION_MARK, Tile.class);
		Ion.registerIonizable(Item.ION_MARK, Item.class);
		
		// register tile
		final TileModel tm = new FakeTile(1);
		
//		
//		WorldMap map = new WorldMap(10, 10);
//		
//		Random r = new Random();
//		
//		for(int i=0; i<10; i++) {
//			map.setTile(1, r.nextInt(10),r.nextInt(10));
//		}
//		
//		Ion.toFile("maptest.ion", map);
		
		final WorldMap map = (WorldMap) Ion.fromFile("maptest.ion");
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				final Tile t = map.getTile(x, y);
				System.out.print(" " + (t == null ? "   " : ((FakeTileData) t.getData()).number));
			}
			
			System.out.println();
		}
	}
}
