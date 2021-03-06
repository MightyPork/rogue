package mightypork.rogue.world.tile;


import mightypork.gamecore.core.App;
import mightypork.gamecore.graphics.GraphicsModule;
import mightypork.gamecore.graphics.textures.TxQuad;
import mightypork.gamecore.resources.Res;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.render.NullTileRenderer;
import mightypork.utils.annotations.Stub;
import mightypork.utils.interfaces.Updateable;
import mightypork.utils.math.algo.Moves;
import mightypork.utils.math.constraints.rect.Rect;


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
			SH_N = Res.txQuad("tile.shadow.n");
			SH_S = Res.txQuad("tile.shadow.s");
			SH_E = Res.txQuad("tile.shadow.e");
			SH_W = Res.txQuad("tile.shadow.w");
			SH_NW = Res.txQuad("tile.shadow.nw");
			SH_NE = Res.txQuad("tile.shadow.ne");
			SH_SW = Res.txQuad("tile.shadow.sw");
			SH_SE = Res.txQuad("tile.shadow.se");
			
			UFOG_N = Res.txQuad("tile.ufog.n");
			UFOG_S = Res.txQuad("tile.ufog.s");
			UFOG_E = Res.txQuad("tile.ufog.e");
			UFOG_W = Res.txQuad("tile.ufog.w");
			UFOG_NW = Res.txQuad("tile.ufog.nw");
			UFOG_NE = Res.txQuad("tile.ufog.ne");
			UFOG_SW = Res.txQuad("tile.ufog.sw");
			UFOG_SE = Res.txQuad("tile.ufog.se");
			UFOG_FULL = Res.txQuad("tile.ufog.full");
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
		
		final GraphicsModule gfx = App.gfx();
		
		if ((shadows & Moves.BITS_NW_CORNER) == Moves.BIT_NW) gfx.quad(rect, SH_NW);
		if ((shadows & Moves.BIT_N) != 0) gfx.quad(rect, SH_N);
		if ((shadows & Moves.BITS_NE_CORNER) == Moves.BIT_NE) gfx.quad(rect, SH_NE);
		
		if ((shadows & Moves.BIT_W) != 0) gfx.quad(rect, SH_W);
		if ((shadows & Moves.BIT_E) != 0) gfx.quad(rect, SH_E);
		
		if ((shadows & Moves.BITS_SW_CORNER) == Moves.BIT_SW) gfx.quad(rect, SH_SW);
		if ((shadows & Moves.BIT_S) != 0) gfx.quad(rect, SH_S);
		if ((shadows & Moves.BITS_SE_CORNER) == Moves.BIT_SE) gfx.quad(rect, SH_SE);
	}
	
	
	@Stub
	public void renderExtra(TileRenderContext context)
	{
	}
	
	
	public void renderUnexploredFog(TileRenderContext context)
	{
		// TODO cache values, update neighbouring tiles upon "explored" flag changed.
		
		final GraphicsModule gfx = App.gfx();
		
		final Rect rect = context.getRect();
		
		if (!context.getTile().isExplored()) {
			gfx.quad(rect, UFOG_FULL);
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
		
		if ((ufog & Moves.BITS_NW_CORNER) == Moves.BIT_NW) gfx.quad(rect, UFOG_NW);
		if ((ufog & Moves.BIT_N) != 0) gfx.quad(rect, UFOG_N);
		if ((ufog & Moves.BITS_NE_CORNER) == Moves.BIT_NE) gfx.quad(rect, UFOG_NE);
		
		if ((ufog & Moves.BIT_W) != 0) gfx.quad(rect, UFOG_W);
		if ((ufog & Moves.BIT_E) != 0) gfx.quad(rect, UFOG_E);
		
		if ((ufog & Moves.BITS_SW_CORNER) == Moves.BIT_SW) gfx.quad(rect, UFOG_SW);
		if ((ufog & Moves.BIT_S) != 0) gfx.quad(rect, UFOG_S);
		if ((ufog & Moves.BITS_SE_CORNER) == Moves.BIT_SE) gfx.quad(rect, UFOG_SE);
		
	}
	
	
	@Override
	public void update(double delta)
	{
	}
}
