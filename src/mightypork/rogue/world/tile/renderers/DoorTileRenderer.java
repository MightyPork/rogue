package mightypork.rogue.world.tile.renderers;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxSheet;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.rogue.Res;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileRenderer;


public class DoorTileRenderer extends TileRenderer {
	
	private final TxSheet closed;
	private final TxSheet open;
	
	
	public DoorTileRenderer(String quadClosed, String quadOpen)
	{
		this.closed = Res.getTxSheet(quadClosed);
		this.open = Res.getTxSheet(quadOpen);
	}
	
	
	@Override
	public void renderTile(TileRenderContext context)
	{
		final Tile t = context.getTile();
		final Rect rect = context.getRect();
		
		if (t.isOccupied()) {
			Render.quadTextured(rect, open.getRandomQuad(context.getTileNoise()));
		} else {
			Render.quadTextured(rect, closed.getRandomQuad(context.getTileNoise()));
		}
	}
	
}
