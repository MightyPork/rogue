package mightypork.rogue.world.entity;


import java.util.List;

import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Move;
import mightypork.gamecore.util.math.algo.Moves;
import mightypork.gamecore.util.math.algo.pathfinding.Heuristic;
import mightypork.gamecore.util.math.algo.pathfinding.PathFinder;


/**
 * Basic Pathfinder implementation for entities
 * 
 * @author Ondřej Hruška
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
	public List<Move> getWalkSides()
	{
		return Moves.CARDINAL_SIDES;
	}
	
}
