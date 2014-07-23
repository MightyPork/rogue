package mightypork.rogue.world.tile.render;


import mightypork.gamecore.core.modules.App;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileRenderer;


/**
 * Tile that spans across two tiles visually (two-high)
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class OneFrameTileRenderer extends TileRenderer {
	
	private final TxQuad txq;
	
	
	public OneFrameTileRenderer(Tile tile, TxQuad txq) {
		super(tile);
		this.txq = txq;
	}
	
	
	@Override
	public void renderTile(TileRenderContext context)
	{
		App.gfx().quad(context.getRect(), txq);
	}
}
