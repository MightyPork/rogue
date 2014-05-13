package mightypork.rogue.world.item;


import java.io.IOException;

import mightypork.gamecore.logging.Log;
import mightypork.gamecore.util.annot.DefaultImpl;
import mightypork.gamecore.util.ion.IonInput;
import mightypork.gamecore.util.ion.IonObjBlob;
import mightypork.gamecore.util.ion.IonOutput;
import mightypork.gamecore.util.math.Calc;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.rogue.world.PlayerFacade;


public abstract class Item implements IonObjBlob {
	
	private final ItemModel model;
	private ItemRenderer renderer;
	private int amount = 1;
	private int uses = 1;
	
	
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
		return isStackable() ? 65535 : 1;
	}
	
	
	public boolean canStackWith(Item other)
	{
		return (getModel().id == other.getModel().id);
	}
	
	
	protected abstract boolean isStackable();
	
	
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
		if (added.isEmpty()) return true;
		
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
	public void consume()
	{
		if (isEmpty()) throw new RuntimeException("Item is empty, cannot consume.");
		
		amount--;
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
		newItm.uses = uses;
		newItm.amount = realRemoved;
		this.amount -= realRemoved;
		
		return newItm;
	}
	
	
	public abstract int getAttackPoints();
	
	
	public abstract int getFoodPoints();
	
	
	public abstract ItemType getType();
	
	
	@Override
	public String toString()
	{
		return Log.str(getClass()) + " x " + getAmount();
	}
	
	
	public int getRemainingUses()
	{
		return uses;
	}
	
	
	public abstract int getMaxUses();
	
	
	public void setRemainingUses(int uses)
	{
		this.uses = Calc.clamp(uses, 0, getMaxUses());
	}
	
	
	public void use()
	{
		if (uses > 0) uses--;
		if (uses == 0) consume();
	}
	
	
	public abstract boolean isDamageable();
	
	
	public abstract String getVisualName();
	
	
	@DefaultImpl
	public boolean pickUp(PlayerFacade pl)
	{
		return pl.addItem(this);
	}
}
