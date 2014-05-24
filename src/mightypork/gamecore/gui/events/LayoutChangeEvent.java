package mightypork.gamecore.gui.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.event_flags.DirectEvent;
import mightypork.gamecore.eventbus.event_flags.NonConsumableEvent;
import mightypork.gamecore.eventbus.event_flags.NonRejectableEvent;


/**
 * Intended use is to notify UI component sub-clients that they should poll
 * their cached constraints.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@DirectEvent
@NonConsumableEvent
@NonRejectableEvent
public class LayoutChangeEvent extends BusEvent<LayoutChangeListener> {
	
	public LayoutChangeEvent()
	{
	}
	
	
	@Override
	public void handleBy(LayoutChangeListener handler)
	{
		handler.onLayoutChanged();
	}
}
