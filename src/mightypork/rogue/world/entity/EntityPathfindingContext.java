package mightypork.rogue.world.entity;


import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Sides;
import mightypork.gamecore.util.math.algo.Step;
import mightypork.gamecore.util.math.algo.pathfinding.Heuristic;
import mightypork.gamecore.util.math.algo.pathfinding.PathFinder;
import mightypork.gamecore.util.math.algo.pathfinding.PathFindingContext;


public abstract class EntityPathfindingContext implements PathFindingContext {
	
	protected final Entity entity;
	
	
	public EntityPathfindingContext(Entity entity)
	{
		this.entity = entity;
	}
	
	
	@Override
	public boolean isAccessible(Coord pos)
	{
		return entity.getLevel().isWalkable(pos);
	}
	
	
	@Override
	public abstract int getCost(Coord from, Coord to);
	
	
	@Override
	public abstract int getMinCost();
	
	
	@Override
	public Heuristic getHeuristic()
	{
		return PathFinder.DIAGONAL_HEURISTIC;
	}
	
	
	@Override
	public Step[] getWalkSides()
	{
		return Sides.cardinalSides;
	}
	
}
