package mightypork.rogue.world;


import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.builders.TiledRect;
import mightypork.util.constraints.rect.proxy.RectBound;


public class TileRenderContext implements RectBound {
	
	private final TileHolder map;
	private final TiledRect tiler;
	private int x, y;
	
	
	public TileRenderContext(TileHolder map, Rect mapArea) {
		this.map = map;
		this.tiler = mapArea.tiles(map.getWidth(), map.getHeight());
	}
	
	
	public TileData getTile()
	{
		return map.getTile(x, y);
	}
	
	
	public TileData getAdjacentTile(int offsetX, int offsetY)
	{
		return map.getTile(x + offsetX, y + offsetY);
	}
	
	
	@Override
	public Rect getRect()
	{
		return tiler.tile(x, y);
	}
}
