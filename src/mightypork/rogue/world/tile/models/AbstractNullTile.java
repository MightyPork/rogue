package mightypork.rogue.world.tile.models;


import mightypork.rogue.world.map.TileRenderContext;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;
import mightypork.util.files.ion.IonBundle;


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
	
	@Override
	public boolean hasMetadata()
	{
		return false;
	}
	
	@Override
	public void loadMetadata(Tile tile, IonBundle ib)
	{
	}
	
	@Override
	public void saveMetadata(Tile tile, IonBundle ib)
	{
	}
	
}
