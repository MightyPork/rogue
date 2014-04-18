package mightypork.rogue.world;


import java.io.InputStream;
import java.io.OutputStream;

import mightypork.util.annotations.DefaultImpl;
import mightypork.util.constraints.rect.proxy.RectBound;


/**
 * Entity model. Provides concrete implementation to an entity, working with
 * it's data object.
 * 
 * @author MightyPork
 * @param <D> Data object class
 * @param <R> Render context class
 */
public abstract class EntityModel<D, R extends RectBound> {
	
	/** Model id */
	private final int id;
	
	
	/**
	 * Create a model. The caller must then register this instance in a Model
	 * registry for the particular entity type.
	 * 
	 * @param id model id
	 */
	public EntityModel(int id) {
		this.id = id;
	}
	
	
	/**
	 * Get the model id.
	 * 
	 * @return id
	 */
	public final int getId()
	{
		return id;
	}
	
	
	/**
	 * Create a data object and populate it with default values.
	 * 
	 * @return data object
	 */
	public abstract D createData();
	
	
	/**
	 * Load an entity from ION input stream.
	 * 
	 * @param data data to load
	 * @param in input stream
	 */
	public abstract void load(D data, InputStream in);
	
	
	/**
	 * Save an entity to ION output stream.
	 * 
	 * @param data data to save
	 * @param out output stream
	 */
	@DefaultImpl
	public abstract void save(D data, OutputStream out);
	
	
	/**
	 * Render the item according to given context.
	 * 
	 * @param data rendered item
	 * @param context rendering context
	 */
	public abstract void render(D data, R context);
	
	
	/**
	 * Update the item (animation, decay etc)
	 * 
	 * @param item item to update
	 * @param delta delta time
	 */
	@DefaultImpl
	public abstract void update(D item, double delta);
	
}
