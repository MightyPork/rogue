package mightypork.rogue.world.item.items.weapons;


import mightypork.rogue.Res;
import mightypork.rogue.world.item.ItemModel;
import mightypork.rogue.world.item.ItemRenderer;
import mightypork.rogue.world.item.items.ItemBaseWeapon;
import mightypork.rogue.world.item.render.QuadItemRenderer;


public class ItemSword extends ItemBaseWeapon {
	
	public ItemSword(ItemModel model)
	{
		super(model);
	}
	
	
	@Override
	protected ItemRenderer makeRenderer()
	{
		return new QuadItemRenderer(this, Res.txq("item.sword"));
	}
	
	
	@Override
	public int getAttackPoints()
	{
		return 6;
	}
	
	
	@Override
	public int getMaxUses()
	{
		return 100;
	}
	
	
	@Override
	public String getVisualName()
	{
		return "Sword";
	}
}
