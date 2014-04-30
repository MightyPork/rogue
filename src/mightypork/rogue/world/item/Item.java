package mightypork.rogue.world.item;


import java.io.IOException;

import mightypork.gamecore.util.ion.IonInput;
import mightypork.gamecore.util.ion.IonObjBinary;
import mightypork.gamecore.util.ion.IonOutput;
import mightypork.gamecore.util.math.constraints.rect.proxy.RectBound;


public class Item implements IonObjBinary {
	
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
