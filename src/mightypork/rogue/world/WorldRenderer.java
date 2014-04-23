package mightypork.rogue.world;


import mightypork.gamecore.render.Render;
import mightypork.rogue.Res;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.util.math.color.RGB;
import mightypork.util.math.constraints.Pollable;
import mightypork.util.math.constraints.num.Num;
import mightypork.util.math.constraints.num.caching.NumCache;
import mightypork.util.math.constraints.rect.Rect;
import mightypork.util.math.constraints.rect.caching.RectCache;
import mightypork.util.math.constraints.rect.proxy.RectProxy;
import mightypork.util.math.constraints.vect.Vect;
import mightypork.util.math.constraints.vect.caching.VectCache;


/**
 * World rendering untility
 * 
 * @author MightyPork
 */
public class WorldRenderer extends RectProxy implements Pollable {
	
	private static final boolean USE_BATCH_RENDERING = true;
	
	private final VectCache vpCenter;
	private final NumCache tileSize;
	
	private final World world;
	private final Entity player;
	
	// can be changed
	private RectCache mapRect;
	private Level activeLevel;
	
	private final Rect rightShadow;
	private final Rect leftShadow;
	private final Rect topShadow;
	private final Rect bottomShadow;
	
	private TileRenderContext trc;
	
	
	public WorldRenderer(World world, Rect viewport, int xTiles, int yTiles, int minTileSize)
	{
		super(viewport);
		
		this.world = world;
		this.player = world.getPlayerEntity();
		
		tileSize = width().div(xTiles).min(height().div(yTiles)).max(minTileSize).cached();
		
		final Num th = tileSize.half();
		vpCenter = center().sub(th, th).cached();
		
		final Num grX = width().perc(30);
		leftShadow = leftEdge().growRight(grX);
		rightShadow = rightEdge().growLeft(grX);
		
		final Num grY = height().perc(20);
		topShadow = topEdge().growDown(grY);
		bottomShadow = bottomEdge().growUp(grY);
		
		rebuildTiles();
	}
	
	
	private void rebuildTiles()
	{
		final Level level = world.getCurrentLevel();
		
		if (activeLevel == level) return;
		activeLevel = level;
		
		mapRect = Rect.make(vpCenter, tileSize.mul(level.getWidth()), tileSize.mul(level.getHeight())).cached();
		
		trc = new TileRenderContext(activeLevel, mapRect);
	}
	
	
	private Vect getOffset()
	{
		final WorldPos pos = player.getPosition();
		final double playerX = pos.getVisualX();
		final double playerY = pos.getVisualY();
		
		final double ts = tileSize.value();
		
		return Vect.make((-ts * playerX), (-ts * playerY));
	}
	
	
	public void render()
	{
		Render.pushMatrix();
		Render.setColor(RGB.WHITE);
		Render.translate(getOffset());
		
		// tiles to render
		final WorldPos pos = player.getPosition();
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
		
		for (trc.y = y1; trc.y <= y2; trc.y++) {
			for (trc.x = x1; trc.x <= x2; trc.x++) {
				trc.renderTile();
			}
		}
		
		if (USE_BATCH_RENDERING) {
			Render.leaveBatchTexturedQuadMode();
		}
		
		// === ITEMS ON TILES ===
		
		for (trc.y = y1; trc.y <= y2; trc.y++) {
			for (trc.x = x1; trc.x <= x2; trc.x++) {
				trc.renderItems();
			}
		}
		
		// === ENTITIES ===
		
		for (final Entity e : activeLevel.getEntities()) {
			
			// avoid entities out of view rect
			final int x = (int) Math.round(e.getPosition().getVisualX());
			final int y = (int) Math.round(e.getPosition().getVisualY());
			
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
	
	
	public WorldPos getClickedTile(Vect clickPos)
	{
		final Vect v = clickPos.sub(mapRect.origin().add(getOffset()));
		final int ts = (int) tileSize.value();
		return new WorldPos(v.xi() / ts, v.yi() / ts);
	}
	
	
	@Override
	public void poll()
	{
		// in order of dependency
		vpCenter.poll();
		tileSize.poll();
		
		mapRect.poll();
		
		rebuildTiles();
	}
	
}
