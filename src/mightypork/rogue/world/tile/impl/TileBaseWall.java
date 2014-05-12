package mightypork.rogue.world.tile.impl;


import mightypork.gamecore.resources.textures.TxSheet;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileType;
import mightypork.rogue.world.tile.render.BasicTileRenderer;


public abstract class TileBaseWall extends TileSolid {
	
	private final BasicTileRenderer renderer;
	
	
	public TileBaseWall(TileModel model, TxSheet sheet)
	{
		super(model);
		this.renderer = new BasicTileRenderer(this, sheet);
	}
	
	
	@Override
	public BasicTileRenderer makeRenderer()
	{
		return renderer;
	}
	
	
	@Override
	public TileType getType()
	{
		return TileType.WALL;
	}
}
