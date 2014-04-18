package mightypork.rogue.world.tile;


public interface TileHolder {
	
	Tile getTile(int x, int y);
	
	
	int getWidth();
	
	
	int getHeight();
}
