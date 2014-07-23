package mightypork.rogue.world.entity.impl;


import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityModel;
import mightypork.rogue.world.entity.EntityPathFinder;
import mightypork.rogue.world.entity.EntityRenderer;
import mightypork.rogue.world.entity.EntityType;
import mightypork.rogue.world.entity.render.EntityRendererMobLR;
import mightypork.rogue.world.item.Items;
import mightypork.utils.math.Calc;
import mightypork.utils.math.algo.pathfinding.PathFinder;


public class EntityGrayRat extends Entity {
	
	private final PathFinder pathf = new EntityPathFinder(this);
	
	private final GrayRatAi ai = new GrayRatAi(this);
	
	private EntityRenderer renderer;
	
	
	public EntityGrayRat(EntityModel model, int eid) {
		super(model, eid);
		
		addModule("ai", ai);
		pos.addMoveListener(ai);
		
		setDespawnDelay(1);
		
		health.setHealthMax(7);
		health.setHealth(Calc.randInt(4, 7));
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
	public void onKilled()
	{
		// drop rat stuff
		
		if (Calc.rand.nextInt(6) == 0) {
			getLevel().dropNear(getCoord(), Items.BONE.createItemDamaged(40));
			return;
		}
		
		if (Calc.rand.nextInt(3) == 0) {
			getLevel().dropNear(getCoord(), Items.CHEESE.createItem());
			return;
		}
		
		if (Calc.rand.nextInt(3) == 0) {
			getLevel().dropNear(getCoord(), Items.MEAT.createItem());
			return;
		}
	}
	
	
	@Override
	public String getVisualName()
	{
		return "Gray Rat";
	}
}
