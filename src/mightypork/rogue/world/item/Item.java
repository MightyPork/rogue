package mightypork.rogue.world.item;


import mightypork.rogue.world.PlayerFacade;
import mightypork.utils.Support;
import mightypork.utils.annotations.DefaultImpl;
import mightypork.utils.ion.IonBundled;
import mightypork.utils.ion.IonDataBundle;
import mightypork.utils.math.Calc;
import mightypork.utils.math.constraints.rect.Rect;


public abstract class Item implements IonBundled {
	
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
	public void save(IonDataBundle out)
	{
		out.put("c", amount);
		out.put("u", uses);
	}
	
	
	@Override
	@DefaultImpl
	public void load(IonDataBundle in)
	{
		amount = in.get("c", amount);
		uses = in.get("u", uses);
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
		return Support.str(getClass()) + " x " + getAmount();
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
