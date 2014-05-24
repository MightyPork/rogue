package mightypork.rogue.world.tile.render;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileRenderer;


/**
 * Tile that spans across two tiles visually (two-high)
 * 
 * @author Ondřej Hruška
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
		Render.quadTextured(rect.growUp(rect.height()), txq);
	}
}
