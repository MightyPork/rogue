package mightypork.rogue.world;


import mightypork.gamecore.render.Render;
import mightypork.rogue.Res;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.util.constraints.Pollable;
import mightypork.util.constraints.num.Num;
import mightypork.util.constraints.num.caching.NumCache;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.RectConst;
import mightypork.util.constraints.rect.caching.RectCache;
import mightypork.util.constraints.rect.proxy.RectProxy;
import mightypork.util.constraints.vect.Vect;
import mightypork.util.constraints.vect.caching.VectCache;
import mightypork.util.math.color.RGB;


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
	
	
	public WorldRenderer(World world, Rect viewport, int xTiles, int yTiles, int minTileSize) {
		super(viewport);
		
		this.world = world;
		this.player = world.playerEntity;
		
		tileSize = width().div(xTiles).min(height().div(yTiles)).max(minTileSize).cached();
		
		final Num th = tileSize.half();
		vpCenter = center().sub(th, th).cached();
		
		final Num grX = width().perc(30);
		leftShadow = leftEdge().growRight(grX);
		rightShadow = rightEdge().growLeft(grX);
		
		final Num grY = height().perc(20);
		topShadow = topEdge().growDown(grY);
		bottomShadow = bottomEdge().growUp(grY);
		
		setupMapRect();
	}
	
	
	private void setupMapRect()
	{
		Level level = world.getCurrentLevel();
		
		if (activeLevel == level) return;
		activeLevel = level;
		
		mapRect = Rect.make(vpCenter, tileSize.mul(level.getWidth()), tileSize.mul(level.getHeight())).cached();
	}
	
	
	private RectConst getCurrentDrawRect()
	{
		
		WorldPos pos = player.getPosition();
		final double playerX = pos.getVisualX();
		final double playerY = pos.getVisualY();
		
		double ts = tileSize.value();
		
		final RectConst drawRect = mapRect.move(-ts * playerX, -ts * playerY).freeze();
		
		return drawRect;
	}
	
	
	public void render()
	{
		setupMapRect();
		
		final TileRenderContext rc = new TileRenderContext(activeLevel, getCurrentDrawRect());
		
		// tiles to render
		final WorldPos pos = player.getPosition();
		final double w = width().value();
		final double h = height().value();
		final double ts = tileSize.value();
		final double tsh = ts / 2;
		
		final int x1 = (int) Math.floor(pos.x - (w / tsh));
		final int y1 = (int) Math.floor(pos.y - (h / tsh));
		final int x2 = (int) Math.ceil(pos.x + (w / tsh));
		final int y2 = (int) Math.ceil(pos.y + (h / tsh));
		
		// === TILES ===
		
		// batch rendering of the tiles
		if (USE_BATCH_RENDERING) {
			Render.enterBatchTexturedQuadMode(Res.getTexture("tiles16"));
		}
		
		for (rc.y = y1; rc.y <= y2; rc.y++) {
			for (rc.x = x1; rc.x <= x2; rc.x++) {
				rc.renderTile();
			}
		}
		
		if (USE_BATCH_RENDERING) {
			Render.leaveBatchTexturedQuadMode();
		}
		
		// === ITEMS ON TILES ===
		
		for (rc.y = y1; rc.y <= y2; rc.y++) {
			for (rc.x = x1; rc.x <= x2; rc.x++) {
				rc.renderItems();
			}
		}
		
		// === ENTITIES ===
		
		for (final Entity e : activeLevel.getEntities()) {
			
			// avoid entities out of view rect
			int x = (int) Math.round(e.getPosition().getVisualX());
			int y = (int) Math.round(e.getPosition().getVisualY());
			
			if (x < x1 - ts || x > x2 + ts) continue;
			if (y < y1 - ts || y > y2 + ts) continue;
			
			e.render(rc);
		}
		
		// === OVERLAY SHADOW ===
		
		Render.quadGradH(leftShadow, RGB.BLACK, RGB.NONE);
		Render.quadGradH(rightShadow, RGB.NONE, RGB.BLACK);
		
		Render.quadGradV(topShadow, RGB.BLACK, RGB.NONE);
		Render.quadGradV(bottomShadow, RGB.NONE, RGB.BLACK);
	}
	
	
	public WorldPos getClickedTile(Vect clickPos)
	{
		RectConst drawRect = getCurrentDrawRect();
		Vect v = clickPos.sub(drawRect.origin());
		int ts = (int)tileSize.value();
		return new WorldPos(v.xi() / ts, v.yi() / ts);
	}
	
	
	@Override
	public void poll()
	{
		// in order of dependency
		vpCenter.poll();
		tileSize.poll();
		
		mapRect.poll();
	}
	
}
