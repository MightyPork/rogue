package mightypork.rogue.world.pathfinding;


import mightypork.rogue.world.Coord;


public abstract class Heuristic {
	
	/**
	 * Get tile cost (estimate of how many tiles remain to the target)
	 * 
	 * @param pos current pos
	 * @param target target pos
	 * @return estimated number of tiles
	 */
	public abstract double getCost(Coord pos, Coord target);
}
