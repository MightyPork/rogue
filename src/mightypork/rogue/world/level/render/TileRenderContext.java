package mightypork.rogue.world.level.render;


import mightypork.gamecore.util.math.algo.Coord;
import mightypork.gamecore.util.math.algo.Step;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.math.constraints.rect.proxy.RectBound;
import mightypork.gamecore.util.math.noise.NoiseGen;
import mightypork.rogue.world.level.LevelReadAccess;
import mightypork.rogue.world.tile.Tile;


/**
 * Context for tile rendering.
 * 
 * @author MightyPork
 */
public final class TileRenderContext extends MapRenderContext implements RectBound {
	
	public final Coord pos = Coord.zero();
	private final NoiseGen noise;
	
	
	public TileRenderContext(LevelReadAccess map, Rect drawArea)
	{
		super(map, drawArea);
		
		//this.tiler.setOverlap(0.002); // avoid gaps (rounding error?)	
		
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
	public Tile getAdjacentTile(Step offset)
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
		map.getTile(pos).renderTile(this);
	}
	
	
	public void renderItems()
	{
		map.getTile(pos).renderExtra(this);
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
