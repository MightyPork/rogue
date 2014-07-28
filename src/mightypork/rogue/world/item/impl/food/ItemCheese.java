package mightypork.rogue.world.item.impl.food;


import mightypork.gamecore.resources.Res;
import mightypork.rogue.world.item.ItemModel;
import mightypork.rogue.world.item.ItemRenderer;
import mightypork.rogue.world.item.impl.ItemBaseFood;
import mightypork.rogue.world.item.render.QuadItemRenderer;


public class ItemCheese extends ItemBaseFood {

	public ItemCheese(ItemModel model)
	{
		super(model);
	}


	@Override
	protected ItemRenderer makeRenderer()
	{
		return new QuadItemRenderer(this, Res.getTxQuad("item.cheese"));
	}


	@Override
	public int getFoodPoints()
	{
		return 2;
	}


	@Override
	public String getVisualName()
	{
		return "Cheese";
	}
}
