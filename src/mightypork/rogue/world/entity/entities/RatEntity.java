package mightypork.rogue.world.entity.entities;


import mightypork.gamecore.util.math.Calc;
import mightypork.gamecore.util.math.algo.pathfinding.PathFinder;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityModel;
import mightypork.rogue.world.entity.EntityPathFinder;
import mightypork.rogue.world.entity.EntityRenderer;
import mightypork.rogue.world.entity.EntityType;
import mightypork.rogue.world.entity.render.EntityRendererMobLR;
import mightypork.rogue.world.item.Items;


public class RatEntity extends Entity {
	
	/** Navigation PFC */
	private PathFinder pathf;
	
	private final RatAi ai = new RatAi(this);
	
	private EntityRenderer renderer;
	
	
	public RatEntity(EntityModel model, int eid) {
		super(model, eid);
		
		addModule("ai", ai);
		pos.addMoveListener(ai);
		
		pos.setStepTime(0.5);
		setDespawnDelay(1);
		
		health.setMaxHealth(5);
		health.setHealth(Calc.randInt(rand, 3, 5)); // fill health bar to max
		health.setHitCooldownTime(0.3);
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
	
	
	@Override
	public void onKilled()
	{
		super.onKilled();
	}
	
	
	@Override
	public void onCorpseRemoved()
	{
		// drop rat stuff
		
		if(rand.nextInt(7) == 0) {
			getLevel().dropNear(getCoord(), Items.BONE.createItem());
			return;
		}

		if(rand.nextInt(3) == 0) {
			getLevel().dropNear(getCoord(), Items.MEAT.createItem());
			return;
		}
		
		if(rand.nextInt(2) == 0) {
			getLevel().dropNear(getCoord(), Items.CHEESE.createItem());
			return;
		}
	}
	
	@Override
	public String getVisualName()
	{
		return "Gray Rat";
	}
}
