package mightypork.rogue.world.item;


import java.io.IOException;

import mightypork.gamecore.util.ion.Ion;
import mightypork.gamecore.util.ion.IonBundle;
import mightypork.gamecore.util.ion.IonInput;
import mightypork.gamecore.util.ion.IonOutput;
import mightypork.gamecore.util.math.Calc;


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
	public Item createItem()
	{
		try {
			final Item itm = itemClass.getConstructor(ItemModel.class).newInstance(this);
			
			itm.setRemainingUses(itm.getMaxUses());
			
			return itm;
			
		} catch (final Exception e) {
			throw new RuntimeException("Could not instantiate an item.", e);
		}
	}
	
	
	public Item loadItem(IonBundle in) throws IOException
	{
		final Item t = createItem();
		t.load(in);
		return t;
	}
	
	
	public void saveItem(IonBundle out, Item item) throws IOException
	{
		if (itemClass != item.getClass()) throw new RuntimeException("Item class mismatch.");
		item.save(out);
	}
	
	
	public Item createItemDamaged(int minimalHealthPercent)
	{
		final Item item = createItem();
		item.setRemainingUses(Calc.randInt((int) Math.ceil(item.getMaxUses() * (minimalHealthPercent / 100D)), item.getMaxUses()));
		return item;
	}
}
