package mightypork.rogue.world.tile.renderers;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.render.textures.TxQuad;
import mightypork.rogue.Res;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileRenderData;
import mightypork.util.math.constraints.rect.Rect;


public class FloorRenderer extends BasicTileRenderer {
	
	private static boolean inited;
	private static TxQuad SH_N, SH_S, SH_E, SH_W, SH_NW, SH_NE, SH_SW, SH_SE;
	
	
	public FloorRenderer(String sheetKey)
	{
		super(sheetKey);
		
		if (!inited) {
			SH_N = Res.getTxQuad("tile.shadow.n");
			SH_S = Res.getTxQuad("tile.shadow.s");
			SH_E = Res.getTxQuad("tile.shadow.e");
			SH_W = Res.getTxQuad("tile.shadow.w");
			SH_NW = Res.getTxQuad("tile.shadow.nw");
			SH_NE = Res.getTxQuad("tile.shadow.ne");
			SH_SW = Res.getTxQuad("tile.shadow.sw");
			SH_SE = Res.getTxQuad("tile.shadow.se");
		}
	}
	
	
	@Override
	public void render(TileRenderContext context)
	{
		super.render(context);
		
		final Rect rect = context.getRect();
		
		final TileRenderData trd = context.getTile().renderData;
		
		if (!trd.shadowsComputed) {
			// no shadows computed yet
			
			trd.shadows = 0; // reset the mask
			
			int move = 0;
			for (int y = -1; y <= 1; y++) {
				for (int x = -1; x <= 1; x++) {
					if (x == 0 && y == 0) continue;
					
					final Tile t2 = context.getAdjacentTile(x, y);
					
					if (t2.doesCastShadow()) {
						trd.shadows |= 1 << move;
					}
					
					move++;
				}
			}
			
			trd.shadowsComputed = true;
		}
		
		if (trd.shadows == 0) return;
		
		if ((trd.shadows & (1 << 0)) != 0) Render.quadTextured(rect, SH_NW);
		if ((trd.shadows & (1 << 1)) != 0) Render.quadTextured(rect, SH_N);
		if ((trd.shadows & (1 << 2)) != 0) Render.quadTextured(rect, SH_NE);
		
		if ((trd.shadows & (1 << 3)) != 0) Render.quadTextured(rect, SH_W);
		if ((trd.shadows & (1 << 4)) != 0) Render.quadTextured(rect, SH_E);
		
		if ((trd.shadows & (1 << 5)) != 0) Render.quadTextured(rect, SH_SW);
		if ((trd.shadows & (1 << 6)) != 0) Render.quadTextured(rect, SH_S);
		if ((trd.shadows & (1 << 7)) != 0) Render.quadTextured(rect, SH_SE);
	}
}
