package mightypork.rogue.world.tile.tiles;


import java.io.IOException;
import java.util.Stack;

import mightypork.gamecore.util.ion.IonInput;
import mightypork.gamecore.util.ion.IonOutput;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.Items;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.DroppedItemRenderer;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;


public abstract class TileWithItems extends Tile {
	
	private final DroppedItemRenderer itemRenderer = new DroppedItemRenderer();
	
	protected final Stack<Item> items = new Stack<>();
	
	
	public TileWithItems(TileModel model)
	{
		super(model);
	}
	
	
	@Override
	public void renderExtra(TileRenderContext context)
	{
		if (isExplored() && !items.isEmpty()) {
			itemRenderer.render(items.peek(), context);
		}
	}
	
	
	@Override
	public void update(double delta)
	{
		super.update(delta);
		itemRenderer.update(delta);
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		super.save(out);
		
		Items.saveItems(out, items);
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		super.load(in);
		
		Items.loadItems(in, items);
	}
	
	
	@Override
	public boolean doesCastShadow()
	{
		return false;
	}
	
	
	@Override
	public boolean isWalkable()
	{
		return true;
	}
	
	
	@Override
	public boolean dropItem(Item item)
	{
		items.push(item);
		return true;
	}
	
	
	@Override
	public Item pickItem()
	{
		return hasItem() ? items.pop() : null;
	}
	
	
	@Override
	public boolean hasItem()
	{
		return !items.isEmpty();
	}
}
