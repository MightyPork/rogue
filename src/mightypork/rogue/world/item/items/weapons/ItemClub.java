package mightypork.rogue.world.item.items.weapons;


import mightypork.rogue.Res;
import mightypork.rogue.world.item.ItemModel;
import mightypork.rogue.world.item.ItemRenderer;
import mightypork.rogue.world.item.items.ItemBaseWeapon;
import mightypork.rogue.world.item.render.QuadItemRenderer;


public class ItemClub extends ItemBaseWeapon {
	
	public ItemClub(ItemModel model)
	{
		super(model);
	}
	
	
	@Override
	protected ItemRenderer makeRenderer()
	{
		return new QuadItemRenderer(this, Res.txq("item.club"));
	}
	
	
	@Override
	public int getAttackPoints()
	{
		return 3;
	}
	
	
	@Override
	public int getMaxUses()
	{
		return 40;
	}
	
	
	@Override
	public String getVisualName()
	{
		return "Wooden Club";
	}
}
