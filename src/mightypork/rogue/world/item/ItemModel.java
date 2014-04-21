package mightypork.rogue.world.item;


import mightypork.rogue.world.map.TileRenderContext;
import mightypork.util.annotations.DefaultImpl;
import mightypork.util.constraints.num.proxy.NumBoundAdapter;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.proxy.RectBound;
import mightypork.util.constraints.rect.proxy.RectBoundAdapter;


public abstract class ItemModel {
	
	public final int id;
	
	
	public ItemModel(int id)
	{
		Items.register(id, this);
		this.id = id;
	}
	
	
	/**
	 * @return new tile with this model
	 */
	@DefaultImpl
	public Item create()
	{
		return new Item(this);
	}
	
	
	public abstract void render(Item item, RectBound context);
	
}
