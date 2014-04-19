package mightypork.rogue.world.item;


import mightypork.rogue.world.tile.TileRenderContext;
import mightypork.util.annotations.DefaultImpl;
import mightypork.util.constraints.num.proxy.NumBoundAdapter;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.proxy.RectBound;
import mightypork.util.constraints.rect.proxy.RectBoundAdapter;


public abstract class ItemModel {
	
	public final int id;
	
	private final RectBoundAdapter tileRect = new RectBoundAdapter();
	private final NumBoundAdapter yOffset = new NumBoundAdapter();
	
	private final Rect itemRect = tileRect.shrink(tileRect.height().perc(10)).moveY(yOffset.neg());
	
	
	public ItemModel(int id)
	{
		Items.register(id, this);
		this.id = id;
	}
	
	
	/**
	 * @return new tile with this model
	 */
	@DefaultImpl
	public Item create()
	{
		return new Item(this);
	}
	
	
	public abstract void render(Item item, RectBound context);
	
	
	public void renderOnTile(Item item, TileRenderContext context)
	{
		tileRect.setRect(context.getRect());
		yOffset.setNum(item.anim);
		
		render(item, itemRect);
	}
	
}
