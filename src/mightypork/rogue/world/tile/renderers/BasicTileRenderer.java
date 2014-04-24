package mightypork.rogue.world.tile.renderers;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.render.textures.TxSheet;
import mightypork.rogue.Res;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.util.math.constraints.rect.Rect;


public class BasicTileRenderer extends TileRenderer {
	
	private final TxSheet sheet;
	
	
	public BasicTileRenderer(String sheetKey)
	{
		this.sheet = Res.getTxSheet(sheetKey);
	}
	
	
	@Override
	public void render(TileRenderContext context)
	{
		final Rect rect = context.getRect();
		Render.quadTextured(rect, sheet.getRandomQuad(context.getTileNoise()));
	}
}
