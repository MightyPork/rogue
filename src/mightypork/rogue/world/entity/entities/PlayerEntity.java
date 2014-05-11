package mightypork.rogue.world.entity.entities;


import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.pathfinding.PathFinder;
import mightypork.rogue.world.entity.*;
import mightypork.rogue.world.entity.modules.EntityMoveListener;
import mightypork.rogue.world.entity.render.EntityRendererMobLR;
import mightypork.rogue.world.events.PlayerKilledEvent;
import mightypork.rogue.world.events.PlayerStepEndEvent;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.tile.Tile;


public class PlayerEntity extends Entity {
	
	class PlayerAi extends EntityModule implements EntityMoveListener {
		
		public PlayerAi(Entity entity)
		{
			super(entity);
			setDespawnDelay(2);
			
			health.setMaxHealth(12);
			health.fill(); // fill health bar to max
			health.setHitCooldownTime(0.5);
		}
		
		
		@Override
		public void onStepFinished()
		{
			entity.getLevel().explore(entity.pos.getCoord());
			fireEvt();
			
			// try to pickup items
			
			final Tile t = getLevel().getTile(getCoord());
			if (t.hasItem()) {
				final Item item = t.pickItem();
				if (getWorld().getPlayer().addItem(item)) {
					// player picked item
				} else {
					t.dropItem(item); // put back.
				}
			}
		}
		
		
		@Override
		public void onPathFinished()
		{
			fireEvt();
		}
		
		
		@Override
		public void onPathInterrupted()
		{
		}
		
		
		private void fireEvt()
		{
			getWorld().getEventBus().send(new PlayerStepEndEvent(PlayerEntity.this));
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
				}
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
		// send kill event to listeners, after the entity has despawned (disappeared)
		getWorld().getEventBus().sendDelayed(new PlayerKilledEvent(), getDespawnDelay());
		
		getWorld().msgDie(lastAttacker);
		
	}
	
	
	@Override
	public String getVisualName()
	{
		return "Player";
	}
}
