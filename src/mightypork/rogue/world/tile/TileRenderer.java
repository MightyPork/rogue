package mightypork.rogue.world.tile;


import mightypork.dynmath.rect.Rect;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.Res;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.math.algo.Moves;
import mightypork.gamecore.util.math.timing.Updateable;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.render.NullTileRenderer;


/**
 * Renderer for a tile; each tile has own renderer.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class TileRenderer implements Updateable {
	
	public static final TileRenderer NONE = new NullTileRenderer();
	
	private static TxQuad SH_N, SH_S, SH_E, SH_W, SH_NW, SH_NE, SH_SW, SH_SE;
	private static TxQuad UFOG_N, UFOG_S, UFOG_E, UFOG_W, UFOG_NW, UFOG_NE, UFOG_SW, UFOG_SE, UFOG_FULL;
	
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
			UFOG_FULL = Res.getTxQuad("tile.ufog.full");
			inited = true;
		}
	}
	
	
	public abstract void renderTile(TileRenderContext context);
	
	
	public void renderShadows(TileRenderContext context)
	{
		if (!shadowsComputed) {
			// no shadows computed yet
			
			shadows = 0; // reset the mask
			
			for (int i = 0; i < 8; i++) {
				final Tile t2 = context.getAdjacentTile(Moves.getSide(i));
				if (!t2.isNull() && t2.doesCastShadow()) {
					shadows |= Moves.getBit(i);
				}
			}
			
			shadowsComputed = true;
		}
		
		if (shadows == 0) return;
		final Rect rect = context.getRect();
		
		if ((shadows & Moves.BITS_NW_CORNER) == Moves.BIT_NW) Render.quadTextured(rect, SH_NW);
		if ((shadows & Moves.BIT_N) != 0) Render.quadTextured(rect, SH_N);
		if ((shadows & Moves.BITS_NE_CORNER) == Moves.BIT_NE) Render.quadTextured(rect, SH_NE);
		
		if ((shadows & Moves.BIT_W) != 0) Render.quadTextured(rect, SH_W);
		if ((shadows & Moves.BIT_E) != 0) Render.quadTextured(rect, SH_E);
		
		if ((shadows & Moves.BITS_SW_CORNER) == Moves.BIT_SW) Render.quadTextured(rect, SH_SW);
		if ((shadows & Moves.BIT_S) != 0) Render.quadTextured(rect, SH_S);
		if ((shadows & Moves.BITS_SE_CORNER) == Moves.BIT_SE) Render.quadTextured(rect, SH_SE);
	}
	
	
	@DefaultImpl
	public void renderExtra(TileRenderContext context)
	{
	}
	
	
	public void renderUnexploredFog(TileRenderContext context)
	{
		// TODO cache values, update neighbouring tiles upon "explored" flag changed.
		
		final Rect rect = context.getRect();
		
		if (!context.getTile().isExplored()) {
			Render.quadTextured(rect, UFOG_FULL);
			return;
		}
		
		byte ufog = 0;
		
		for (int i = 0; i < 8; i++) {
			final Tile t2 = context.getAdjacentTile(Moves.getSide(i));
			if (t2.isNull() || !t2.isExplored()) {
				ufog |= Moves.getBit(i);
			}
		}
		
		if (ufog == 0) return;
		
		if ((ufog & Moves.BITS_NW_CORNER) == Moves.BIT_NW) Render.quadTextured(rect, UFOG_NW);
		if ((ufog & Moves.BIT_N) != 0) Render.quadTextured(rect, UFOG_N);
		if ((ufog & Moves.BITS_NE_CORNER) == Moves.BIT_NE) Render.quadTextured(rect, UFOG_NE);
		
		if ((ufog & Moves.BIT_W) != 0) Render.quadTextured(rect, UFOG_W);
		if ((ufog & Moves.BIT_E) != 0) Render.quadTextured(rect, UFOG_E);
		
		if ((ufog & Moves.BITS_SW_CORNER) == Moves.BIT_SW) Render.quadTextured(rect, UFOG_SW);
		if ((ufog & Moves.BIT_S) != 0) Render.quadTextured(rect, UFOG_S);
		if ((ufog & Moves.BITS_SE_CORNER) == Moves.BIT_SE) Render.quadTextured(rect, UFOG_SE);
		
	}
	
	
	@Override
	public void update(double delta)
	{
	}
}
