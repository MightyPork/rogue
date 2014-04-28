package mightypork.rogue.world.tile.tiles;


import java.io.IOException;

import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.DroppedItemRenderer;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.util.files.ion.IonInput;
import mightypork.util.files.ion.IonOutput;


public abstract class TileWithItems extends BasicTile {
	
	private DroppedItemRenderer itemRenderer = new DroppedItemRenderer();
	
	
	public TileWithItems(int id, TileRenderer renderer)
	{
		super(id, renderer);
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
	public boolean canHaveItems()
	{
		return true;
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
}
