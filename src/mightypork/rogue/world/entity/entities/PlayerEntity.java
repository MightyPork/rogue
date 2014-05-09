package mightypork.rogue.world.entity.entities;


import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.pathfinding.PathFinder;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityModel;
import mightypork.rogue.world.entity.EntityModule;
import mightypork.rogue.world.entity.EntityPathFinder;
import mightypork.rogue.world.entity.EntityType;
import mightypork.rogue.world.entity.modules.EntityMoveListener;
import mightypork.rogue.world.entity.render.EntityRenderer;
import mightypork.rogue.world.entity.render.EntityRendererMobLR;
import mightypork.rogue.world.events.PlayerKilledEvent;


public class PlayerEntity extends Entity {
	
	class PlayerAi extends EntityModule implements EntityMoveListener {
		
		public PlayerAi(Entity entity)
		{
			super(entity);
			setDespawnDelay(3);
			
			health.setMaxHealth(12);
			health.fill(); // fill health bar to max
		}
		
		
		@Override
		public void onStepFinished()
		{
			entity.getLevel().explore(entity.pos.getCoord());
		}
		
		
		@Override
		public void onPathFinished()
		{
		}
		
		
		@Override
		public void onPathInterrupted()
		{
		}
		
		
		@Override
		public boolean isModuleSaved()
		{
			return false;
		}
		
	}
	
	private PathFinder pathf;
	private EntityRenderer renderer;
	
	private final PlayerAi ai = new PlayerAi(this);
	
	
	public PlayerEntity(EntityModel model, int eid)
	{
		super(model, eid);
		
		pos.setStepTime(0.25);
		
		addModule("ai", ai);
		pos.addMoveListener(ai);
	}
	
	
	@Override
	public PathFinder getPathFinder()
	{
		if (pathf == null) {
			pathf = new EntityPathFinder(this) {
				
				@Override
				public int getCost(Coord from, Coord to)
				{
					if (!getLevel().getTile(pos.getCoord()).isExplored()) {
						return 1000; // avoid unexplored, but allow them if there's no other way
					}
					
					return super.getCost(from, to);
				};
			};
		}
		
		return pathf;
	}
	
	
	@Override
	protected EntityRenderer getRenderer()
	{
		if (renderer == null) {
			renderer = new EntityRendererMobLR(this, "sprite.player");
		}
		
		return renderer;
	}
	
	
	@Override
	public EntityType getType()
	{
		return EntityType.PLAYER;
	}
	
	
	@Override
	public void onKilled()
	{
		// send kill event to listeners (GUI?)
		getWorld().getEventBus().sendDelayed(new PlayerKilledEvent(), getDespawnDelay());
	}
}
