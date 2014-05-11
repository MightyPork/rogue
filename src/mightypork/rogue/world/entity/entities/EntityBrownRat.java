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


public class EntityBrownRat extends Entity {
	
	private EntityRenderer renderer;
	
	private final PathFinder pathf = new EntityPathFinder(this);
	
	private final BrownRatAi ai = new BrownRatAi(this);
	
	
	public EntityBrownRat(EntityModel model, int eid)
	{
		super(model, eid);
		
		addModule("ai", ai);
		pos.addMoveListener(ai);
		
		pos.setStepTime(0.37); // faster than gray rat
		setDespawnDelay(1);
		
		health.setMaxHealth(14);
		health.setHealth(Calc.randInt(rand, 8, 14)); // tougher to kill
		health.setHitCooldownTime(0.35); // a bit longer than gray rat
	}
	
	
	@Override
	protected EntityRenderer getRenderer()
	{
		if (renderer == null) {
			renderer = new EntityRendererMobLR(this, "sprite.rat.brown");
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
	public void onCorpseRemoved()
	{
		// drop rat stuff
		
		if (rand.nextInt(8) == 0) {
			getLevel().dropNear(getCoord(), Items.BONE.createItemDamaged(10));
			return;
		}
		
		if (rand.nextInt(3) == 0) {
			getLevel().dropNear(getCoord(), Items.MEAT.createItem());
			return;
		}
		
		if (rand.nextInt(6) == 0) {
			getLevel().dropNear(getCoord(), Items.CHEESE.createItem());
			return;
		}
	}
	
	
	@Override
	public String getVisualName()
	{
		return "Brown Rat";
	}
}
