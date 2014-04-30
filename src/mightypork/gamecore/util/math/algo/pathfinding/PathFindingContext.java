package mightypork.gamecore.util.math.algo.pathfinding;


import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Step;


public interface PathFindingContext {
	
	/**
	 * @param pos tile pos
	 * @return true if the tile is walkable
	 */
	boolean isAccessible(Coord pos);
	
	
	/**
	 * Cost of walking onto a tile. It's useful to use ie. 10 for basic step.
	 * 
	 * @param from last tile
	 * @param to current tile
	 * @return cost
	 */
	int getCost(Coord from, Coord to);
	
	
	/**
	 * @return lowest cost. Used to multiply heuristics.
	 */
	int getMinCost();
	
	
	/**
	 * @return used heuristic
	 */
	Heuristic getHeuristic();
	
	
	Step[] getWalkSides();
}
