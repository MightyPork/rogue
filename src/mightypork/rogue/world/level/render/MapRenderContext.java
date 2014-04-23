package mightypork.rogue.world.level.render;


import mightypork.rogue.world.level.MapAccess;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.RectConst;
import mightypork.util.constraints.rect.builders.TiledRect;


public abstract class MapRenderContext {
	
	protected final MapAccess map;
	protected final TiledRect tiler;
	private final Rect mapRect;
	
	private RectConst tileRects[][];
	
	
	public MapRenderContext(MapAccess map, Rect drawArea) {
		this.map = map;
		this.tiler = drawArea.tiles(map.getWidth(), map.getHeight());
		this.mapRect = drawArea;
		
		tileRects = new RectConst[map.getHeight()][map.getWidth()];
		
		rebuildTileRects();
	}
	
	
	public void rebuildTileRects()
	{
		for(int y=0;y<map.getHeight();y++) {
			for(int x=0;x<map.getWidth();x++) {
				tileRects[y][x] = tiler.tile(x, y).freeze();
			}
		}
	}


	public Rect getRectForTile(int x, int y)
	{
		return tileRects[y][x];
	}
	
	
	public Rect getMapRect()
	{
		return mapRect;
	}
}
