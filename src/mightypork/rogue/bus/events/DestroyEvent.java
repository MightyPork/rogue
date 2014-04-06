package mightypork.rogue.bus.events;


import mightypork.utils.control.bus.events.Event;
import mightypork.utils.control.bus.events.types.ImmediateEvent;
import mightypork.utils.control.interf.Destroyable;


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
