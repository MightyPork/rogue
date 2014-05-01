package mightypork.rogue.world.tile.tiles;


import mightypork.gamecore.resources.textures.TxSheet;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.TileType;
import mightypork.rogue.world.tile.renderers.BasicTileRenderer;


public abstract class TileBaseFloor extends TileWalkable {
	
	private BasicTileRenderer renderer;


	public TileBaseFloor(TileModel model, TxSheet sheet)
	{
		super(model);
		this.renderer = new BasicTileRenderer(this, sheet);
	}
	
	@Override
	protected TileRenderer getRenderer()
	{
		return renderer;
	}
	
	
	@Override
	public TileType getType()
	{
		return TileType.FLOOR;
	}
}
