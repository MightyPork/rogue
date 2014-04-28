package mightypork.rogue.world.tile;

import mightypork.gamecore.render.Render;
import mightypork.gamecore.render.textures.TxQuad;
import mightypork.rogue.Res;
import mightypork.rogue.world.Sides;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.util.math.constraints.rect.Rect;


/**
 * Renderer for a tile model, in client
 * 
 * @author MightyPork
 */
public abstract class TileRenderer {
	
	private static TxQuad SH_N, SH_S, SH_E, SH_W, SH_NW, SH_NE, SH_SW, SH_SE;
	private static TxQuad UFOG_N, UFOG_S, UFOG_E, UFOG_W, UFOG_NW, UFOG_NE, UFOG_SW, UFOG_SE;
	
	
	private static boolean inited;
	
	
	public TileRenderer() {
		if (!inited) {
			SH_N = Res.getTxQuad("tile.shadow.n");
			SH_S = Res.getTxQuad("tile.shadow.s");
			SH_E = Res.getTxQuad("tile.shadow.e");
			SH_W = Res.getTxQuad("tile.shadow.w");
			SH_NW = Res.getTxQuad("tile.shadow.nw");
			SH_NE = Res.getTxQuad("tile.shadow.ne");
			SH_SW = Res.getTxQuad("tile.shadow.sw");
			SH_SE = Res.getTxQuad("tile.shadow.se");
			
			UFOG_N = Res.getTxQuad("tile.ufog.n");
			UFOG_S = Res.getTxQuad("tile.ufog.s");
			UFOG_E = Res.getTxQuad("tile.ufog.e");
			UFOG_W = Res.getTxQuad("tile.ufog.w");
			UFOG_NW = Res.getTxQuad("tile.ufog.nw");
			UFOG_NE = Res.getTxQuad("tile.ufog.ne");
			UFOG_SW = Res.getTxQuad("tile.ufog.sw");
			UFOG_SE = Res.getTxQuad("tile.ufog.se");
		}
	}
	
	
	public abstract void renderTile(TileRenderContext context);
	
	
	public void renderShadows(TileRenderContext context)
	{
		final TileRenderData trd = context.getTile().renderData;
		
		if (!trd.shadowsComputed) {
			// no shadows computed yet
			
			trd.shadows = 0; // reset the mask
			
			for (int i = 0; i < 8; i++) {
				final Tile t2 = context.getAdjacentTile(Sides.get(i));
				if (!t2.isNull() && t2.doesCastShadow()) {
					trd.shadows |= Sides.bit(i);
				}
			}
			
			trd.shadowsComputed = true;
		}
		
		if (trd.shadows == 0) return;
		final Rect rect = context.getRect();
		
		if ((trd.shadows & Sides.NW) != 0) Render.quadTextured(rect, SH_NW);
		if ((trd.shadows & Sides.N) != 0) Render.quadTextured(rect, SH_N);
		if ((trd.shadows & Sides.NE) != 0) Render.quadTextured(rect, SH_NE);
		
		if ((trd.shadows & Sides.W) != 0) Render.quadTextured(rect, SH_W);
		if ((trd.shadows & Sides.E) != 0) Render.quadTextured(rect, SH_E);
		
		if ((trd.shadows & Sides.SW) != 0) Render.quadTextured(rect, SH_SW);
		if ((trd.shadows & Sides.S) != 0) Render.quadTextured(rect, SH_S);
		if ((trd.shadows & Sides.SE) != 0) Render.quadTextured(rect, SH_SE);
	}
	
	
	public void renderUnexploredFog(TileRenderContext context)
	{
		// TODO cache in tile, update neighbouring tiles upon "explored" flag changed.
		
		byte ufog = 0;
		
		ufog = 0; // reset the mask
		
		for (int i = 0; i < 8; i++) {
			final Tile t2 = context.getAdjacentTile(Sides.get(i));
			if (t2.isNull() || !t2.isExplored()) {
				ufog |= Sides.bit(i);
			}
		}
		
		if (ufog == 0) return;
		
		final Rect rect = context.getRect();	
		if ((ufog & Sides.NW_CORNER) == Sides.NW) Render.quadTextured(rect, UFOG_NW);
		if ((ufog & Sides.N) != 0) Render.quadTextured(rect, UFOG_N);
		if ((ufog & Sides.NE_CORNER) == Sides.NE) Render.quadTextured(rect, UFOG_NE);
		
		if ((ufog & Sides.W) != 0) Render.quadTextured(rect, UFOG_W);
		if ((ufog & Sides.E) != 0) Render.quadTextured(rect, UFOG_E);
		
		if ((ufog & Sides.SW_CORNER) == Sides.SW)  Render.quadTextured(rect, UFOG_SW);
		if ((ufog & Sides.S) != 0) Render.quadTextured(rect, UFOG_S);
		if ((ufog & Sides.SE_CORNER) == Sides.SE) Render.quadTextured(rect, UFOG_SE);
	}
}
