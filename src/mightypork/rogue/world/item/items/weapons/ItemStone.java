package mightypork.rogue.world.item.items.weapons;

import mightypork.rogue.Res;
import mightypork.rogue.world.item.ItemModel;
import mightypork.rogue.world.item.ItemRenderer;
import mightypork.rogue.world.item.items.ItemBaseWeapon;
import mightypork.rogue.world.item.render.QuadItemRenderer;


public class ItemStone extends ItemBaseWeapon {

	public ItemStone(ItemModel model) {
		super(model);
	}

	@Override
	protected ItemRenderer makeRenderer()
	{
		return new QuadItemRenderer(this, Res.txq("item.stone"));
	}

	@Override
	public int getAttackPoints()
	{
		return 2;
	}
	
	@Override
	public int getMaxUses()
	{
		return 20;
	}
	
	@Override
	public String getVisualName()
	{
		return "Rock";
	}
}