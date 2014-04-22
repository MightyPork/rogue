package mightypork.rogue.world.map;


import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.builders.TiledRect;


public abstract class MapRenderContext {
	
	protected final MapAccess map;
	protected final TiledRect tiler;
	private final Rect mapRect;
	
	
	public MapRenderContext(MapAccess map, Rect drawArea)
	{
		this.map = map;
		this.tiler = drawArea.tiles(map.getWidth(), map.getHeight());
		this.mapRect = drawArea;
	}
	
	
	public Rect getRectForTile(int x, int y)
	{
		return tiler.tile(x, y);
	}
	
	
	public Rect getMapRect()
	{
		return mapRect;
	}
}
