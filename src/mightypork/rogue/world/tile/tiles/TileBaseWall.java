package mightypork.rogue.world.tile.tiles;


import mightypork.gamecore.resources.textures.TxSheet;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.TileType;
import mightypork.rogue.world.tile.renderers.BasicTileRenderer;


public abstract class TileBaseWall extends TileSolid {
	
	private BasicTileRenderer renderer;
	
	
	public TileBaseWall(TileModel model, TxSheet sheet)
	{
		super(model);
		this.renderer = new BasicTileRenderer(this, sheet);
	}
	
	
	public BasicTileRenderer getRenderer()
	{
		return renderer;
	}
	
	
	@Override
	public TileType getType()
	{
		return TileType.WALL;
	}
}
