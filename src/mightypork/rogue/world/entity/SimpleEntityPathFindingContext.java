package mightypork.rogue.world.entity;

import mightypork.rogue.world.Coord;


public class SimpleEntityPathFindingContext extends EntityPathfindingContext {

	public SimpleEntityPathFindingContext(Entity entity) {
		super(entity);
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
	
	
}
