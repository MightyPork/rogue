package mightypork.rogue.world.map;


import mightypork.rogue.world.tile.Tile;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.builders.TiledRect;
import mightypork.util.constraints.rect.proxy.RectBound;
import mightypork.util.math.noise.NoiseGen;


/**
 * Context for tile rendering. Provides tile rect, surrounding tiles, rendered
 * tile and random noise values for position-static tile variation.
 * 
 * @author MightyPork
 */
public final class TileRenderContext implements RectBound {
	
	private final MapAccess map;
	private final TiledRect tiler;
	private final NoiseGen noise;
	
	public int x, y;
	
	
	public TileRenderContext(MapAccess map, Rect drawArea)
	{
		this.map = map;
		this.tiler = drawArea.tiles(map.getWidth(), map.getHeight());
		
		this.tiler.setOverlap(0.001);		
		
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
	 * Rect of the current tile to draw
	 */
	@Override
	public Rect getRect()
	{
		return tiler.tile(x, y);
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
}
