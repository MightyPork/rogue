package mightypork.rogue.world.item;


import java.io.IOException;

import mightypork.gamecore.util.ion.IonInput;
import mightypork.gamecore.util.ion.IonOutput;


/**
 * Item model (builder)
 * 
 * @author MightyPork
 */
public final class ItemModel {
	
	/** Model ID */
	public final int id;
	public final Class<? extends Item> itemClass;
	
	
	public ItemModel(int id, Class<? extends Item> item)
	{
		Items.register(id, this);
		this.id = id;
		this.itemClass = item;
	}
	
	
	/**
	 * @return new item instance of this type
	 */
	public <T extends Item> T createItem()
	{
		try {
			return (T) itemClass.getConstructor(ItemModel.class).newInstance(this);
		} catch (final Exception e) {
			throw new RuntimeException("Could not instantiate an item.", e);
		}
	}
	
	
	public Item loadItem(IonInput in) throws IOException
	{
		final Item t = createItem();
		t.load(in);
		return t;
	}
	
	
	public void saveItem(IonOutput out, Item tile) throws IOException
	{
		if (itemClass != tile.getClass()) throw new RuntimeException("Item class mismatch.");
		
		tile.save(out);
	}
}
