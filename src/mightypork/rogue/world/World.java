package mightypork.rogue.world;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import mightypork.rogue.world.map.Level;
import mightypork.rogue.world.map.TileRenderContext;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.RectConst;
import mightypork.util.constraints.rect.proxy.RectBound;
import mightypork.util.constraints.vect.VectConst;
import mightypork.util.control.timing.Updateable;
import mightypork.util.ion.IonBundle;
import mightypork.util.ion.IonBundled;


public class World implements IonBundled, Updateable {
	
	private final ArrayList<Level> levels = new ArrayList<>();
	
	private final Player player = new Player();
	
	private transient final Set<MapObserver> observers = new HashSet<>();
	
	/** This seed can be used to re-create identical world. */
	private long seed;
	
	
	@Override
	public void load(IonBundle in) throws IOException
	{
		in.loadBundled("player", player);
		seed = in.get("seed", 0L);
		in.loadSequence("levels", levels);
	}
	
	
	@Override
	public void save(IonBundle out) throws IOException
	{
		out.putBundled("player", player);
		out.put("seed", seed);
		out.putSequence("levels", levels);
	}
	
	
	public void removeObserver(MapObserver observer)
	{
		observers.remove(observer);
	}
	
	
	public void addObserver(MapObserver observer)
	{
		observers.add(observer);
	}
	
	
	public void addLevel(Level level)
	{
		levels.add(level);
	}
	
	
	@Override
	public void update(double delta)
	{
		player.update(delta);
		
		for (int level = 0; level < levels.size(); level++) {
			for (final MapObserver observer : observers) {
				if (observer.getPosition().floor == level) {
					levels.get(level).update(observer, delta);
				}
			}
		}
	}
	
	
	public Level getLevelForObserver(MapObserver observer)
	{
		return levels.get(observer.getPosition().floor);
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
		final Level floor = getLevelForObserver(player); // TODO fractional movement
		
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
				(floor.getWidth() - playerX) * tileSize,
				playerY*tileSize,
				(floor.getHeight() - playerY) * tileSize
		).freeze();
		//@formatter:on
		
		System.out.println(playerX + "," + playerY + " : " + mapRect);
		System.out.println(floor.getWidth() + "," + floor.getHeight());
		
		// tiles to render
		final int x1 = (int) Math.floor(playerX - (vpW / tileSize));
		final int y1 = (int) Math.floor(playerY - (vpH / tileSize));
		final int x2 = (int) Math.ceil(playerX + (vpW / tileSize));
		final int y2 = (int) Math.ceil(playerY + (vpH / tileSize));
		
		final TileRenderContext trc = new TileRenderContext(floor, mapRect); //-tileSize*0.5
		for (trc.y = y1; trc.y <= y2; trc.y++) {
			for (trc.x = x1; trc.x <= x2; trc.x++) {
				trc.render();
			}
		}
	}
	
	
	public void setSeed(long seed)
	{
		this.seed = seed;
	}
	
	
	public long getSeed()
	{
		return seed;
	}
	
	
	public Player getPlayer()
	{
		return player;
	}
	
}
