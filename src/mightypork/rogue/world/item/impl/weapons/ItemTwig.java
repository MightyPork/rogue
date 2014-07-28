package mightypork.rogue.world.item.impl.weapons;


import mightypork.gamecore.resources.Res;
import mightypork.rogue.world.item.ItemModel;
import mightypork.rogue.world.item.ItemRenderer;
import mightypork.rogue.world.item.impl.ItemBaseWeapon;
import mightypork.rogue.world.item.render.QuadItemRenderer;


public class ItemTwig extends ItemBaseWeapon {

	public ItemTwig(ItemModel model)
	{
		super(model);
	}


	@Override
	protected ItemRenderer makeRenderer()
	{
		return new QuadItemRenderer(this, Res.txQuad("item.twig"));
	}


	@Override
	public int getAttackPoints()
	{
		return 1;
	}


	@Override
	public int getMaxUses()
	{
		return 10;
	}


	@Override
	public String getVisualName()
	{
		return "Twig";
	}
}
