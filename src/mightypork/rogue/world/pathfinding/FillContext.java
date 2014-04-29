package mightypork.rogue.world.pathfinding;


import mightypork.rogue.world.Coord;


public interface FillContext {
	
	boolean canEnter(Coord pos);
	
	
	boolean canSpreadFrom(Coord pos);
	
	
	Coord[] getSpreadSides();
	
	
	/**
	 * Get the max distance filled form start point. Use -1 for unlimited range.
	 * 
	 * @return max distance
	 */
	double getMaxDistance();
	
	
	/**
	 * @return true if start should be spread no matter what
	 */
	boolean forceSpreadStart();
}
