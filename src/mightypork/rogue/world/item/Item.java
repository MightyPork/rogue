package mightypork.rogue.world.item;


import java.io.IOException;

import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.ion.IonInput;
import mightypork.gamecore.util.ion.IonObjBlob;
import mightypork.gamecore.util.ion.IonOutput;
import mightypork.gamecore.util.math.constraints.rect.Rect;


public abstract class Item implements IonObjBlob {
	
	private final ItemModel model;
	private ItemRenderer renderer;
	private int amount = 1;
	
	
	public Item(ItemModel model)
	{
		this.model = model;
	}
	
	
	public final void render(Rect rect)
	{
		if (renderer == null) {
			renderer = makeRenderer();
		}
		
		renderer.render(rect);
	}
	
	
	protected abstract ItemRenderer makeRenderer();
	
	
	@Override
	@DefaultImpl
	public void save(IonOutput out) throws IOException
	{
		out.writeIntShort(amount);
	}
	
	
	@Override
	@DefaultImpl
	public void load(IonInput in) throws IOException
	{
		amount = in.readIntShort();
	}
	
	
	public final ItemModel getModel()
	{
		return model;
	}
	
	
	@DefaultImpl
	protected int getMaxStackSize()
	{
		return isStackable() ? 1 : 65535;
	}
	
	
	public boolean canStackWith(Item other)
	{
		return (getModel().id == other.getModel().id) && isStackable();
	}
	
	
	public abstract boolean isStackable();
	
	
	/**
	 * Add another item to this item
	 * 
	 * @param added added item
	 * @return if items are compatible and the added item was completely moved
	 *         to this item, returns true and the added item should be
	 *         discarded.
	 */
	public boolean addItem(Item added)
	{
		if (!canStackWith(added)) return false;
		
		final int room = getMaxStackSize() - this.amount;
		final int avail = added.amount;
		
		final int moved = Math.min(room, avail);
		this.amount += moved;
		added.amount -= moved;
		
		return added.isEmpty();
	}
	
	
	/**
	 * @return item amount in the stack
	 */
	public int getAmount()
	{
		return Math.max(0, amount);
	}
	
	
	/**
	 * Consume one item.
	 * 
	 * @return true if the item is fully consumed and should be removed.
	 */
	public boolean consume()
	{
		if (isEmpty()) throw new RuntimeException("Item is empty, cannot consume.");
		
		amount--;
		
		return isEmpty();
	}
	
	
	public boolean isEmpty()
	{
		return getAmount() == 0;
	}
	
	
	public Item split(int removed)
	{
		if (isEmpty()) throw new RuntimeException("Item is empty, cannot split.");
		
		final int realRemoved = Math.min(removed, amount);
		
		final Item newItm = model.createItem();
		newItm.amount = realRemoved;
		this.amount -= realRemoved;
		
		return newItm;
	}
	
	
	public abstract int getAttackPoints();
	
	
	public abstract int getFoodPoints();
	
	
	public abstract ItemType getType();
}
