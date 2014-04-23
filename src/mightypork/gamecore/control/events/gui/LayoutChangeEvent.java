package mightypork.gamecore.control.events.gui;


import mightypork.util.control.eventbus.BusEvent;
import mightypork.util.control.eventbus.events.flags.ImmediateEvent;
import mightypork.util.control.eventbus.events.flags.NonConsumableEvent;


/**
 * Intended use is to notify UI component sub-clients that they should poll
 * their cached constraints.
 * 
 * @author MightyPork
 */
@ImmediateEvent
@NonConsumableEvent
public class LayoutChangeEvent extends BusEvent<LayoutChangeListener> {
	
	public LayoutChangeEvent() {
	}
	
	
	@Override
	public void handleBy(LayoutChangeListener handler)
	{
		handler.onLayoutChanged();
	}
}
