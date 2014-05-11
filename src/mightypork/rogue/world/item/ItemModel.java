package mightypork.rogue.world.item;


import java.io.IOException;
import java.util.Random;

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
	
	public static final Random rand = new Random();
	
	
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
	
	
	public Item createItemDamaged(int minimalHealthPercent)
	{
		final Item item = createItem();
		item.setRemainingUses(Calc.randInt(rand, (int) Math.ceil(item.getMaxUses() * (minimalHealthPercent / 100D)), item.getMaxUses()));
		return item;
	}
}
