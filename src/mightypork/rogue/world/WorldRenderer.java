package mightypork.rogue.world;


import mightypork.gamecore.core.modules.App;
import mightypork.gamecore.render.GradH;
import mightypork.gamecore.render.GradV;
import mightypork.gamecore.render.GraphicsModule;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.utils.math.algo.Coord;
import mightypork.utils.math.color.pal.RGB;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.RectConst;
import mightypork.utils.math.constraints.rect.proxy.RectProxy;
import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.math.constraints.vect.VectConst;


/**
 * World rendering untility
 * 
 * @author Ondřej Hruška (MightyPork)
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
		return Vect.make(WorldProvider.get().getPlayer().getVisualPos().neg().sub(0.5, 0.5)).freeze();
	}
	
	
	public void render()
	{
		prepareRenderContextIfNeeded();
		
		final GraphicsModule gfx = App.gfx();
		
		gfx.pushGeometry();
		gfx.setColor(RGB.WHITE);
		gfx.translate(center());
		gfx.scaleXY(tileSize.value());
		gfx.translate(getOffset());
		
		// tiles to render
		
		final Coord pos = WorldProvider.get().getPlayer().getCoord();
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
		// TODO
//		if (USE_BATCH_RENDERING) {
//			gfx.enterBatchTexturedQuadMode(Res.getTexture("tiles"));
//		}
		for (trc.pos.x = x1; trc.pos.x <= x2; trc.pos.x++) {
			for (trc.pos.y = y1; trc.pos.y <= y2; trc.pos.y++) {
				trc.renderTile();
			}
		}
		// TODO
//		if (USE_BATCH_RENDERING) {
//			gfx.leaveBatchTexturedQuadMode();
//		}
		
		// === ITEMS ON TILES ===
		
		for (trc.pos.x = x1; trc.pos.x <= x2; trc.pos.x++) {
			for (trc.pos.y = y1; trc.pos.y <= y2; trc.pos.y++) {
				trc.renderItems();
			}
		}
		
		// === ENTITIES ===
		
		for (final Entity e : activeLevel.getEntities()) {
			
			// avoid entities out of view rect
			final Vect entPos = e.pos.getVisualPos();
			final int x = (int) Math.round(entPos.x());
			final int y = (int) Math.round(entPos.y());
			
			if (x < x1 - ts || x > x2 + ts) continue;
			if (y < y1 - ts || y > y2 + ts) continue;
			
			e.render(trc);
		}
		
		// === unexplored fog ===
		
		// batch rendering of the tiles
		// TODO
//		if (USE_BATCH_RENDERING) {
//			gfx.setColor(RGB.WHITE, Const.RENDER_UFOG ? 1 : 0.6);
//			gfx.enterBatchTexturedQuadMode(Res.getTexture("tiles"));
//		}
		// TODO use Const.RENDER_UFOG
		for (trc.pos.x = x1; trc.pos.x <= x2; trc.pos.x++) {
			for (trc.pos.y = y1; trc.pos.y <= y2; trc.pos.y++) {
				trc.renderUFog();
			}
		}
		// TODO
//		if (USE_BATCH_RENDERING) {
//			gfx.leaveBatchTexturedQuadMode();
//		}
		
		// === OVERLAY SHADOW ===
		
		gfx.popGeometry();
		
		gfx.quad(leftShadow, new GradH(RGB.BLACK, RGB.NONE));
		gfx.quad(rightShadow, new GradH(RGB.NONE, RGB.BLACK));
		
		gfx.quad(topShadow, new GradV(RGB.BLACK, RGB.NONE));
		gfx.quad(bottomShadow, new GradV(RGB.NONE, RGB.BLACK));
	}
	
	
	public Vect getClickedTile(Vect clickPos)
	{
		final int ts = (int) tileSize.value();
		final Vect v = clickPos.sub(center().add(getOffset().mul(ts)));
		return Vect.make(v.x() / ts, v.y() / ts);
	}
	
}
