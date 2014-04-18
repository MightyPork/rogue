package mightypork.rogue.world;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mightypork.util.constraints.rect.proxy.RectBound;
import mightypork.util.control.timing.Updateable;
import mightypork.util.files.ion.Ion;
import mightypork.util.files.ion.Ionizable;


/**
 * Abstract entity
 * 
 * @author MightyPork
 * @param <D> Data object class
 * @param <M> Model class
 * @param <R> Render context class
 */
public abstract class Entity<D, M extends EntityModel<D, R>, R extends RectBound> implements Ionizable, Updateable {
	
	protected M model;
	protected D data;
	
	
	/**
	 * Used by Ion for loading.
	 */
	public Entity() {
	}
	
	
	/**
	 * Create from model
	 * 
	 * @param model model
	 */
	public Entity(M model) {
		setModel(model);
	}
	
	
	@Override
	public final void loadFrom(InputStream in) throws IOException
	{
		final int id = Ion.readInt(in);
		setModel(id);
		model.load(data, in); // load saved data
	}
	
	
	private void initData()
	{
		data = model.createData();
	}
	
	
	/**
	 * @return entity model
	 */
	public final M getModel()
	{
		return model;
	}
	
	
	/**
	 * Assign a model.
	 * 
	 * @param id model id
	 */
	public final void setModel(int id)
	{
		setModel(getModelForId(id));
	}
	
	
	/**
	 * Assign a model.
	 * 
	 * @param model model
	 */
	public final void setModel(M model)
	{
		this.model = model;
		initData();
	}
	
	
	@Override
	public final void saveTo(OutputStream out) throws IOException
	{
		Ion.writeInt(out, model.getId());
		model.save(data, out);
	}
	
	
	public void render(R context)
	{
		model.render(data, context);
	}
	
	
	@Override
	public void update(double delta)
	{
		model.update(data, delta);
	}
	
	
	/**
	 * Get model for ID
	 * 
	 * @param id id
	 * @return model for the ID
	 */
	protected abstract M getModelForId(int id);
	
}
