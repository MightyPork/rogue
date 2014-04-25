package mightypork.rogue.world.entity.models;


import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityData;
import mightypork.rogue.world.entity.renderers.PlayerRenderer;


/**
 * Player info
 * 
 * @author MightyPork
 */
public class PlayerModel extends EntityModel {
	
	private static final double STEP_TIME = 0.25;
	
	
	public PlayerModel(int id)
	{
		super(id);
		setRenderer(new PlayerRenderer("player"));
	}
	
	
	@Override
	public void update(Entity entity, double delta)
	{
	}
	
	
	@Override
	public double getStepTime(Entity entity)
	{
		return STEP_TIME;
	}
	
	
	@Override
	public void onStepFinished(Entity entity)
	{
	}
	
	
	@Override
	public void onPathFinished(Entity entity)
	{
	}
	
	
	@Override
	public void onPathInterrupted(Entity entity)
	{
	}
	
	
	@Override
	public void initMetadata(EntityData metadata)
	{
	}
}
