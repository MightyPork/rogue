package mightypork.rogue.world.tile.tiles;


import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.TileType;
import mightypork.util.math.color.Color;
import mightypork.util.math.color.RGB;


public class NullTile extends Tile {
	
	public NullTile(int id, TileRenderer renderer)
	{
		super(id, renderer);
	}
	
	
	@Override
	public void update(Level level, double delta)
	{
	}
	
	
	@Override
	public void renderTile(TileRenderContext context)
	{
	}
	
	
	@Override
	public void renderExtra(TileRenderContext context)
	{
	}
	
	
	@Override
	public boolean isWalkable()
	{
		return false;
	}
	
	
	@Override
	public boolean isPotentiallyWalkable()
	{
		return false;
	}
	
	
	@Override
	public TileType getType()
	{
		return TileType.NULL;
	}
	
	
	@Override
	public boolean canHaveItems()
	{
		return false;
	}
	
	
	@Override
	public boolean doesCastShadow()
	{
		return false;
	}
	
	
	@Override
	public boolean doesReceiveShadow()
	{
		return false;
	}
	
	
	@Override
	public Color getMapColor()
	{
		return RGB.NONE;
	}
}
