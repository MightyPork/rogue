package mightypork.rogue.world.level.render;


import mightypork.rogue.world.level.MapAccess;
import mightypork.util.math.constraints.rect.Rect;
import mightypork.util.math.constraints.rect.builders.TiledRect;


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