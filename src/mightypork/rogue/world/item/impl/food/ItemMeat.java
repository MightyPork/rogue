package mightypork.rogue.world.item.impl.food;


import mightypork.gamecore.resources.Res;
import mightypork.rogue.world.item.ItemModel;
import mightypork.rogue.world.item.ItemRenderer;
import mightypork.rogue.world.item.impl.ItemBaseFood;
import mightypork.rogue.world.item.render.QuadItemRenderer;


public class ItemMeat extends ItemBaseFood {
	
	public ItemMeat(ItemModel model)
	{
		super(model);
	}
	
	
	@Override
	protected ItemRenderer makeRenderer()
	{
		return new QuadItemRenderer(this, Res.txQuad("item.meat"));
	}
	
	
	@Override
	public int getFoodPoints()
	{
		return 4;
	}
	
	
	@Override
	public String getVisualName()
	{
		return "Chunk Of Meat";
	}
}
