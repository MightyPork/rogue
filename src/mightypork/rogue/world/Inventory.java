package mightypork.rogue.world;


import java.io.IOException;

import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.Items;
import mightypork.utils.ion.IonBinary;
import mightypork.utils.ion.IonInput;
import mightypork.utils.ion.IonOutput;


public class Inventory implements IonBinary {
	
	public static final short ION_MARK = 54;
	private Item[] items;
	private int lastAddIndex = 0;
	
	
	public Inventory(int size)
	{
		this.items = new Item[size];
	}
	
	
	public Inventory()
	{
		// ION constructor
	}
	
	
	@Override
	public void load(IonInput in) throws IOException
	{
		final int size = in.readIntByte();
		items = new Item[size];
		
		// for all items in sequence
		while (in.hasNextEntry()) {
			
			// load item index
			final int i = in.readIntByte();
			
			// load item
			setItem(i, Items.loadItem(in));
		}
	}
	
	
	@Override
	public void save(IonOutput out) throws IOException
	{
		// write length
		out.writeIntByte(getSize());
		
		// find items that are writable
		for (int i = 0; i < getSize(); i++) {
			final Item item = getItem(i);
			if (item != null && !item.isEmpty()) {
				
				// start sequence entry
				out.startEntry();
				
				// write index
				out.writeIntByte(i);
				
				// write item at index
				Items.saveItem(out, item);
			}
		}
		// close sequence
		out.endSequence();
	}
	
	
	/**
	 * Get item in a slot
	 *
	 * @param i slot number
	 * @return item in the slot; can be null.
	 */
	public Item getItem(int i)
	{
		if (i < 0 || i > getSize()) return null;
		
		verifyIndex(i);
		final Item itm = items[i];
		if (itm == null || itm.isEmpty()) return null;
		return itm;
	}
	
	
	private void verifyIndex(int i)
	{
		if (i < 0 || i > getSize()) {
			throw new IndexOutOfBoundsException("Invalid inventory index: " + i + ", size: " + getSize());
		}
	}
	
	
	/**
	 * Put item in a slot
	 *
	 * @param i slot number
	 * @param item item to store
	 */
	public void setItem(int i, Item item)
	{
		verifyIndex(i);
		items[i] = item;
		lastAddIndex = i;
	}
	
	
	/**
	 * @return inventory size
	 */
	public int getSize()
	{
		return items.length;
	}
	
	
	/**
	 * Add an item, try to merge first.
	 *
	 * @param stored stored item
	 * @return true if the item was entirely added, and is now empty.
	 */
	public boolean addItem(Item stored)
	{
		// try to merge with another item
		for (int i = 0; i < getSize(); i++) {
			final Item itm = getItem(i);
			if (itm != null) {
				if (itm.addItem(stored)) {
					lastAddIndex = i;
					return true;
				}
			}
		}
		
		// try to place in a free slot
		for (int i = 0; i < getSize(); i++) {
			final Item itm = getItem(i);
			if (itm == null) {
				setItem(i, stored.split(stored.getAmount())); // store a copy, empty the original item.
				lastAddIndex = i;
				return true;
			}
		}
		
		// could not insert.
		return false;
	}
	
	
	/**
	 * Clean empty items
	 */
	public void clean()
	{
		for (int i = 0; i < getSize(); i++) {
			final Item itm = getItem(i);
			if (itm == null) continue;
			if (itm.isEmpty()) setItem(i, null);
		}
	}
	
	
	public int getLastAddIndex()
	{
		return lastAddIndex;
	}
	
	
	@Override
	public String toString()
	{
		String s = "Inv[";
		
		for (int i = 0; i < getSize(); i++) {
			if (i > 0) s += ", ";
			s += i + ": ";
			final Item itm = getItem(i);
			
			if (itm == null) s += "<null>";
			else s += itm;
		}
		s += "]";
		return s;
	}
}
