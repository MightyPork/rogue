package mightypork.rogue.world;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import mightypork.gamecore.render.Render;
import mightypork.rogue.world.map.LevelMap;
import mightypork.rogue.world.map.TileRenderContext;
import mightypork.rogue.world.structs.LevelList;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.RectConst;
import mightypork.util.constraints.rect.proxy.RectBound;
import mightypork.util.constraints.vect.Vect;
import mightypork.util.constraints.vect.VectConst;
import mightypork.util.control.timing.Updateable;
import mightypork.util.error.CorruptedDataException;
import mightypork.util.files.ion.Ion;
import mightypork.util.files.ion.IonBundle;
import mightypork.util.files.ion.Ionizable;
import mightypork.util.math.color.RGB;


public class World implements Ionizable, Updateable {
	
	public static final short ION_MARK = 706;
	
	private LevelList levels = new LevelList();
	
	private LocalPlayer player = new LocalPlayer();
	
	private transient final Set<MapObserver> observers = new HashSet<>();
	
	private long seed;
	
	
	@Override
	public void load(InputStream in) throws IOException
	{
		// world data
		IonBundle ib = (IonBundle) Ion.readObject(in);
		player = ib.get("player", player);
		levels = ib.get("levels", levels);
		seed = ib.get("seed", seed);
		
		// levels
		Ion.readSequence(in, levels);
		
		if (player == null) throw new CorruptedDataException("Null player in world.");
	}
	
	
	@Override
	public void save(OutputStream out) throws IOException
	{
		IonBundle ib = new IonBundle();
		ib.put("player", player);
		ib.put("levels", levels);
		ib.put("seed", seed);
	}
	
	
	public void setPlayer(LocalPlayer player)
	{
		removeObserver(this.player);
		
		this.player = player;
		
		addObserver(player);
	}
	
	
	public void removeObserver(MapObserver observer)
	{
		observers.remove(observer);
	}
	
	
	public void addObserver(MapObserver observer)
	{
		observers.add(observer);
	}
	
	
	public void addLevel(LevelMap level)
	{
		levels.add(level);
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
	
	@Override
	public void update(double delta)
	{
		for (int level = 0; level < levels.size(); level++) {
			for (MapObserver observer : observers) {
				if (observer.getPosition().floor == level) {
					levels.get(level).update(observer, delta);
				}
			}
		}
	}
	
	
	public LevelMap getLevelForObserver(MapObserver observer)
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
		LevelMap floor = getLevelForObserver(player); // TODO fractional movement
		
		Rect r = viewport.getRect();
		double vpH = r.height().value();
		double vpW = r.width().value();
		
		// adjust tile size to fit desired amount of tiles
		
		double allowedSizeW = vpW / xTiles;
		double allowedSizeH = vpH / yTiles;
		int tileSize = (int) Math.round(Math.max(Math.min(allowedSizeH, allowedSizeW), minSize));
		
		tileSize -= tileSize % 16;
		
		VectConst vpCenter = r.center().sub(tileSize * 0.5, tileSize).freeze(); // 0.5 to center, 1 to move up (down is teh navbar)
		
		int playerX = player.getPosition().x;
		int playerY = player.getPosition().y;
		
		// total map area
		//@formatter:off
		RectConst mapRect = vpCenter.startRect().grow(
				playerX*tileSize,
				playerY*tileSize,//
				(floor.getWidth() - playerX) * tileSize,
				(floor.getHeight() - playerY) * tileSize
		).freeze();
		//@formatter:on
		
		// tiles to render
		int x1 = (int) Math.floor(playerX - (vpW / tileSize));
		int y1 = (int) Math.floor(playerY - (vpH / tileSize));
		int x2 = (int) Math.ceil(playerX + (vpW / tileSize));
		int y2 = (int) Math.ceil(playerY + (vpH / tileSize));
		
		TileRenderContext trc = new TileRenderContext(floor, mapRect); //-tileSize*0.5
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
	
}
