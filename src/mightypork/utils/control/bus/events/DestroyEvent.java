package mightypork.utils.control.bus.events;


import mightypork.utils.control.bus.Event;
import mightypork.utils.control.interf.Destroyable;


/**
 * Invoke destroy() method of all subscribers. Used to deinit a system.
 * 
 * @author MightyPork
 */
public class DestroyEvent implements Event<Destroyable> {
	
	@Override
	public void handleBy(Destroyable handler)
	{
		handler.destroy();
	}
	
}
