package mightypork.rogue.world.entity;


import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Sides;
import mightypork.gamecore.util.math.algo.Step;
import mightypork.gamecore.util.math.algo.pathfinding.Heuristic;
import mightypork.gamecore.util.math.algo.pathfinding.PathFinder;


/**
 * Basic Pathfinder implementation for entities
 * 
 * @author MightyPork
 */
public class EntityPathFinder extends PathFinder {
	
	protected final Entity entity;
	
	
	public EntityPathFinder(Entity entity)
	{
		this.entity = entity;
	}
	
	
	@Override
	public boolean isAccessible(Coord pos)
	{
		return entity.getLevel().isWalkable(pos);
	}
	
	
	@Override
	public int getCost(Coord from, Coord to)
	{
		return 10;
	}
	
	
	@Override
	public int getMinCost()
	{
		return 10;
	}
	
	
	@Override
	public Heuristic getHeuristic()
	{
		return PathFinder.CORNER_HEURISTIC;
	}
	
	
	@Override
	public Step[] getWalkSides()
	{
		return Sides.CARDINAL_SIDES;
	}
	
}
