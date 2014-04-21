package mightypork.rogue.world.item;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mightypork.util.constraints.rect.proxy.RectBound;
import mightypork.util.files.ion.Ion;
import mightypork.util.files.ion.IonConstructor;
import mightypork.util.files.ion.Ionizable;


public class Item implements Ionizable {
	
	public static final short ION_MARK = 701;
	
	private transient ItemModel model;
	
	public int id;
	
	
	public Item(int id)
	{
		this(Items.get(id));
	}
	
	
	@IonConstructor
	public Item()
	{
	}
	
	
	public Item(ItemModel model)
	{
		this.model = model;
		this.id = model.id;
	}
	
	
	public void render(RectBound context)
	{
		model.render(this, context);
	}
	
	
	@Override
	public void save(OutputStream out) throws IOException
	{
		Ion.writeShort(out, (short) id);
	}
	
	
	@Override
	public void load(InputStream in) throws IOException
	{
		id = Ion.readShort(in);
		
		// if id changed, get new model
		if (model == null || id != model.id) model = Items.get(id);
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
}
