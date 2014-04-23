package mightypork.rogue.world.item;


import java.io.IOException;

import mightypork.util.files.ion.IonBinary;
import mightypork.util.files.ion.IonInput;
import mightypork.util.files.ion.IonOutput;
import mightypork.util.math.constraints.rect.proxy.RectBound;


public class Item implements IonBinary {
	
	public static final short ION_MARK = 51;
	
	private ItemModel model;
	
	public int id;
	
	
	public Item(int id)
	{
		this(Items.get(id));
	}
	
	
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
	public void save(IonOutput out) throws IOException
	{
		out.writeIntByte(id);
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		id = in.readIntByte();
		
		// if id changed, get new model
		if (model == null || id != model.id) model = Items.get(id);
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
}
