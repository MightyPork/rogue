package mightypork.rogue.world;


import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.util.math.Easing;
import mightypork.gamecore.util.math.constraints.num.mutable.NumAnimated;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.item.Item;


/**
 * Message log in world view
 * 
 * @author MightyPork
 */
public class WorldConsole implements Updateable {
	
	/** Used only for display */
	public Item lastPickupItem;
	public double timeSinceLastPickup = Integer.MAX_VALUE;
	
	private static final double DURATION = 5;
	
	public class Entry implements Updateable {
		
		private final String text;
		private final NumAnimated fadeout;
		private boolean fading = false;
		private double elapsed = 0;
		
		
		private Entry(String text)
		{
			this.text = text;
			this.fadeout = new NumAnimated(1, Easing.LINEAR);
			this.fadeout.setDefaultDuration(0.5);
		}
		
		
		@Override
		public void update(double delta)
		{
			elapsed += delta;
			
			if (fading) {
				fadeout.update(delta);
				return;
			}
			
			if (elapsed > DURATION) {
				fading = true;
				fadeout.fadeOut();
			}
		}
		
		
		private boolean canRemove()
		{
			return fading && fadeout.isFinished();
		}
		
		
		public double getAlpha()
		{
			return !fading ? 1 : fadeout.value();
		}
		
		
		public String getMessage()
		{
			return text;
		}
		
		
		public double getAge()
		{
			return elapsed;
		}
	}
	
	private final Deque<Entry> entries = new LinkedBlockingDeque<>();
	
	
	@Override
	public void update(double delta)
	{
		for (final Iterator<Entry> iter = entries.iterator(); iter.hasNext();) {
			final Entry e = iter.next();
			
			e.update(delta);
			
			if (e.canRemove()) {
				iter.remove();
			}
		}
		
		timeSinceLastPickup += delta;
	}
	
	
	public void addMessage(String message)
	{
		entries.addFirst(new Entry(message));
	}
	
	
	public Collection<Entry> getEntries()
	{
		return entries;
	}
	
	
	public void clear()
	{
		entries.clear();
	}
	
	
	public void msgCannotPick()
	{
		addMessage("Can't collect items, inventory is full.");
	}
	
	
	public void msgDie(Entity attacker)
	{
		addMessage("You've been defeated by a " + attacker.getVisualName() + "!");
		
		addMessage("CTRL+M ... main menu");
		addMessage("CTRL+N ... new game");
	}
	
	
	public void msgDiscoverSecretDoor()
	{
		addMessage("You've discovered a secret door.");
	}
	
	
	public void msgEat(Item item)
	{
		addMessage("You've eaten a " + item.getVisualName() + ".");
	}
	
	
	public void msgEnterFloor(int floor)
	{
		addMessage("~ Floor " + (1 + floor) + " ~");
	}
	
	
	public void msgEquipWeapon(Item item)
	{
		addMessage("You're now wielding " + (item == null ? "NOTHING" : "a " + item.getVisualName()) + ".");
	}
	
	
	public void msgHeartPiece()
	{
		addMessage("Your health capacity has been increased.");
	}
	
	
	public void msgKill(Entity prey)
	{
		addMessage("You've killed a " + prey.getVisualName() + ".");
	}
	
	
	public void msgNoMoreFood()
	{
		addMessage("You don't have any food!");
	}
	
	
	public void msgNotHungry()
	{
		addMessage("You are not hungry.");
	}
	
	
	public void msgPick(Item item)
	{
		addMessage("You've picked up a " + item.getVisualName() + ".");
		lastPickupItem = item;
		timeSinceLastPickup = 0;
	}
	
	
	public void msgWeaponBreak(Item item)
	{
		addMessage("Your " + item.getVisualName() + " has broken!");
	}
}