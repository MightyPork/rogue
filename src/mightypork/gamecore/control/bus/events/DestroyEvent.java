package mightypork.gamecore.control.bus.events;


import mightypork.gamecore.control.bus.events.types.ImmediateEvent;
import mightypork.gamecore.control.interf.Destroyable;


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
