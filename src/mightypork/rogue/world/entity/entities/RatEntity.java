package mightypork.rogue.world.entity.entities;


import mightypork.gamecore.util.math.algo.pathfinding.PathFinder;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityModel;
import mightypork.rogue.world.entity.EntityPathFinder;
import mightypork.rogue.world.entity.EntityType;
import mightypork.rogue.world.entity.renderers.EntityRenderer;
import mightypork.rogue.world.entity.renderers.EntityRendererMobLR;


public class RatEntity extends Entity {
	
	/** Navigation PFC */
	private PathFinder pathf;
	
	private final RatAi ai = new RatAi(this);
	
	private EntityRenderer renderer;
	
	
	public RatEntity(EntityModel model, int eid)
	{
		super(model, eid);
		
		addModule("ai", ai);
		pos.addMoveListener(ai);
		
		pos.setStepTime(0.5);
	}
	
	
	@Override
	public PathFinder getPathFinder()
	{
		if (pathf == null) {
			pathf = new EntityPathFinder(this);
		}
		
		return pathf;
	}
	
	
	@Override
	public EntityType getType()
	{
		return EntityType.MONSTER;
	}
	
	
	@Override
	protected EntityRenderer getRenderer()
	{
		if (renderer == null) {
			renderer = new EntityRendererMobLR(this, "sprite.rat");
		}
		
		return renderer;
	}
	
}
