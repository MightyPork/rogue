package mightypork.rogue.world.entity;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import mightypork.rogue.world.World;
import mightypork.rogue.world.entity.modules.EntityModuleHealth;
import mightypork.rogue.world.entity.modules.EntityModulePosition;
import mightypork.rogue.world.entity.modules.EntityMoveListener;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.render.MapRenderContext;
import mightypork.rogue.world.pathfinding.PathFindingContext;
import mightypork.util.annotations.DefaultImpl;
import mightypork.util.error.IllegalValueException;
import mightypork.util.files.ion.IonBundle;
import mightypork.util.files.ion.IonBundled;
import mightypork.util.timing.Updateable;


/**
 * World entity (mob or player)
 * 
 * @author MightyPork
 */
public abstract class Entity implements IonBundled, Updateable, EntityMoveListener {
	
	private Level level;
	private final EntityModel model;
	
	/** Entity ID */
	private int entityId;
	
	private final Map<String, EntityModule> modules = new HashMap<>();
	
	// default modules
	public final EntityModulePosition pos = new EntityModulePosition(this);
	public final EntityModuleHealth health = new EntityModuleHealth(this);
	
	
	public Entity(EntityModel model, int eid)
	{
		
		this.entityId = eid;
		this.model = model;
		
		// register modules
		modules.put("pos", pos);
		pos.addMoveListener(this);
		modules.put("health", health);
	}
	
	
	@Override
	public void save(IonBundle bundle) throws IOException
	{
		bundle.put("eid", entityId);
		for (final Entry<String, EntityModule> entry : modules.entrySet()) {
			bundle.putBundled(entry.getKey(), entry.getValue());
		}
	}
	
	
	@Override
	public void load(IonBundle bundle) throws IOException
	{
		entityId = bundle.get("eid", -1);
		if (entityId < 0) throw new IllegalValueException("Bad entity id: " + entityId);
		
		for (final Entry<String, EntityModule> entry : modules.entrySet()) {
			bundle.loadBundled(entry.getKey(), entry.getValue());
		}
	}
	
	
	/**
	 * @return unique entity id
	 */
	public int getEntityId()
	{
		return entityId;
	}
	
	
	public void setLevel(Level level)
	{
		this.level = level;
	}
	
	
	public Level getLevel()
	{
		return level;
	}
	
	
	public World getWorld()
	{
		return getLevel().getWorld();
	}
	
	
	public EntityModel getModel()
	{
		return model;
	}
	
	
	public abstract PathFindingContext getPathfindingContext();
	
	
	public abstract void render(MapRenderContext context);
	
	
	@Override
	public void update(double delta)
	{
		for (final Entry<String, EntityModule> entry : modules.entrySet()) {
			entry.getValue().update(delta);
		}
	}
	
	
	@DefaultImpl
	public void onKilled()
	{
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
}
