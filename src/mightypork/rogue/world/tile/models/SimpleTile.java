package mightypork.rogue.world.tile.models;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.render.textures.TxSheet;
import mightypork.rogue.Res;
import mightypork.rogue.world.map.TileRenderContext;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;
import mightypork.util.annotations.DefaultImpl;
import mightypork.util.files.ion.IonBundle;


/**
 * Basic implementation of a tile with coord-random texture and no animation.
 * 
 * @author MightyPork
 */
public abstract class SimpleTile extends TileModel {
	
	protected final TxSheet sheet;
	
	
	public SimpleTile(int id, String sheetKey)
	{
		super(id);
		this.sheet = Res.getTxSheet(sheetKey);
	}
	
	
	@Override
	public void render(TileRenderContext context)
	{
		Render.quadTextured(context.getRect(), sheet.getRandomQuad(context.getTileNoise()));
	}
	
	
	@Override
	@DefaultImpl
	public void update(Tile tile, double delta)
	{
	}
	
	
	/*
	 * Items can override this if their walkability changes based on something
	 */
	@Override
	public boolean isWalkable(Tile tile)
	{
		return isPotentiallyWalkable();
	}
	
	
	@Override
	public abstract boolean isPotentiallyWalkable();
	
	
	@Override
	@DefaultImpl
	public boolean hasMetadata()
	{
		return false; // it's a SIMPLE tile
	}
	
	
	@Override
	@DefaultImpl
	public void loadMetadata(Tile tile, IonBundle ib)
	{
	}
	
	
	@Override
	@DefaultImpl
	public void saveMetadata(Tile tile, IonBundle ib)
	{
	}
}
