package mightypork.rogue.world.tile.render;


import mightypork.gamecore.core.App;
import mightypork.gamecore.graphics.textures.TxSheet;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.utils.math.constraints.rect.Rect;


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
		App.gfx().quad(rect, sheet.getRandomQuad(context.getTileNoise()));
	}
}
