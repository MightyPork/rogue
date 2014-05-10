package mightypork.rogue.world.item.items;


import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.ItemModel;
import mightypork.rogue.world.item.ItemType;


public abstract class ItemBaseWeapon extends Item {
	
	public ItemBaseWeapon(ItemModel model)
	{
		super(model);
	}
	
	
	@Override
	public boolean isStackable()
	{
		return false;
	}
	
	
	@Override
	public int getFoodPoints()
	{
		return 0;
	}
	
	
	@Override
	public ItemType getType()
	{
		return ItemType.WEAPON;
	}
	
}
