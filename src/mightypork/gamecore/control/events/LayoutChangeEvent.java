package mightypork.gamecore.control.events;


import mightypork.util.control.eventbus.events.Event;
import mightypork.util.control.eventbus.events.flags.ImmediateEvent;


/**
 * Intended use is to notify UI component sub-clients that they should poll
 * their cached constraints.
 * 
 * @author MightyPork
 */
@ImmediateEvent
public class LayoutChangeEvent implements Event<LayoutChangeEvent.Listener> {
	
	public LayoutChangeEvent() {
	}
	
	
	@Override
	public void handleBy(Listener handler)
	{
		handler.onLayoutChanged();
	}
	
	public interface Listener {
		
		public void onLayoutChanged();
	}
}
