package mightypork.rogue.world.tile;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mightypork.util.control.timing.Animator;
import mightypork.util.control.timing.Updateable;
import mightypork.util.files.ion.Ion;
import mightypork.util.files.ion.IonBundle;
import mightypork.util.files.ion.IonConstructor;
import mightypork.util.files.ion.Ionizable;


public final class Tile implements Ionizable, Updateable {
	
	public static final short ION_MARK = 700;
	
	private transient TileModel model;
	public transient Object modelData;
	public transient Animator anim;
	
	public int id;
	
	public TileItems items;
	
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
		this.items = new TileItems();
	}
	
	
	public void render(TileRenderContext context)
	{
		model.render(this, context);
		
		if (!items.isEmpty()) {
			items.peek().renderOnTile(context);
		}
	}
	
	
	@Override
	public void save(OutputStream out) throws IOException
	{
		if (model.isNullTile()) throw new RuntimeException("Cannot save null tile.");
		
		final IonBundle ib = new IonBundle();
		
		ib.put("id", id);
		ib.put("flags", flags);
		ib.put("numbers", numbers);
		ib.put("items", items);
		
		Ion.writeObject(out, ib);
	}
	
	
	@Override
	public void load(InputStream in) throws IOException
	{
		final IonBundle ib = (IonBundle) Ion.readObject(in);
		
		id = ib.get("id", id);
		flags = ib.get("flags", flags);
		numbers = ib.get("numbers", numbers);
		items = ib.get("items", items);
		
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
		if (!items.isEmpty()) {
			items.peek().update(delta);
		}
	}
	
	
	public TileModel getModel()
	{
		return model;
	}
	
}
