package mightypork.rogue.world.entity;


import java.io.IOException;
import java.util.Collection;

import mightypork.rogue.world.entity.impl.EntityBossRat;
import mightypork.rogue.world.entity.impl.EntityBrownRat;
import mightypork.rogue.world.entity.impl.EntityGrayRat;
import mightypork.rogue.world.entity.impl.EntityPlayer;
import mightypork.utils.ion.IonInput;
import mightypork.utils.ion.IonOutput;


/**
 * Entity registry
 *
 * @author Ondřej Hruška (MightyPork)
 */
public final class Entities {

	private static final EntityModel[] entities = new EntityModel[256];

	public static final EntityModel PLAYER = new EntityModel(1, EntityPlayer.class);
	public static final EntityModel RAT_GRAY = new EntityModel(2, EntityGrayRat.class);
	public static final EntityModel RAT_BROWN = new EntityModel(3, EntityBrownRat.class);
	public static final EntityModel RAT_BOSS = new EntityModel(4, EntityBossRat.class);


	public static void register(int id, EntityModel model)
	{
		if (id < 0 || id >= entities.length) {
			throw new IllegalArgumentException("Entity model ID " + id + " is out of range.");
		}

		if (entities[id] != null) {
			throw new IllegalArgumentException("Entity model ID " + id + " already in use.");
		}

		entities[id] = model;
	}


	public static EntityModel get(int id)
	{
		final EntityModel e = entities[id];

		if (e == null) {
			throw new IllegalArgumentException("No entity model with ID " + id + ".");
		}

		return e;
	}


	public static void loadEntities(IonInput in, Collection<Entity> entities) throws IOException
	{
		entities.clear();
		while (in.hasNextEntry()) {
			entities.add(loadEntity(in));
		}
	}


	public static void saveEntities(IonOutput out, Collection<Entity> entities) throws IOException
	{
		for (final Entity entity : entities) {
			out.startEntry();
			saveEntity(out, entity);
		}

		out.endSequence();
	}


	public static Entity loadEntity(IonInput in) throws IOException
	{
		final int id = in.readIntByte();

		final EntityModel model = get(id);
		return model.loadEntity(in);
	}


	public static void saveEntity(IonOutput out, Entity entity) throws IOException
	{
		final EntityModel model = entity.getModel();

		out.writeIntByte(model.id);

		model.saveEntity(out, entity);
	}
}
