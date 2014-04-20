package mightypork.rogue.world.item;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mightypork.rogue.world.map.TileRenderContext;
import mightypork.util.constraints.rect.proxy.RectBound;
import mightypork.util.control.timing.Animator;
import mightypork.util.control.timing.AnimatorBounce;
import mightypork.util.control.timing.Updateable;
import mightypork.util.files.ion.Ion;
import mightypork.util.files.ion.IonBundle;
import mightypork.util.files.ion.IonConstructor;
import mightypork.util.files.ion.Ionizable;
import mightypork.util.math.Easing;


public class Item implements Updateable, Ionizable {
	
	public static final short ION_MARK = 701;
	
	private transient ItemModel model;
	public transient Object modelData;
	public transient Animator anim;
	
	public int id;
	
	public boolean[] flags;
	public int[] numbers;
	
	
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
		this.anim = new AnimatorBounce(2, Easing.SINE_BOTH);
	}
	
	
	public void render(RectBound context)
	{
		model.render(this, context);
	}
	
	
	@Override
	public void save(OutputStream out) throws IOException
	{
		final IonBundle ib = new IonBundle();
		
		ib.put("id", id);
		ib.put("flags", flags);
		ib.put("numbers", numbers);
		
		Ion.writeObject(out, ib);
	}
	
	
	@Override
	public void load(InputStream in) throws IOException
	{
		final IonBundle ib = (IonBundle) Ion.readObject(in);
		
		id = ib.get("id", 0);
		flags = ib.get("flags", null);
		numbers = ib.get("numbers", null);
		
		if (id != model.id) {
			model = Items.get(id);
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
		if (anim != null) {
			anim.update(delta);
			
		}
	}
	
	
	public void renderOnTile(TileRenderContext context)
	{
		model.renderOnTile(this, context);
	}
	
}
