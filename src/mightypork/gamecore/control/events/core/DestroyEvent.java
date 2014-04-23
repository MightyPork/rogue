package mightypork.gamecore.control.events.core;


import mightypork.util.control.Destroyable;
import mightypork.util.control.eventbus.BusEvent;
import mightypork.util.control.eventbus.events.flags.ImmediateEvent;
import mightypork.util.control.eventbus.events.flags.NonConsumableEvent;


/**
 * Invoke destroy() method of all subscribers. Used to deinit a system.
 * 
 * @author MightyPork
 */
@ImmediateEvent
@NonConsumableEvent
public class DestroyEvent extends BusEvent<Destroyable> {
	
	@Override
	public void handleBy(Destroyable handler)
	{
		handler.destroy();
	}
	
}
