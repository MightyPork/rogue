package mightypork.rogue.world;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mightypork.util.constraints.rect.proxy.RectBound;
import mightypork.util.control.timing.Updateable;
import mightypork.util.files.ion.BinaryUtils;
import mightypork.util.files.ion.Ionizable;


public class ItemData implements Ionizable, Updateable {
	
	public static final short ION_MARK = 701;
	
	private Model<ItemData, RectBound> model;
	
	// data fields for models to use
	public Object data;
	public boolean[] flags;
	public int[] ints;
	
	
	/**
	 * Create from model id
	 * 
	 * @param id model id
	 */
	public ItemData(int id) {
		this(Items.get(id));
	}
	
	
	/**
	 * Create from model
	 * 
	 * @param model model
	 */
	public ItemData(Model<ItemData, RectBound> model) {
		this.model = model;
		model.create(this);
	}
	
	
	/**
	 * Create without model. Model will be read from ION input stream.
	 */
	public ItemData() {
	}
	
	
	@Override
	public void loadFrom(InputStream in) throws IOException
	{
		final int id = BinaryUtils.readInt(in);
		model = Items.get(id);
		model.load(this, in);
	}
	
	
	@Override
	public void saveTo(OutputStream out) throws IOException
	{
		BinaryUtils.writeInt(out, model.getId());
		model.save(this, out);
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
	
	public void render(RectBound context)
	{
		model.render(this, context);
	}
	
	
	@Override
	public void update(double delta)
	{
		model.update(this, delta);
	}
}
