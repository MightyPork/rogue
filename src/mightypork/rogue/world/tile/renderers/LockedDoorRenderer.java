package mightypork.rogue.world.tile.renderers;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxSheet;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.rogue.Res;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.Tile;


public class LockedDoorRenderer extends DoorRenderer {
	
	private final TxSheet locked;
	
	
	public LockedDoorRenderer(String sheetLocked, String sheetClosed, String sheetOpen)
	{
		super(sheetClosed, sheetOpen);
		this.locked = Res.getTxSheet(sheetLocked);
	}
	
	
	@Override
	public void renderTile(TileRenderContext context)
	{
		final Tile t = context.getTile();
		
		if (!t.isWalkable()) {
			final Rect rect = context.getRect();
			Render.quadTextured(rect, locked.getRandomQuad(context.getTileNoise()));
		} else {
			super.renderTile(context);
		}
	}
	
}
