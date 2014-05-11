package mightypork.rogue.world.item;


import mightypork.gamecore.util.math.constraints.rect.Rect;


public abstract class ItemRenderer {
	
	protected final Item item;
	
	
	public ItemRenderer(Item item)
	{
		this.item = item;
	}
	
	
	public abstract void render(Rect r);
	
}
