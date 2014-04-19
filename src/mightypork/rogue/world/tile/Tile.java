package mightypork.rogue.world.tile;


import mightypork.rogue.world.Entity;
import mightypork.rogue.world.item.Item;


/**
 * Concrete tile in the world.
 * 
 * @author MightyPork
 */
public final class Tile extends Entity<TileData, TileModel, TileRenderContext> {
	
	public static final short ION_MARK = 700;
	
	
	public Tile() {
		super();
	}
	
	
	public Tile(TileModel model) {
		super(model);
	}
	
	
	public Tile(int id) {
		super(Tiles.get(id));
	}
	
	
	@Override
	protected TileModel getModelForId(int id)
	{
		return Tiles.get(id);
	}
	
	
	@Override
	public short getIonMark()
	{
		return ION_MARK;
	}
	
	
	@Override
	public void render(TileRenderContext context)
	{
		super.render(context);
		
		// render laying-on-top item
		if (!data.items.isEmpty()) {
			final Item item = data.items.peek();
			
			item.render(context.getRect());
		}
	}
	
	
	@Override
	public void update(double delta)
	{
		super.update(delta);
		
		// update laying-on-top item
		if (!data.items.isEmpty()) {
			final Item item = data.items.peek();
			item.update(delta);
		}
	}
	
	
	/**
	 * Try to reveal secrets of this tile
	 */
	public void search()
	{
		model.search(data);
	}
	
	
	/**
	 * @return true if a mob can walk through
	 */
	public boolean isWalkable()
	{
		return model.isWalkable(data);
	}
}
