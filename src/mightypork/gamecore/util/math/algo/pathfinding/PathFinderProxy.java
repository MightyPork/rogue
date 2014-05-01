package mightypork.gamecore.util.math.algo.pathfinding;


import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Step;


/**
 * Pathfinder proxy. Can be used to override individual methods but
 * keep the rest as is.
 * 
 * @author MightyPork
 */
public class PathFinderProxy extends PathFinder {
	
	private final PathFinder source;
	
	
	public PathFinderProxy(PathFinder other)
	{
		this.source = other;
	}
	
	
	@Override
	public boolean isAccessible(Coord pos)
	{
		return source.isAccessible(pos);
	}
	
	
	@Override
	public int getCost(Coord from, Coord to)
	{
		return source.getCost(from, to);
	}
	
	
	@Override
	public int getMinCost()
	{
		return source.getMinCost();
	}
	
	
	@Override
	protected Heuristic getHeuristic()
	{
		return source.getHeuristic();
	}
	
	
	@Override
	protected Step[] getWalkSides()
	{
		return source.getWalkSides();
	}
	
}
