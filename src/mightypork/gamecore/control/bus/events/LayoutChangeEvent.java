package mightypork.gamecore.control.bus.events;


import mightypork.gamecore.control.bus.events.types.ImmediateEvent;
import mightypork.utils.math.constraints.Pollable;


/**
 * Intended use is to notify UI component sub-clients that they should poll
 * their cached constraints.
 * 
 * @author MightyPork
 */
@ImmediateEvent
public class LayoutChangeEvent implements Event<Pollable> {
	
	public LayoutChangeEvent() {
	}
	
	
	@Override
	public void handleBy(Pollable handler)
	{
		handler.poll();
	}
}
