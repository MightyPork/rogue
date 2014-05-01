package mightypork.rogue.world.entity;


import java.io.IOException;

import mightypork.gamecore.util.ion.IonBundle;
import mightypork.gamecore.util.ion.IonInput;
import mightypork.gamecore.util.ion.IonOutput;
import mightypork.rogue.world.World;


/**
 * Entity model
 * 
 * @author MightyPork
 */
public final class EntityModel {
	
	/** Model ID */
	public final int id;
	public final Class<? extends Entity> tileClass;
	
	
	public EntityModel(int id, Class<? extends Entity> entity)
	{
		Entities.register(id, this);
		this.id = id;
		this.tileClass = entity;
	}
	
	
	public Entity createEntity(World world)
	{
		return createEntity(world.getNewEID());
	}
	
	
	public Entity createEntity(int eid)
	{
		try {
			return tileClass.getConstructor(EntityModel.class, int.class).newInstance(this, eid);
		} catch (final Exception e) {
			throw new RuntimeException("Could not instantiate a tile.", e);
		}
	}
	
	
	public Entity loadEntity(IonInput in) throws IOException
	{
		final IonBundle bundle = in.readBundle();
		final Entity ent = createEntity(-1);
		ent.load(bundle);
		return ent;
	}
	
	
	public void saveEntity(IonOutput out, Entity entity) throws IOException
	{
		final IonBundle bundle = new IonBundle();
		entity.save(bundle);
		out.writeBundle(bundle);
	}
}
