package mightypork.rogue.world.item.impl.food;


import mightypork.rogue.Res;
import mightypork.rogue.world.item.ItemModel;
import mightypork.rogue.world.item.ItemRenderer;
import mightypork.rogue.world.item.impl.ItemBaseFood;
import mightypork.rogue.world.item.render.QuadItemRenderer;


public class ItemSandwich extends ItemBaseFood {
	
	public ItemSandwich(ItemModel model)
	{
		super(model);
	}
	
	
	@Override
	protected ItemRenderer makeRenderer()
	{
		return new QuadItemRenderer(this, Res.txq("item.sandwich"));
	}
	
	
	@Override
	public int getFoodPoints()
	{
		return 8;
	}
	
	
	@Override
	public String getVisualName()
	{
		return "Sandwich";
	}
}
