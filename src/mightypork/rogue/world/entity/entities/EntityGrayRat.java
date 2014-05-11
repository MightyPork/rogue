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


public class EntityGrayRat extends Entity {
	
	private final PathFinder pathf = new EntityPathFinder(this);
	
	private final GrayRatAi ai = new GrayRatAi(this);
	
	private EntityRenderer renderer;
	
	
	public EntityGrayRat(EntityModel model, int eid)
	{
		super(model, eid);
		
		addModule("ai", ai);
		pos.addMoveListener(ai);
		
		pos.setStepTime(0.5);
		setDespawnDelay(1);
		
		health.setMaxHealth(5);
		health.setHealth(Calc.randInt(rand, 3, 5));
		health.setHitCooldownTime(0.3);
	}
	
	
	@Override
	public PathFinder getPathFinder()
	{
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
			renderer = new EntityRendererMobLR(this, "sprite.rat.gray");
		}
		
		return renderer;
	}
	
	
	@Override
	public void onCorpseRemoved()
	{
		// drop rat stuff
		
		if (rand.nextInt(6) == 0) {
			getLevel().dropNear(getCoord(), Items.BONE.createItemDamaged(10));
			return;
		}
		
		if (rand.nextInt(7) == 0) {
			getLevel().dropNear(getCoord(), Items.MEAT.createItem());
			return;
		}
		
		if (rand.nextInt(3) == 0) {
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
