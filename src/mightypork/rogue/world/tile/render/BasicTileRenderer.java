package mightypork.rogue.world.tile.render;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxSheet;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileRenderer;


public class BasicTileRenderer extends TileRenderer {
	
	private final TxSheet sheet;
	
	
	public BasicTileRenderer(Tile tile, TxSheet sheet)
	{
		super(tile);
		this.sheet = sheet;
	}
	
	
	@Override
	public void renderTile(TileRenderContext context)
	{
		final Rect rect = context.getRect();
		Render.quadTextured(rect, sheet.getRandomQuad(context.getTileNoise()));
	}
}
