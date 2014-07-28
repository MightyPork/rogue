package mightypork.rogue.world.entity;


import java.io.IOException;

import mightypork.rogue.world.World;
import mightypork.utils.ion.IonDataBundle;
import mightypork.utils.ion.IonInput;
import mightypork.utils.ion.IonOutput;


/**
 * Entity model - builder
 *
 * @author Ondřej Hruška (MightyPork)
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


	/**
	 * Create entitiy without EID. EID will be assigned when the entity is added
	 * to a level.
	 *
	 * @return entity.
	 */
	public Entity createEntity()
	{
		return createEntity(-1);
	}


	public Entity loadEntity(IonInput in) throws IOException
	{
		final IonDataBundle bundle = in.readBundle();
		final Entity ent = createEntity(-1);
		ent.load(bundle);
		return ent;
	}


	public void saveEntity(IonOutput out, Entity entity) throws IOException
	{
		final IonDataBundle bundle = new IonDataBundle();
		entity.save(bundle);
		out.writeBundle(bundle);
	}
}
