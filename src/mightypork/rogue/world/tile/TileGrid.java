package mightypork.rogue.world.tile;


public interface TileGrid {
	
	Tile getTile(int x, int y);
	
	
	int getWidth();
	
	
	int getHeight();
}
