package mightypork.rogue.world.entity.models;


import mightypork.rogue.world.World;
import mightypork.rogue.world.WorldPos;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.level.Level;


/**
 * Player info
 * 
 * @author MightyPork
 */
public class PlayerModel extends EntityModel {
	
	private static final double STEP_TIME = 0.3;
	
	
	public PlayerModel(int id)
	{
		super(id);
	}
	
	
	@Override
	public Entity createEntity(int eid, WorldPos pos)
	{
		final Entity e = super.createEntity(eid, pos);
		
		// set metadata
		
		return e;
	}
	
	
	@Override
	public void update(Entity entity, Level level, double delta)
	{
	}
	
	
	@Override
	public boolean hasMetadata()
	{
		return true;
	}
	
	
	@Override
	public double getStepTime(Entity entity)
	{
		return STEP_TIME;
	}
	
	
	@Override
	public void onStepFinished(Entity entity, World world, Level level)
	{
	}
	
	
	@Override
	public void onPathFinished(Entity entity, World world, Level level)
	{
	}
}
