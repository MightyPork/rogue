package mightypork.gamecore.util.math.algo.pathfinding;


import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Step;


/**
 * Pathfinding context proxy. Can be used to override individual methods but
 * keep the rest as is.
 * 
 * @author MightyPork
 */
public class PathFindingContextProxy implements PathFindingContext {
	
	private final PathFindingContext source;
	
	
	public PathFindingContextProxy(PathFindingContext other)
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
	public Heuristic getHeuristic()
	{
		return source.getHeuristic();
	}
	
	
	@Override
	public Step[] getWalkSides()
	{
		return source.getWalkSides();
	}
	
}
