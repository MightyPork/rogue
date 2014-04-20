package mightypork.rogue.world.tile.models;


import mightypork.rogue.world.map.TileRenderContext;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;


/**
 * Null tile
 * 
 * @author MightyPork
 */
public abstract class AbstractNullTile extends TileModel {
	
	private Tile inst;
	
	
	public AbstractNullTile(int id)
	{
		super(id);
	}
	
	
	@Override
	public void render(TileRenderContext context)
	{
	}
	
	
	@Override
	public void update(Tile tile, double delta)
	{
	}
	
	
	@Override
	public boolean isWalkable(Tile tile)
	{
		return isPotentiallyWalkable();
	}
	
	
	@Override
	public boolean isNullTile()
	{
		return true;
	}
	
	
	@Override
	public Tile createTile()
	{
		if (inst == null) {
			inst = new Tile(this);
		}
		
		return inst;
	}
	
}
