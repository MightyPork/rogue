package mightypork.rogue.world.tile.render;


import mightypork.gamecore.core.App;
import mightypork.gamecore.graphics.textures.TxQuad;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.utils.math.constraints.rect.Rect;


/**
 * Tile that spans across two tiles visually (two-high)
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class TwoHighTileRenderer extends TileRenderer {
	
	private final TxQuad txq;
	
	
	public TwoHighTileRenderer(Tile tile, TxQuad txq)
	{
		super(tile);
		this.txq = txq;
	}
	
	
	@Override
	public void renderTile(TileRenderContext context)
	{
		final Rect rect = context.getRect();
		App.gfx().quad(rect.growUp(rect.height()), txq);
	}
}
