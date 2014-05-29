package mightypork.rogue.world.entity;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import mightypork.rogue.world.World;
import mightypork.rogue.world.entity.modules.EntityModuleHealth;
import mightypork.rogue.world.entity.modules.EntityModulePosition;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.render.MapRenderContext;
import mightypork.utils.annotations.DefaultImpl;
import mightypork.utils.eventbus.clients.DelegatingClient;
import mightypork.utils.exceptions.IllegalValueException;
import mightypork.utils.interfaces.Updateable;
import mightypork.utils.ion.IonBundled;
import mightypork.utils.ion.IonDataBundle;
import mightypork.utils.math.algo.Coord;
import mightypork.utils.math.algo.pathfinding.PathFinder;


/**
 * World entity (mob or player). Entities are attached to the event bus.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class Entity implements IonBundled, Updateable, DelegatingClient {
	
	private Level level;
	private final EntityModel model;
	
	/** Entity ID */
	private int entityId = -1;
	
	private final Map<String, EntityModule> modules = new HashMap<>();
	
	// default modules
	public final EntityModulePosition pos = new EntityModulePosition(this);
	public final EntityModuleHealth health = new EntityModuleHealth(this);
	private double despawnDelay = 1;
	protected Entity lastAttacker;
	private boolean freed;
	
	
	public Entity(EntityModel model, int eid)
	{
		this.entityId = eid;
		this.model = model;
		
		// register modules
		addModule("pos", pos);
		addModule("health", health);
	}
	
	
	@Override
	public final void save(IonDataBundle bundle)
	{
		bundle.put("eid", entityId);
		
		final IonDataBundle modulesBundle = new IonDataBundle();
		for (final Entry<String, EntityModule> entry : modules.entrySet()) {
			modulesBundle.putBundled(entry.getKey(), entry.getValue());
		}
		bundle.put("modules", modulesBundle);
		
		final IonDataBundle extra = new IonDataBundle();
		saveExtra(extra);
		bundle.put("extra", extra);
	}
	
	
	@DefaultImpl
	protected void saveExtra(IonDataBundle bundle)
	{
	}
	
	
	@Override
	public final void load(IonDataBundle bundle)
	{
		entityId = bundle.get("eid", -1);
		if (entityId < 0) throw new IllegalValueException("Bad entity id: " + entityId);
		
		final IonDataBundle modulesBundle = bundle.get("modules", new IonDataBundle());
		
		for (final Entry<String, EntityModule> entry : modules.entrySet()) {
			modulesBundle.loadBundled(entry.getKey(), entry.getValue());
		}
		
		final IonDataBundle extra = bundle.get("extra", new IonDataBundle());
		loadExtra(extra);
	}
	
	
	@DefaultImpl
	protected void loadExtra(IonDataBundle bundle)
	{
	}
	
	
	protected final void addModule(String key, EntityModule module)
	{
		if (modules.containsKey(key)) {
			throw new RuntimeException("Entity module " + key + " already defined.");
		}
		modules.put(key, module);
	}
	
	
	/**
	 * @return unique entity id
	 */
	public final int getEntityId()
	{
		return entityId;
	}
	
	
	public void setLevel(Level level)
	{
		if (level != null && entityId < 0) {
			entityId = level.getWorld().getNewEID();
		}
		
		if (level != null) level.freeTile(getCoord());
		
		this.level = level;
		
		if (level != null) level.occupyTile(getCoord());
	}
	
	
	public final Level getLevel()
	{
		return level;
	}
	
	
	public final World getWorld()
	{
		return level.getWorld();
	}
	
	
	public final EntityModel getModel()
	{
		return model;
	}
	
	
	public abstract PathFinder getPathFinder();
	
	
	@DefaultImpl
	public final void render(MapRenderContext context)
	{
		getRenderer().render(context);
	}
	
	
	protected abstract EntityRenderer getRenderer();
	
	
	@Override
	public void update(double delta)
	{
		if (!freed && isDead() && health.getTimeSinceLastDamage() >= 0.2) {
			getLevel().freeTile(getCoord());
			freed = true;
		}
	}
	
	
	/**
	 * @return entity type (used for AI targeting)
	 */
	public abstract EntityType getType();
	
	
	/**
	 * @return entity coord in level
	 */
	public Coord getCoord()
	{
		return pos.getCoord();
	}
	
	
	public void setCoord(Coord coord)
	{
		if (level != null) level.freeTile(getCoord());
		
		pos.setCoord(coord);
		
		if (level != null) level.occupyTile(getCoord());
	}
	
	
	/**
	 * Called right after the entity's health reaches zero.
	 */
	@DefaultImpl
	public void onKilled()
	{
	}
	
	
	/**
	 * @return true if dead
	 */
	public final boolean isDead()
	{
		return health.isDead();
	}
	
	
	/**
	 * @return whether this dead entity can be removed from level
	 */
	@DefaultImpl
	public boolean canRemoveCorpse()
	{
		return isDead() && health.getTimeSinceLastDamage() > despawnDelay;
	}
	
	
	/**
	 * Called after the corpse has been cleaned from level.
	 */
	@DefaultImpl
	public void onCorpseRemoved()
	{
	}
	
	
	/**
	 * Receive damage from an attacker.<br>
	 * The entity can decide whether to dodge, reduce damage etc.
	 * 
	 * @param attacker the entity attacking. Can be null for environmental
	 *            damage.
	 * @param attackStrength attack strength in health points to take
	 */
	public void receiveAttack(Entity attacker, int attackStrength)
	{
		this.lastAttacker = attacker;
		health.receiveDamage(attackStrength);
	}
	
	
	/**
	 * Set how long after being killed is the corpse elligible for removal
	 * 
	 * @param despawnDelay (secs)
	 */
	public void setDespawnDelay(double despawnDelay)
	{
		this.despawnDelay = despawnDelay;
	}
	
	
	public double getDespawnDelay()
	{
		return despawnDelay;
	}
	
	
	public abstract String getVisualName();
	
	
	@Override
	public boolean doesDelegate()
	{
		return true;
	}
	
	
	@Override
	public Collection<?> getChildClients()
	{
		return modules.values();
	}
	
	
	public Entity getLastAttacker()
	{
		return lastAttacker;
	}
	
	
	public double getLastAttackTime()
	{
		return health.getTimeSinceLastDamage();
	}
}
