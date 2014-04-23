package mightypork.rogue.world.entity.models;


import mightypork.rogue.world.World;
import mightypork.rogue.world.WorldPos;
import mightypork.rogue.world.entity.Entities;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.renderers.EntityRenderer;
import mightypork.rogue.world.level.Level;


/**
 * Entity model
 * 
 * @author MightyPork
 */
public abstract class EntityModel implements EntityMoveListener {
	
	/** Model ID */
	public final int id;
	public EntityRenderer renderer = EntityRenderer.NONE;
	
	
	public EntityModel(int id)
	{
		Entities.register(id, this);
		this.id = id;
	}
	
	
	public EntityModel setRenderer(EntityRenderer renderer)
	{
		this.renderer = renderer;
		return this;
	}
	
	
	/**
	 * @return new tile of this type; if 100% invariant, can return cached one.
	 */
	public Entity createEntity(int eid, WorldPos pos)
	{
		return new Entity(eid, pos, this);
	}
	
	
	/**
	 * Entity is idle, waiting for action.
	 */
	public abstract void update(Entity entity, Level level, double delta);
	
	
	/**
	 * @return true if this entity type has metadata worth saving
	 */
	public abstract boolean hasMetadata();
	
	
	/**
	 * Get one path step duration (in seconds)
	 */
	public abstract double getStepTime(Entity entity);
	
	
	@Override
	public abstract void onStepFinished(Entity entity, World world, Level level);
	
	
	@Override
	public abstract void onPathFinished(Entity entity, World world, Level level);
	
	
	@Override
	public abstract void onPathInterrupted(Entity entity, World world, Level level);
	
}
