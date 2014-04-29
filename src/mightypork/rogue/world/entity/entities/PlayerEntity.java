package mightypork.rogue.world.entity.entities;


import mightypork.rogue.world.Coord;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityModel;
import mightypork.rogue.world.entity.EntityPathfindingContext;
import mightypork.rogue.world.entity.SimpleEntityPathFindingContext;
import mightypork.rogue.world.entity.renderers.EntityRenderer;
import mightypork.rogue.world.entity.renderers.EntityRendererMobLR;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.render.MapRenderContext;
import mightypork.rogue.world.pathfinding.PathFindingContext;


public class PlayerEntity extends Entity {
	
	private final EntityPathfindingContext pathfc = new SimpleEntityPathFindingContext(this) {
		
		@Override
		public int getCost(Coord from, Coord to)
		{
			
			if (!getLevel().getTile(pos.getCoord()).isExplored()) { return 1000; }
			
			return super.getCost(from, to);
			
		};
	};
	
	private final EntityRenderer renderer = new EntityRendererMobLR(this, "sprite.player");
	
	
	public PlayerEntity(EntityModel model, int eid)
	{
		super(model, eid);
		
		// init default values
		pos.setStepTime(0.25);
	}
	
	
	@Override
	public PathFindingContext getPathfindingContext()
	{
		return pathfc;
	}
	
	
	@Override
	public void setLevel(Level level)
	{
		super.setLevel(level);
		onStepFinished(this);
	}
	
	
	@Override
	public void render(MapRenderContext context)
	{
		renderer.render(context);
	}
	
	
	@Override
	public void onStepFinished(Entity entity)
	{
		getLevel().explore(pos.getCoord());
	}
}
