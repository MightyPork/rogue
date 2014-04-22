package mightypork.rogue.world;


import mightypork.rogue.world.map.Level;
import mightypork.rogue.world.map.TileRenderContext;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.RectConst;
import mightypork.util.constraints.rect.proxy.RectBound;
import mightypork.util.constraints.vect.VectConst;
import mightypork.util.control.timing.Updateable;


public class WorldClient implements Updateable {
	
	private Level level = null;
	
	private final Player player = null;
		
	
	@Override
	public void update(double delta)
	{
		player.updateVisual(delta);
		
		level.updateVisual(player, delta);
	}
	
	
	/**
	 * Draw on screen
	 * 
	 * @param viewport rendering area on screen
	 * @param xTiles Desired nr of tiles horizontally
	 * @param yTiles Desired nr of tiles vertically
	 * @param minSize minimum tile size
	 */
	public void render(final RectBound viewport, final int yTiles, final int xTiles, final int minSize)
	{		
		final Rect r = viewport.getRect();
		final double vpH = r.height().value();
		final double vpW = r.width().value();
		
		// adjust tile size to fit desired amount of tiles
		
		final double allowedSizeW = vpW / xTiles;
		final double allowedSizeH = vpH / yTiles;
		int tileSize = (int) Math.round(Math.max(Math.min(allowedSizeH, allowedSizeW), minSize));
		
		tileSize -= tileSize % 16;
		
		final VectConst vpCenter = r.center().sub(tileSize * 0.5, tileSize).freeze(); // 0.5 to center, 1 to move up (down is teh navbar)
		
		final double playerX = player.getPosition().getXVisual();
		final double playerY = player.getPosition().getYVisual();
		
		// total map area
		//@formatter:off
		final RectConst mapRect = vpCenter.startRect().grow(
				playerX*tileSize,
				(level.getWidth() - playerX) * tileSize,
				playerY*tileSize,
				(level.getHeight() - playerY) * tileSize
		).freeze();
		//@formatter:on
		
		System.out.println(playerX + "," + playerY + " : " + mapRect);
		System.out.println(level.getWidth() + "," + level.getHeight());
		
		// tiles to render
		final int x1 = (int) Math.floor(playerX - (vpW / tileSize));
		final int y1 = (int) Math.floor(playerY - (vpH / tileSize));
		final int x2 = (int) Math.ceil(playerX + (vpW / tileSize));
		final int y2 = (int) Math.ceil(playerY + (vpH / tileSize));
		
		final TileRenderContext trc = new TileRenderContext(level, mapRect); //-tileSize*0.5
		for (trc.y = y1; trc.y <= y2; trc.y++) {
			for (trc.x = x1; trc.x <= x2; trc.x++) {
				trc.render();
			}
		}
	}
	
	
	public Player getPlayer()
	{
		return player;
	}
	
}
