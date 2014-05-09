package mightypork.rogue.world.tile.tiles;


import mightypork.gamecore.resources.textures.TxSheet;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.TileType;
import mightypork.rogue.world.tile.render.BasicTileRenderer;


public abstract class TileBaseFloor extends TileWithItems {
	
	private final BasicTileRenderer renderer;
	
	
	public TileBaseFloor(TileModel model, TxSheet sheet)
	{
		super(model);
		this.renderer = new BasicTileRenderer(this, sheet);
	}
	
	
	@Override
	protected TileRenderer makeRenderer()
	{
		return renderer;
	}
	
	
	@Override
	public TileType getType()
	{
		return TileType.FLOOR;
	}
}
