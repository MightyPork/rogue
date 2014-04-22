package mightypork.rogue.world.entity;


import mightypork.rogue.world.entity.models.EntityModel;
import mightypork.rogue.world.entity.models.PlayerModel;


/**
 * Tile registry
 * 
 * @author MightyPork
 */
public final class Entities {
	
	private static final EntityModel[] entities = new EntityModel[256];
	
	public static final EntityModel PLAYER = new PlayerModel(0);
	
	
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
}
