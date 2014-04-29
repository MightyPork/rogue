package mightypork.rogue.world.tile.tiles;


import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.level.Level;
import mightypork.rogue.world.level.render.TileRenderContext;
import mightypork.rogue.world.tile.Tile;
import mightypork.rogue.world.tile.TileModel;
import mightypork.rogue.world.tile.TileRenderer;
import mightypork.rogue.world.tile.TileType;


public class NullTile extends Tile {
	
	public NullTile(TileModel model, TileRenderer renderer)
	{
		super(model, renderer);
	}
	
	
	@Override
	public void update(Level level, double delta)
	{
	}
	
	
	@Override
	public void renderTile(TileRenderContext context)
	{
	}
	
	
	@Override
	public void renderExtra(TileRenderContext context)
	{
	}
	
	
	@Override
	public boolean isWalkable()
	{
		return false;
	}
	
	
	@Override
	public TileType getType()
	{
		return TileType.NULL;
	}
	
	
	@Override
	public boolean doesCastShadow()
	{
		return false;
	}
	
	
	@Override
	public boolean doesReceiveShadow()
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
	
	
	@Override
	public boolean hasItem()
	{
		return false;
	}
}
