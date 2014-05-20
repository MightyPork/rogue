package mightypork.rogue.world.entity.impl;


import mightypork.gamecore.util.math.algo.pathfinding.PathFinder;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityModel;
import mightypork.rogue.world.entity.EntityPathFinder;
import mightypork.rogue.world.entity.EntityRenderer;
import mightypork.rogue.world.entity.EntityType;
import mightypork.rogue.world.entity.render.EntityRendererMobLR;
import mightypork.rogue.world.events.GameWinEvent;


public class EntityBossRat extends Entity {
	
	private EntityRenderer renderer;
	
	/** Navigation PFC */
	private final PathFinder pathf = new EntityPathFinder(this);
	
	private final BossRatAi ai = new BossRatAi(this);
	
	
	public EntityBossRat(EntityModel model, int eid)
	{
		super(model, eid);
		
		addModule("ai", ai);
		pos.addMoveListener(ai);
		
		setDespawnDelay(1);
		
		health.setHealthMax(80);
		health.setHealth(80);
		health.setHitCooldownTime(0.33);
	}
	
	
	@Override
	protected EntityRenderer getRenderer()
	{
		if (renderer == null) {
			renderer = new EntityRendererMobLR(this, "sprite.rat.boss");
		}
		
		return renderer;
	}
	
	
	@Override
	public EntityType getType()
	{
		return EntityType.MONSTER;
	}
	
	
	@Override
	public PathFinder getPathFinder()
	{
		return pathf;
	}
	
	
	@Override
	public void onKilled()
	{
		// send kill event to listeners, after the entity has despawned (disappeared)
		getWorld().getEventBus().sendDelayed(new GameWinEvent(), getDespawnDelay() * 1.5); // dramatic pause
	}
	
	
	@Override
	public String getVisualName()
	{
		return "Rat Boss";
	}
}
