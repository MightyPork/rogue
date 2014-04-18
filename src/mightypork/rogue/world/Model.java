package mightypork.rogue.world;


import java.io.InputStream;
import java.io.OutputStream;

import mightypork.util.constraints.rect.proxy.RectBound;


public interface Model<DATA, RCTX> {
	
	/**
	 * Get the id.
	 * 
	 * @return id
	 */
	int getId();
	
	
	/**
	 * Populate an entity with data for this model
	 * 
	 * @param entity entity in world
	 */
	void create(DATA entity);
	
	
	/**
	 * Load entity data from a binary stream.
	 * 
	 * @param entity item to load
	 * @param in input stream
	 */
	void load(DATA entity, InputStream in);
	
	
	/**
	 * Save entity data to a binary stream.
	 * 
	 * @param entity entity to save
	 * @param out output stream
	 */
	void save(DATA entity, OutputStream out);
	
	
	/**
	 * Render according to given context.
	 * 
	 * @param entity data object
	 * @param context rendering context
	 */
	void render(DATA entity, RCTX context);
	
	
	/**
	 * Update the entity (animation, decay etc)
	 * 
	 * @param entity data object
	 * @param delta delta time
	 */
	void update(DATA entity, double delta);
	
}
