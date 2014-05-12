package mightypork.rogue.world.tile.impl;


import mightypork.gamecore.resources.textures.TxSheet;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.TileType;
import mightypork.rogue.world.tile.render.BasicTileRenderer;


/**
 * Collapsed wall that's walk-through
 * 
 * @author MightyPork
 */
public abstract class TileBasePassage extends TileSolid {
	
	private final BasicTileRenderer renderer;
	
	
	public TileBasePassage(TileModel model, TxSheet sheet)
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
		return TileType.PASSAGE;
	}
	
	
	@Override
	public boolean isWalkable()
	{
		return true;
	}
	
}
