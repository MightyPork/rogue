package mightypork.rogue.world.level.render;


import mightypork.rogue.world.Coord;
import mightypork.rogue.world.level.MapAccess;
import mightypork.rogue.world.tile.Tile;
import mightypork.util.math.constraints.rect.Rect;
import mightypork.util.math.constraints.rect.proxy.RectBound;
import mightypork.util.math.noise.NoiseGen;


/**
 * Context for tile rendering.
 * 
 * @author MightyPork
 */
public final class TileRenderContext extends MapRenderContext implements RectBound {
	
	public final Coord pos = Coord.zero();
	private final NoiseGen noise;
	
	
	public TileRenderContext(MapAccess map, Rect drawArea)
	{
		super(map, drawArea);
		
		this.tiler.setOverlap(0.01); // avoid gaps (rounding error?)	
		
		this.noise = map.getNoiseGen();
	}
	
	
	/**
	 * @return the rendered tile.
	 */
	public Tile getTile()
	{
		return map.getTile(pos);
	}
	
	
	/**
	 * Get a neighbor tile
	 * 
	 * @param offset offsets
	 * @return the tile at that position
	 */
	public Tile getAdjacentTile(Coord offset)
	{
		return map.getTile(pos.add(offset));
	}
	
	
	/**
	 * @return per-coord noise value 0..1
	 */
	public double getTileNoise()
	{
		return noise.valueAt(pos.x, pos.y);
	}
	
	
	public void renderTile()
	{
		if(!map.getTile(pos).isExplored()) return;
		map.getTile(pos).renderTile(this);
	}
	
	
	public void renderItems()
	{
		map.getTile(pos).renderItems(this);
	}
	
	
	/**
	 * Rect of the current tile to draw
	 */
	@Override
	public Rect getRect()
	{
		return getRectForTile(pos);
	}
}
