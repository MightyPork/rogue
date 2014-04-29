package mightypork.rogue.world.tile.tiles;


import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderer;


public abstract class SolidTile extends Tile {
	
	public SolidTile(TileModel model, TileRenderer renderer)
	{
		super(model, renderer);
	}
	
	
	@Override
	public boolean isWalkable()
	{
		return false;
	}
	
	
	@Override
	public boolean doesCastShadow()
	{
		return true;
	}
	
	
	@Override
	public boolean hasItem()
	{
		return false;
	}
	
	
	@Override
	public boolean dropItem(Item item)
	{
		return false;
	}
	
	
	@Override
	public Item pickItem()
	{
		return null;
	}
}
