package mightypork.rogue.world.tile.renderers;


import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.DroppedItemRenderer;


/**
 * Renderer for a tile model, in client
 * 
 * @author MightyPork
 */
public abstract class TileRenderer {
	
	public static final TileRenderer NONE = new NullTileRenderer();
	
	private DroppedItemRenderer itemRenderer;
	
	
	/**
	 * Update tile renderer
	 * 
	 * @param delta delta time
	 */
	public void update(double delta)
	{
		if (itemRenderer != null) {
			itemRenderer.update(delta);
		}
	}
	
	
	/**
	 * Render the tile.
	 * 
	 * @param context
	 */
	public abstract void render(TileRenderContext context);
	
	
	public void renderItemOnTile(Item item, TileRenderContext context)
	{
		if (itemRenderer == null) {
			itemRenderer = new DroppedItemRenderer();
		}
		
		itemRenderer.render(item, context);
	}
}
