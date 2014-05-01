package mightypork.rogue.world.entity.entities;


import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.pathfinding.PathFinder;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityModel;
import mightypork.rogue.world.entity.EntityModule;
import mightypork.rogue.world.entity.EntityPathFinder;
import mightypork.rogue.world.entity.EntityType;
import mightypork.rogue.world.entity.modules.EntityMoveListener;
import mightypork.rogue.world.entity.renderers.EntityRenderer;
import mightypork.rogue.world.entity.renderers.EntityRendererMobLR;
import mightypork.rogue.world.level.Level;


public class PlayerEntity extends Entity {
	
	class PlayerAi extends EntityModule implements EntityMoveListener {
		
		public PlayerAi(Entity entity)
		{
			super(entity);
			health.setMaxHealth(24);
			health.fill();
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
						return 1000;
					}
					
					return super.getCost(from, to);
				};
			};
		}
		
		return pathf;
	}
	
	
	@Override
	public void setLevel(Level level)
	{
		super.setLevel(level);
		ai.onStepFinished(); // explore start area
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
}
