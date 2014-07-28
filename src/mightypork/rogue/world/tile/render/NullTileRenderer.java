package mightypork.rogue.world.tile.render;


import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.TileRenderer;


/**
 * Do-nothing tile renderer
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class NullTileRenderer extends TileRenderer {

	public NullTileRenderer()
	{
		super(null);
	}


	@Override
	public void renderTile(TileRenderContext context)
	{
	}


	@Override
	public void renderShadows(TileRenderContext context)
	{
	}


	@Override
	public void renderUnexploredFog(TileRenderContext context)
	{
	}


	@Override
	public void update(double delta)
	{
	}

}
