package mightypork.rogue.world.tile;


import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.builders.TiledRect;
import mightypork.util.constraints.rect.proxy.RectBound;
import mightypork.util.math.noise.NoiseGen;


public final class TileRenderContext implements RectBound {
	
	private final TileGrid map;
	private final TiledRect tiler;
	private final NoiseGen noise;
	
	public int x, y;
	
	
	public TileRenderContext(TileGrid map, Rect drawArea, long renderNoiseSeed) {
		this.map = map;
		this.tiler = drawArea.tiles(map.getWidth(), map.getHeight());
		this.noise = new NoiseGen(0.2, 0, 0.5, 1, renderNoiseSeed);
	}
	
	
	public Tile getTile()
	{
		return map.getTile(x, y);
	}
	
	
	public Tile getAdjacentTile(int offsetX, int offsetY)
	{
		return map.getTile(x + offsetX, y + offsetY);
	}
	
	
	@Override
	public Rect getRect()
	{
		return tiler.tile(x, y);
	}
	
	
	/**
	 * @return per-coord noise value 0..1
	 */
	public double getNoise()
	{
		return noise.valueAt(x, y);
	}
	
	
	public void setCoord(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
