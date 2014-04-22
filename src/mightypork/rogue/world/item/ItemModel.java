package mightypork.rogue.world.item;


import mightypork.util.annotations.DefaultImpl;
import mightypork.util.constraints.rect.proxy.RectBound;


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
