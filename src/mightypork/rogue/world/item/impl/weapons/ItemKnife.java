package mightypork.rogue.world.item.impl.weapons;


import mightypork.gamecore.resources.Res;
import mightypork.rogue.world.item.ItemModel;
import mightypork.rogue.world.item.ItemRenderer;
import mightypork.rogue.world.item.impl.ItemBaseWeapon;
import mightypork.rogue.world.item.render.QuadItemRenderer;


public class ItemKnife extends ItemBaseWeapon {
	
	public ItemKnife(ItemModel model)
	{
		super(model);
	}
	
	
	@Override
	protected ItemRenderer makeRenderer()
	{
		return new QuadItemRenderer(this, Res.txQuad("item.knife"));
	}
	
	
	@Override
	public int getAttackPoints()
	{
		return 4;
	}
	
	
	@Override
	public int getMaxUses()
	{
		return 70;
	}
	
	
	@Override
	public String getVisualName()
	{
		return "Knife";
	}
}
