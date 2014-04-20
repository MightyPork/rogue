package mightypork.rogue.world.tile;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mightypork.rogue.world.map.TileRenderContext;
import mightypork.rogue.world.structs.ItemStack;
import mightypork.util.control.timing.Animator;
import mightypork.util.control.timing.Updateable;
import mightypork.util.files.ion.Ion;
import mightypork.util.files.ion.IonConstructor;
import mightypork.util.files.ion.Ionizable;


public final class Tile implements Ionizable, Updateable {
	
	public static final short ION_MARK = 700;
	
	private transient TileModel model;
	public transient Object modelData;
	public transient Animator anim;
	
	public int id;
	
	public ItemStack items = new ItemStack();
	
	public boolean[] flags;
	public int[] numbers;
	
	
	public Tile(int id)
	{
		this(Tiles.get(id));
	}
	
	
	@IonConstructor
	public Tile()
	{
	}
	
	
	public Tile(TileModel model)
	{
		this.model = model;
		this.id = model.id;
	}
	
	
	public void render(TileRenderContext context)
	{
		model.render(context);
		
		if (!items.isEmpty()) {
			items.peek().renderOnTile(context);
		}
	}
	
	
	@Override
	public void save(OutputStream out) throws IOException
	{
		if (model.isNullTile()) throw new RuntimeException("Cannot save null tile.");
		
		Ion.writeShort(out, (short) id);
		
		byte written = 0;
		if (flags != null) written |= 1;
		if (numbers != null) written |= 2;
		if (items != null && !items.isEmpty()) written |= 4;
		Ion.writeByte(out, written);
		
		if ((written & 1) != 0) Ion.writeBooleanArray(out, flags);
		if ((written & 2) != 0) Ion.writeIntArray(out, numbers);
		if ((written & 4) != 0) Ion.writeObject(out, items);
	}
	
	
	@Override
	public void load(InputStream in) throws IOException
	{
		id = Ion.readShort(in);
		
		final byte written = Ion.readByte(in);
		
		if ((written & 1) != 0) flags = Ion.readBooleanArray(in);
		if ((written & 2) != 0) numbers = Ion.readIntArray(in);
		if ((written & 4) != 0) items = (ItemStack) Ion.readObject(in);
		
		// renew model
		if (model == null || id != model.id) {
			model = Tiles.get(id);
		}
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
	
	@Override
	public void update(double delta)
	{
		model.update(this, delta);
		if (!items.isEmpty()) {
			items.peek().update(delta);
		}
	}
	
	
	public TileModel getModel()
	{
		return model;
	}
	
}
