package mightypork.rogue.world.item.impl.weapons;


import mightypork.gamecore.resources.Res;
import mightypork.rogue.world.item.ItemModel;
import mightypork.rogue.world.item.ItemRenderer;
import mightypork.rogue.world.item.impl.ItemBaseWeapon;
import mightypork.rogue.world.item.render.QuadItemRenderer;


public class ItemRock extends ItemBaseWeapon {
	
	public ItemRock(ItemModel model)
	{
		super(model);
	}
	
	
	@Override
	protected ItemRenderer makeRenderer()
	{
		return new QuadItemRenderer(this, Res.getTxQuad("item.stone"));
	}
	
	
	@Override
	public int getAttackPoints()
	{
		return 2;
	}
	
	
	@Override
	public int getMaxUses()
	{
		return 30;
	}
	
	
	@Override
	public String getVisualName()
	{
		return "Rock";
	}
}
