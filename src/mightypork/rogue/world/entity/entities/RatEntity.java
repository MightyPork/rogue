package mightypork.rogue.world.entity.entities;


import mightypork.gamecore.util.math.algo.pathfinding.PathFinder;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityModel;
import mightypork.rogue.world.entity.EntityPathFinder;
import mightypork.rogue.world.entity.EntityType;
import mightypork.rogue.world.entity.render.EntityRenderer;
import mightypork.rogue.world.entity.render.EntityRendererMobLR;


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
		setDespawnDelay(3);
		
		health.setMaxHealth(3 + rand.nextInt(3));
		health.fill(); // fill health bar to max
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
