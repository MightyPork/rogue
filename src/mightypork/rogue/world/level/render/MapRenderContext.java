package mightypork.rogue.world.level.render;


import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.tile.Tile;
import mightypork.utils.math.algo.Coord;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.builders.TiledRect;


public abstract class MapRenderContext {

	protected final Level map;
	protected final TiledRect tiler;
	private final Rect mapRect;


	public MapRenderContext(Level map, Rect drawArea)
	{
		this.map = map;

		this.tiler = drawArea.tiles(map.getWidth(), map.getHeight());
		this.mapRect = drawArea;
	}


	public Rect getRectForTile(Coord pos)
	{
		return tiler.tile(pos.x, pos.y);
	}


	public Rect getMapRect()
	{
		return mapRect;
	}


	public Tile getTile(Coord pos)
	{
		return map.getTile(pos);
	}
}
