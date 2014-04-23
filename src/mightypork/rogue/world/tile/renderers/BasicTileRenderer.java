package mightypork.rogue.world.tile.renderers;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.render.textures.TxQuad;
import mightypork.gamecore.render.textures.TxSheet;
import mightypork.rogue.Res;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.Tile;
import mightypork.util.math.constraints.rect.Rect;


public class BasicTileRenderer extends TileRenderer {
	
	private final TxSheet sheet;
	
	private static boolean inited;
	private static TxQuad SH_N, SH_S, SH_E, SH_W, SH_NW, SH_NE, SH_SW, SH_SE;
	
	
	public BasicTileRenderer(String sheetKey)
	{
		this.sheet = Res.getTxSheet(sheetKey);
		
		if (!inited) {
			SH_N = Res.getTxQuad("tile16.shadow.n");
			SH_S = Res.getTxQuad("tile16.shadow.s");
			SH_E = Res.getTxQuad("tile16.shadow.e");
			SH_W = Res.getTxQuad("tile16.shadow.w");
			SH_NW = Res.getTxQuad("tile16.shadow.nw");
			SH_NE = Res.getTxQuad("tile16.shadow.ne");
			SH_SW = Res.getTxQuad("tile16.shadow.sw");
			SH_SE = Res.getTxQuad("tile16.shadow.se");
		}
	}
	
	
	@Override
	public void render(TileRenderContext context)
	{
		final Rect rect = context.getRect();
		Render.quadTextured(rect, sheet.getRandomQuad(context.getTileNoise()));
		
		final Tile t = context.getTile();
		
		if (t.getModel().doesCastShadow()) return; // no shadows for wall
			
		Tile t2;
		
		if (!t.shadowsComputed) {
			// no shadows computed yet
			
			t.shadows = 0; // reset the mask
			
			int move = 0;
			for (int y = -1; y <= 1; y++) {
				for (int x = -1; x <= 1; x++) {
					if (x == 0 && y == 0) continue;
					
					t2 = context.getAdjacentTile(x, y);
					
					if (t2.getModel().doesCastShadow()) {
						t.shadows |= 1 << move;
					}
					
					move++;
				}
			}
			
			t.shadowsComputed = true;
		}
		
		if (t.shadows == 0) return;
		
		if ((t.shadows & (1 << 0)) != 0) Render.quadTextured(rect, SH_NW);
		if ((t.shadows & (1 << 1)) != 0) Render.quadTextured(rect, SH_N);
		if ((t.shadows & (1 << 2)) != 0) Render.quadTextured(rect, SH_NE);
		
		if ((t.shadows & (1 << 3)) != 0) Render.quadTextured(rect, SH_W);
		if ((t.shadows & (1 << 4)) != 0) Render.quadTextured(rect, SH_E);
		
		if ((t.shadows & (1 << 5)) != 0) Render.quadTextured(rect, SH_SW);
		if ((t.shadows & (1 << 6)) != 0) Render.quadTextured(rect, SH_S);
		if ((t.shadows & (1 << 7)) != 0) Render.quadTextured(rect, SH_SE);
	}
}
