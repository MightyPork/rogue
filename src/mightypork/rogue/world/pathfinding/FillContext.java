package mightypork.rogue.world.pathfinding;


import mightypork.rogue.world.Coord;


public interface FillContext {
	
	boolean canEnter(Coord pos);
	
	
	boolean canSpread(Coord pos);
	
	
	/**
	 * Get the max distance filled form start point. Use -1 for unlimited range.
	 * 
	 * @return max distance
	 */
	int getMaxDistance();
}
