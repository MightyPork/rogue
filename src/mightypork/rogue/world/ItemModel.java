package mightypork.rogue.world;


import java.io.InputStream;
import java.io.OutputStream;

import mightypork.util.annotations.DefaultImpl;
import mightypork.util.constraints.rect.proxy.RectBound;


/**
 * An item model
 * 
 * @author MightyPork
 */
public abstract class ItemModel implements Model<ItemData, RectBound> {
	
	public final int id;
	
	
	public ItemModel(int id) {
		this.id = id;
		Items.register(id, this);
	}
	
	
	@Override
	public final int getId()
	{
		return id;
	}
	
	
	@Override
	@DefaultImpl
	public void create(ItemData item)
	{
	}
	
	
	/**
	 * On search performed (reveal hidden door etc)
	 * 
	 * @param item item in world
	 */
	@DefaultImpl
	public void search(TileData item)
	{
	}
	
	
	/**
	 * Check if an entity can walk this item. If the item is not potentially
	 * walkable, then this method must always return false.
	 * 
	 * @param item item in world
	 */
	public abstract void isWalkable(TileData item);
	
	
	@Override
	@DefaultImpl
	public void load(ItemData item, InputStream in)
	{
	}
	
	
	@Override
	@DefaultImpl
	public void save(ItemData item, OutputStream out)
	{
	}
	
	
	@Override
	public abstract void render(ItemData item, RectBound context);
	
	
	@Override
	@DefaultImpl
	public void update(ItemData item, double delta)
	{
	}
}
