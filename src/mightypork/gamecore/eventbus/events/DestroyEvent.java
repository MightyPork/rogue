package mightypork.gamecore.eventbus.events;


import mightypork.gamecore.eventbus.BusEvent;
import mightypork.gamecore.eventbus.event_flags.ImmediateEvent;
import mightypork.gamecore.eventbus.event_flags.NonConsumableEvent;


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