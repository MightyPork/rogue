package mightypork.rogue.world.tile.impl;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mightypork.gamecore.render.DisplaySystem;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.render.textures.TxSheet;
import mightypork.rogue.Res;
import mightypork.rogue.world.tile.TileData;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderContext;
import mightypork.util.annotations.DefaultImpl;


public abstract class SimpleTile extends TileModel {
	
	private TxSheet sheet;
	
	
	public SimpleTile(int id, String sheetKey) {
		super(id);
		this.sheet = Res.getTxSheet(sheetKey);
	}
	
	
	@Override
	@DefaultImpl
	public void load(TileData data, InputStream in) throws IOException
	{
		// do nothing
	}
	
	
	@Override
	@DefaultImpl
	public void save(TileData data, OutputStream out) throws IOException
	{
		// do nothing
	}
	
	
	@Override
	public void render(TileData data, TileRenderContext context)
	{
		// TODO worldmap should take care of this and break the row drawing when it encounters end of screen etc
		
		// not in screen -> no draw
		if (!context.getRect().intersectsWith(DisplaySystem.getBounds())) return;
		
		Render.quadTextured(context.getRect(), sheet.getRandomQuad(context.getNoise()));
	}
	
	
	@Override
	@DefaultImpl
	public void update(TileData item, double delta)
	{
		// do nothing
	}
	
	@Override
	@DefaultImpl
	public boolean isWalkable(TileData data)
	{
		return isWalkable();
	}
	
}
