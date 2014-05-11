package mightypork.rogue.world;


import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import mightypork.gamecore.eventbus.events.Updateable;
import mightypork.gamecore.util.math.Easing;
import mightypork.gamecore.util.math.constraints.num.mutable.NumAnimated;


public class WorldConsole implements Updateable {
	
	private static final double DURATION = 5;
	
	public class Entry implements Updateable {
		
		private final String text;
		private final NumAnimated fadeout;
		private boolean fading = false;
		private double elapsed = 0;
		
		
		private Entry(String text) {
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
	
	private final Deque<Entry> entries = new LinkedList<>();
	
	
	@Override
	public void update(double delta)
	{
		for (Iterator<Entry> iter = entries.iterator(); iter.hasNext();) {
			Entry e = iter.next();
			
			e.update(delta);
			
			if (e.canRemove()) {
				iter.remove();
			}
		}
	}
	
	
	public void addMessage(String message)
	{
		entries.addFirst(new Entry(message));
	}
	
	
	public Collection<Entry> getEntries()
	{
		return entries;
	}
	
	public void clear() {
		entries.clear();
	}
}
