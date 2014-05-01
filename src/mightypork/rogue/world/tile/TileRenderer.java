package mightypork.rogue.world.tile;


import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.gamecore.util.math.algo.Sides;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.rogue.Res;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.renderers.NullTileRenderer;


/**
 * Renderer for a tile; each tile has own renderer.
 * 
 * @author MightyPork
 */
public abstract class TileRenderer implements Updateable {
	
	public static final TileRenderer NONE = new NullTileRenderer();
	
	private static TxQuad SH_N, SH_S, SH_E, SH_W, SH_NW, SH_NE, SH_SW, SH_SE;
	private static TxQuad UFOG_N, UFOG_S, UFOG_E, UFOG_W, UFOG_NW, UFOG_NE, UFOG_SW, UFOG_SE;
	
	private static boolean inited;
	
	// data
	
	public byte shadows;
	public boolean shadowsComputed;
	
	protected final Tile tile;
	
	
	protected Tile getTile()
	{
		return tile;
	}
	
	
	public TileRenderer(Tile tile)
	{
		this.tile = tile;
		
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
		if (!shadowsComputed) {
			// no shadows computed yet
			
			shadows = 0; // reset the mask
			
			for (int i = 0; i < 8; i++) {
				final Tile t2 = context.getAdjacentTile(Sides.get(i));
				if (!t2.isNull() && t2.doesCastShadow()) {
					shadows |= Sides.bit(i);
				}
			}
			
			shadowsComputed = true;
		}
		
		if (shadows == 0) return;
		final Rect rect = context.getRect();
		
		if ((shadows & Sides.NW_CORNER) == Sides.MASK_NW) Render.quadTextured(rect, SH_NW);
		if ((shadows & Sides.MASK_N) != 0) Render.quadTextured(rect, SH_N);
		if ((shadows & Sides.NE_CORNER) == Sides.MASK_NE) Render.quadTextured(rect, SH_NE);
		
		if ((shadows & Sides.MASK_W) != 0) Render.quadTextured(rect, SH_W);
		if ((shadows & Sides.MASK_E) != 0) Render.quadTextured(rect, SH_E);
		
		if ((shadows & Sides.SW_CORNER) == Sides.MASK_SW) Render.quadTextured(rect, SH_SW);
		if ((shadows & Sides.MASK_S) != 0) Render.quadTextured(rect, SH_S);
		if ((shadows & Sides.SE_CORNER) == Sides.MASK_SE) Render.quadTextured(rect, SH_SE);
	}
	
	
	public void renderUnexploredFog(TileRenderContext context)
	{
		// TODO cache values, update neighbouring tiles upon "explored" flag changed.
		
		byte ufog = 0;
		
		for (int i = 0; i < 8; i++) {
			final Tile t2 = context.getAdjacentTile(Sides.get(i));
			if (t2.isNull() || !t2.isExplored()) {
				ufog |= Sides.bit(i);
			}
		}
		
		if (ufog == 0) return;
		
		final Rect rect = context.getRect();
		if ((ufog & Sides.NW_CORNER) == Sides.MASK_NW) Render.quadTextured(rect, UFOG_NW);
		if ((ufog & Sides.MASK_N) != 0) Render.quadTextured(rect, UFOG_N);
		if ((ufog & Sides.NE_CORNER) == Sides.MASK_NE) Render.quadTextured(rect, UFOG_NE);
		
		if ((ufog & Sides.MASK_W) != 0) Render.quadTextured(rect, UFOG_W);
		if ((ufog & Sides.MASK_E) != 0) Render.quadTextured(rect, UFOG_E);
		
		if ((ufog & Sides.SW_CORNER) == Sides.MASK_SW) Render.quadTextured(rect, UFOG_SW);
		if ((ufog & Sides.MASK_S) != 0) Render.quadTextured(rect, UFOG_S);
		if ((ufog & Sides.SE_CORNER) == Sides.MASK_SE) Render.quadTextured(rect, UFOG_SE);
	}
	
	
	@Override
	public void update(double delta)
	{
	}
}
