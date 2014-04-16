package mightypork.gamecore.control.events;


import mightypork.util.control.Destroyable;
import mightypork.util.control.eventbus.events.Event;
import mightypork.util.control.eventbus.events.flags.ImmediateEvent;


/**
 * Invoke destroy() method of all subscribers. Used to deinit a system.
 * 
 * @author MightyPork
 */
@ImmediateEvent
public class DestroyEvent implements Event<Destroyable> {
	
	@Override
	public void handleBy(Destroyable handler)
	{
		handler.destroy();
	}
	
}
