package mightypork.rogue.world.entity;


import mightypork.rogue.world.Coord;
import mightypork.rogue.world.Sides;
import mightypork.rogue.world.pathfinding.Heuristic;
import mightypork.rogue.world.pathfinding.PathFinder;
import mightypork.rogue.world.pathfinding.PathFindingContext;


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
	public Coord[] getWalkSides()
	{
		return Sides.cardinalSides;
	}
	
}
