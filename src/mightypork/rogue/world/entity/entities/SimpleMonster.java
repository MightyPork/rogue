package mightypork.rogue.world.entity.entities;


import mightypork.gamecore.util.math.algo.pathfinding.PathFindingContext;
import mightypork.rogue.world.entity.*;


public abstract class SimpleMonster extends Entity {
	
	/** Navigation PFC */
	private EntityPathfindingContext pathfc;
	
	private final EntityModule ai = new MonsterAi(this);
	
	
	public SimpleMonster(EntityModel model, int eid)
	{
		super(model, eid);
		
		addModule("ai", ai);
	}
	
	
	@Override
	public PathFindingContext getPathfindingContext()
	{
		if (pathfc == null) {
			pathfc = new SimpleEntityPathFindingContext(this);
		}
		
		return pathfc;
	}
	
	
	@Override
	public EntityType getType()
	{
		return EntityType.MONSTER;
	}
	
}
