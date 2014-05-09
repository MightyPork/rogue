package mightypork.rogue.world.item.items;

import mightypork.rogue.Res;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.ItemModel;
import mightypork.rogue.world.item.ItemRenderer;
import mightypork.rogue.world.item.rendr.QuadItemRenderer;


public class ItemMeat extends Item {

	public ItemMeat(ItemModel model) {
		super(model);
	}

	@Override
	protected ItemRenderer makeRenderer()
	{
		return new QuadItemRenderer(Res.getTxQuad("item.meat"));
	}
	
}
