package mightypork.rogue.world;


public interface TileHolder {
	
	TileData getTile(int x, int y);
	
	
	int getWidth();
	
	
	int getHeight();
}
