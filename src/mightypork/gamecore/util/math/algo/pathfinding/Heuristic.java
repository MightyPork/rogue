package mightypork.gamecore.util.math.algo.pathfinding;


import mightypork.gamecore.util.math.algo.Coord;


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
