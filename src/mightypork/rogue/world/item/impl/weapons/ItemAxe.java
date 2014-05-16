package mightypork.rogue.world.item.impl.weapons;


import mightypork.rogue.Res;
import mightypork.rogue.world.item.ItemModel;
import mightypork.rogue.world.item.ItemRenderer;
import mightypork.rogue.world.item.impl.ItemBaseWeapon;
import mightypork.rogue.world.item.render.QuadItemRenderer;


public class ItemAxe extends ItemBaseWeapon {
	
	public ItemAxe(ItemModel model)
	{
		super(model);
	}
	
	
	@Override
	protected ItemRenderer makeRenderer()
	{
		return new QuadItemRenderer(this, Res.getTxQuad("item.axe"));
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
		return "Axe";
	}
}
