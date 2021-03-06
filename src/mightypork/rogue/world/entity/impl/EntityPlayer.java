package mightypork.rogue.world.entity.impl;


import mightypork.gamecore.core.App;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityModel;
import mightypork.rogue.world.entity.EntityModule;
import mightypork.rogue.world.entity.EntityPathFinder;
import mightypork.rogue.world.entity.EntityRenderer;
import mightypork.rogue.world.entity.EntityType;
import mightypork.rogue.world.entity.modules.EntityMoveListener;
import mightypork.rogue.world.entity.render.EntityRendererMobLR;
import mightypork.rogue.world.events.PlayerKilledEvent;
import mightypork.rogue.world.events.PlayerStepEndEvent;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.tile.Tile;
import mightypork.utils.math.algo.Coord;
import mightypork.utils.math.algo.pathfinding.PathFinder;


public class EntityPlayer extends Entity {
	
	class PlayerAi extends EntityModule implements EntityMoveListener {
		
		public PlayerAi(Entity entity)
		{
			super(entity);
			setDespawnDelay(2);
			
			health.setHealthMax(6); // 3 hearts
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
				
				if (item.pickUp(getWorld().getPlayer())) {
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
			App.bus().send(new PlayerStepEndEvent(EntityPlayer.this));
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
	
	
	public EntityPlayer(EntityModel model, int eid)
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
		App.bus().sendDelayed(new PlayerKilledEvent(), getDespawnDelay());
		
		getWorld().getConsole().msgDie(lastAttacker);
	}
	
	
	@Override
	public String getVisualName()
	{
		return "Player";
	}
}
