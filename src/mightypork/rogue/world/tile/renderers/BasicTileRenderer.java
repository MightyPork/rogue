package mightypork.rogue.world.tile.renderers;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.render.textures.TxSheet;
import mightypork.rogue.Res;
import mightypork.rogue.world.level.render.TileRenderContext;


public class BasicTileRenderer extends TileRenderer {
	
	private final TxSheet sheet;
	
	
	public BasicTileRenderer(String sheetKey)
	{
		this.sheet = Res.getTxSheet(sheetKey);
	}
	
	
	@Override
	public void render(TileRenderContext context)
	{
		Render.quadTextured(context.getRect(), sheet.getRandomQuad(context.getTileNoise()));
	}
}
