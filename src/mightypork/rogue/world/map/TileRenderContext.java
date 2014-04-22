package mightypork.rogue.world.map;


import mightypork.rogue.world.tile.Tile;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.proxy.RectBound;
import mightypork.util.math.noise.NoiseGen;


/**
 * Context for tile rendering.
 * 
 * @author MightyPork
 */
public final class TileRenderContext extends MapRenderContext implements RectBound {
	
	public int x;
	public int y;
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
		return map.getTile(x, y);
	}
	
	
	/**
	 * Get a neighbor tile
	 * 
	 * @param offsetX x offset (left-right)
	 * @param offsetY y offset (up-down)
	 * @return the tile at that position
	 */
	public Tile getAdjacentTile(int offsetX, int offsetY)
	{
		return map.getTile(x + offsetX, y + offsetY);
	}
	
	
	/**
	 * @return per-coord noise value 0..1
	 */
	public double getTileNoise()
	{
		return noise.valueAt(x, y);
	}
	
	
	public void render()
	{
		map.getTile(x, y).render(this);
	}
	
	
	/**
	 * Rect of the current tile to draw
	 */
	@Override
	public Rect getRect()
	{
		return getRectForTile(x, y);
	}
}
