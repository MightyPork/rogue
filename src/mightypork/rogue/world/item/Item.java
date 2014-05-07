package mightypork.rogue.world.item;


import java.io.IOException;

import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.ion.IonInput;
import mightypork.gamecore.util.ion.IonObjBlob;
import mightypork.gamecore.util.ion.IonOutput;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.math.constraints.rect.proxy.RectBound;


public abstract class Item implements IonObjBlob {
	
	private final ItemModel model;
	private ItemRenderer renderer;
	
	
	public Item(ItemModel model) {
		this.model = model;
	}
	
	
	public final void render(Rect rect)
	{
		if (renderer == null) {
			renderer = makeRenderer();
		}
		
		renderer.render(rect);
	};
	
	
	protected abstract ItemRenderer makeRenderer();
	
	
	@Override
	@DefaultImpl
	public void save(IonOutput out) throws IOException
	{
	}
	
	
	@Override
	@DefaultImpl
	public void load(IonInput in) throws IOException
	{
	}
	
	
	public final ItemModel getModel()
	{
		return model;
	}
}
