package mightypork.rogue.world.tile.renderers;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.render.textures.TxQuad;
import mightypork.rogue.Res;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.util.math.constraints.rect.Rect;


public class DoorRenderer extends TileRenderer {
	
	private final TxQuad closed;
	private final TxQuad open;
	
	
	public DoorRenderer(String quadClosed, String quadOpen)
	{
		this.closed = Res.getTxQuad(quadClosed);
		this.open = Res.getTxQuad(quadOpen);
	}
	
	
	@Override
	public void renderTile(TileRenderContext context)
	{
		final Tile t = context.getTile();
		final Rect rect = context.getRect();
		
		if (t.isOccupied()) {
			Render.quadTextured(rect, open);
		} else {
			Render.quadTextured(rect, closed);
		}
	}
	
}
