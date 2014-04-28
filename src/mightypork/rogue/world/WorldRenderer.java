package mightypork.rogue.world;


import mightypork.gamecore.render.Render;
import mightypork.rogue.Res;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.modules.EntityPos;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.util.math.color.RGB;
import mightypork.util.math.constraints.Pollable;
import mightypork.util.math.constraints.num.Num;
import mightypork.util.math.constraints.rect.Rect;
import mightypork.util.math.constraints.rect.RectConst;
import mightypork.util.math.constraints.rect.proxy.RectProxy;
import mightypork.util.math.constraints.vect.Vect;
import mightypork.util.math.constraints.vect.VectConst;


/**
 * World rendering untility
 * 
 * @author MightyPork
 */
public class WorldRenderer extends RectProxy {
	
	private static final boolean USE_BATCH_RENDERING = true;
	
	private final Num tileSize;
	
	// can be changed
	private RectConst mapRect;
	private Level activeLevel;
	
	private final Rect rightShadow;
	private final Rect leftShadow;
	private final Rect topShadow;
	private final Rect bottomShadow;
	
	private TileRenderContext trc;
	
	
	public WorldRenderer(Rect viewport, Num tileSize) {
		super(viewport);
		
		this.tileSize = tileSize;
		
		final Num grX = width().perc(30);
		leftShadow = leftEdge().growRight(grX);
		rightShadow = rightEdge().growLeft(grX);
		
		final Num grY = height().perc(20);
		topShadow = topEdge().growDown(grY);
		bottomShadow = bottomEdge().growUp(grY);
	}
	
	
	private void prepareRenderContextIfNeeded()
	{
		final Level level = WorldProvider.get().getCurrentLevel();
		
		if (activeLevel == level) return;
		
		activeLevel = level;
		
		mapRect = Rect.make(0, 0, level.getWidth(), level.getHeight());
		
		trc = new TileRenderContext(activeLevel, mapRect);
	}
	
	
	private VectConst getOffset()
	{
		Entity ent = WorldProvider.get().getPlayerEntity();
		return Vect.make(ent.pos.getVisualPos().neg().add(0.5, 0.5)).freeze();
	}
	
	
	public void render()
	{
		prepareRenderContextIfNeeded();
		
		Render.pushMatrix();
		Render.setColor(RGB.WHITE);
		Render.translate(center());
		Render.scaleXY(tileSize.value());
		Render.translate(getOffset());
		
		// tiles to render
		Entity ent = WorldProvider.get().getPlayerEntity();
		
		final Coord pos = ent.pos.getCoord();
		final double w = width().value();
		final double h = height().value();
		
		final double ts = tileSize.value();
		
		final int xtilesh = (int) (w / (ts * 2)) + 1;
		final int ytilesh = (int) (h / (ts * 2)) + 1;
		
		final int x1 = pos.x - xtilesh;
		final int y1 = pos.y - ytilesh;
		final int x2 = pos.x + xtilesh;
		final int y2 = pos.y + ytilesh;
		
		// === TILES ===
		
		// batch rendering of the tiles
		if (USE_BATCH_RENDERING) {
			Render.enterBatchTexturedQuadMode(Res.getTexture("tiles16"));
		}
		
		for (trc.pos.x = x1; trc.pos.x <= x2; trc.pos.x++) {
			for (trc.pos.y = y1; trc.pos.y <= y2; trc.pos.y++) {
				trc.renderTile();
			}
		}
		
		if (USE_BATCH_RENDERING) {
			Render.leaveBatchTexturedQuadMode();
		}
		
		// === ITEMS ON TILES ===
		
		for (trc.pos.x = x1; trc.pos.x <= x2; trc.pos.x++) {
			for (trc.pos.y = y1; trc.pos.y <= y2; trc.pos.y++) {
				trc.renderItems();
			}
		}
		
		// === ENTITIES ===
		
		for (final Entity e : activeLevel.getEntities()) {
			
			// avoid entities out of view rect
			Vect entPos = e.pos.getVisualPos(); 
			final int x = (int) Math.round(entPos.x());
			final int y = (int) Math.round(entPos.y());
			
			if (x < x1 - ts || x > x2 + ts) continue;
			if (y < y1 - ts || y > y2 + ts) continue;
			
			e.render(trc);
		}
		
		Render.popMatrix();
		
		// === OVERLAY SHADOW ===
		
		Render.quadGradH(leftShadow, RGB.BLACK, RGB.NONE);
		Render.quadGradH(rightShadow, RGB.NONE, RGB.BLACK);
		
		Render.quadGradV(topShadow, RGB.BLACK, RGB.NONE);
		Render.quadGradV(bottomShadow, RGB.NONE, RGB.BLACK);
	}
	
	
	public Coord getClickedTile(Vect clickPos)
	{
		final int ts = (int) tileSize.value();
		final Vect v = clickPos.sub(center().add(getOffset().mul(ts)));
		return new Coord(v.xi() / ts, v.yi() / ts);
	}
	
}
