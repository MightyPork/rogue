package mightypork.rogue.world.tile.tiles;


import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.util.annotations.DefaultImpl;


public abstract class BasicTile extends Tile {
	
	public BasicTile(int id, TileRenderer renderer)
	{
		super(id, renderer);
	}
	
	
	@Override
	public boolean isWalkable()
	{
		return isPotentiallyWalkable();
	}
	
	
	@Override
	public void renderTile(TileRenderContext context)
	{
		if (!isExplored()) return;
		
		renderer.renderTile(context);
		
		if (doesReceiveShadow()) renderer.renderShadows(context);
		
		renderer.renderUnexploredFog(context);
	}
	
	
	@Override
	@DefaultImpl
	public void renderExtra(TileRenderContext context)
	{
	}
	
	
	@Override
	@DefaultImpl
	public void update(Level level, double delta)
	{
	}
	
	
	@Override
	public boolean doesReceiveShadow()
	{
		return !doesCastShadow();
	}
}
