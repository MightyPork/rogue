package mightypork.rogue.world.tile.tiles;


import java.io.IOException;
import java.util.Stack;

import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.DroppedItemRenderer;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.util.files.ion.IonInput;
import mightypork.util.files.ion.IonOutput;


public abstract class GroundTile extends Tile {
	
	private final DroppedItemRenderer itemRenderer = new DroppedItemRenderer();
	
	protected final Stack<Item> items = new Stack<>();
	
	
	public GroundTile(TileModel model, TileRenderer renderer)
	{
		super(model, renderer);
	}
	
	
	@Override
	public void renderExtra(TileRenderContext context)
	{
		if (!items.isEmpty()) {
			itemRenderer.render(items.peek(), context);
		}
	}
	
	
	@Override
	public void update(Level level, double delta)
	{
		itemRenderer.update(delta);
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		super.save(out);
		
		out.writeSequence(items);
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		super.load(in);
		
		in.readSequence(items);
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
