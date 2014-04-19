package mightypork.rogue.world.tile.models;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.render.textures.TxSheet;
import mightypork.rogue.Res;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderContext;
import mightypork.util.annotations.DefaultImpl;


public abstract class SimpleTile extends TileModel {
	
	protected final TxSheet sheet;
	
	
	public SimpleTile(int id, String sheetKey)
	{
		super(id);
		this.sheet = Res.getTxSheet(sheetKey);
	}
	
	
	@Override
	public void render(Tile tile, TileRenderContext context)
	{
		Render.quadTextured(context.getRect(), sheet.getRandomQuad(context.getTileNoise()));
	}
	
	
	@Override
	@DefaultImpl
	public boolean isWalkable(Tile tile)
	{
		return isPotentiallyWalkable();
	}
	
	
	@Override
	public abstract boolean isPotentiallyWalkable();
	
}
