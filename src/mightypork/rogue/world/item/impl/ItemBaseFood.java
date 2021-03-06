package mightypork.rogue.world.item.impl;


import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.ItemModel;
import mightypork.rogue.world.item.ItemType;


public abstract class ItemBaseFood extends Item {
	
	public ItemBaseFood(ItemModel model)
	{
		super(model);
	}
	
	
	@Override
	public boolean isStackable()
	{
		return true;
	}
	
	
	@Override
	public final int getAttackPoints()
	{
		return 0;
	}
	
	
	@Override
	public final ItemType getType()
	{
		return ItemType.FOOD;
	}
	
	
	@Override
	public boolean isDamageable()
	{
		return false;
	}
	
	
	@Override
	public int getMaxUses()
	{
		return 1;
	}
}
