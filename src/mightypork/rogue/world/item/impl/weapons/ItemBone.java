package mightypork.rogue.world.item.impl.weapons;


import mightypork.rogue.Res;
import mightypork.rogue.world.item.ItemModel;
import mightypork.rogue.world.item.ItemRenderer;
import mightypork.rogue.world.item.impl.ItemBaseWeapon;
import mightypork.rogue.world.item.render.QuadItemRenderer;


public class ItemBone extends ItemBaseWeapon {
	
	public ItemBone(ItemModel model)
	{
		super(model);
	}
	
	
	@Override
	protected ItemRenderer makeRenderer()
	{
		return new QuadItemRenderer(this, Res.txq("item.bone"));
	}
	
	
	@Override
	public int getAttackPoints()
	{
		return 1;
	}
	
	
	@Override
	public int getMaxUses()
	{
		return 15;
	}
	
	
	@Override
	public String getVisualName()
	{
		return "Bone";
	}
}
