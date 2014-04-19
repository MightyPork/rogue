package mightypork.rogue.world.tile.models;


import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderContext;


public class NullTile extends TileModel {
	
	private Tile inst;
	
	
	public NullTile(int id)
	{
		super(id);
	}
	
	
	@Override
	public void render(Tile tile, TileRenderContext context)
	{
	}
	
	
	@Override
	public boolean isWalkable(Tile tile)
	{
		return false;
	}
	
	
	@Override
	public boolean isPotentiallyWalkable()
	{
		return true;
	}
	
	
	@Override
	public boolean isNullTile()
	{
		return true;
	}
	
	
	@Override
	public Tile create()
	{
		if (inst == null) {
			inst = new Tile(this);
		}
		
		return inst;
	}
	
}
