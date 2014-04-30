package mightypork.rogue.world.tile.renderers;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxSheet;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.rogue.Res;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.TileRenderer;


public class BasicTileRenderer extends TileRenderer {
	
	private final TxSheet sheet;
	
	
	public BasicTileRenderer(String sheetKey)
	{
		this.sheet = Res.getTxSheet(sheetKey);
	}
	
	
	@Override
	public void renderTile(TileRenderContext context)
	{
		final Rect rect = context.getRect();
		Render.quadTextured(rect, sheet.getRandomQuad(context.getTileNoise()));
	}
}
