package mightypork.rogue.world.map;


import mightypork.rogue.world.WorldPos;


public interface MapObserver {
	
	int getViewRange();
	
	
	WorldPos getViewPosition();
}
