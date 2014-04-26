package mightypork.rogue.world.entity.models;


import mightypork.rogue.world.Coord;
import mightypork.rogue.world.entity.Entities;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityData;
import mightypork.rogue.world.entity.renderers.EntityRenderer;
import mightypork.rogue.world.pathfinding.Heuristic;
import mightypork.rogue.world.pathfinding.PathFinder;
import mightypork.util.annotations.DefaultImpl;


/**
 * Entity model
 * 
 * @author MightyPork
 */
public abstract class EntityModel implements EntityMoveListener {
	
	/** Model ID */
	public final int id;
	public EntityRenderer renderer = EntityRenderer.NONE;
	
	
	public EntityModel(int id) {
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
	public Entity createEntity(int eid)
	{
		return new Entity(eid, this);
	}
	
	
	/**
	 * Entity is idle, waiting for action.
	 */
	public abstract void update(Entity entity, double delta);
	
	
	/**
	 * Get one path step duration (in seconds)
	 */
	public abstract double getStepTime(Entity entity);
	
	
	@Override
	public abstract void onStepFinished(Entity entity);
	
	
	@Override
	public abstract void onPathFinished(Entity entity);
	
	
	@Override
	public abstract void onPathInterrupted(Entity entity);
	
	
	@DefaultImpl
	public boolean canWalkInto(Entity entity, Coord pos)
	{
		return entity.getLevel().canWalkInto(pos);
	}
	
	
	@DefaultImpl
	public int getPathMinCost()
	{
		return 10;
	}
	
	
	@DefaultImpl
	public Heuristic getPathHeuristic()
	{
		return PathFinder.DIAGONAL_HEURISTIC;
	}
	
	
	@DefaultImpl
	public int getPathCost(Entity entity, Coord from, Coord to)
	{
		return 10;
	}
	
	
	public abstract void initMetadata(EntityData metadata);
	
	
	@DefaultImpl
	public void onEnteredLevel(Entity entity)
	{
	}
	
}
