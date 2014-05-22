package mightypork.rogue.world.item.impl.weapons;


import mightypork.gamecore.resources.Res;
import mightypork.rogue.world.item.ItemModel;
import mightypork.rogue.world.item.ItemRenderer;
import mightypork.rogue.world.item.impl.ItemBaseWeapon;
import mightypork.rogue.world.item.render.QuadItemRenderer;


public class ItemSword extends ItemBaseWeapon {
	
	public ItemSword(ItemModel model)
	{
		super(model);
	}
	
	
	@Override
	protected ItemRenderer makeRenderer()
	{
		return new QuadItemRenderer(this, Res.getTxQuad("item.sword"));
	}
	
	
	@Override
	public int getAttackPoints()
	{
		return 5;
	}
	
	
	@Override
	public int getMaxUses()
	{
		return 210;
	}
	
	
	@Override
	public String getVisualName()
	{
		return "Sword";
	}
}
