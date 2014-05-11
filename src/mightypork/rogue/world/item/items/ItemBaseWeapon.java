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
	public final int getFoodPoints()
	{
		return 0;
	}
	
	
	@Override
	public final ItemType getType()
	{
		return ItemType.WEAPON;
	}
	
	
	@Override
	public boolean isDamageable()
	{
		return true;
	}
	
}
