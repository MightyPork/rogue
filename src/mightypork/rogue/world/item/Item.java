package mightypork.rogue.world.item;


import mightypork.rogue.world.Entity;
import mightypork.util.constraints.rect.proxy.RectBound;


public final class Item extends Entity<ItemData, ItemModel, RectBound> {
	
	public static final short ION_MARK = 701;
	
	
	public Item() {
		super();
	}
	
	
	public Item(ItemModel model) {
		super(model);
	}
	
	
	public Item(int id) {
		super(Items.get(id));
	}
	
	
	@Override
	protected ItemModel getModelForId(int id)
	{
		return Items.get(id);
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
}
